package fr.liris.cima.gscl.discovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;

import fr.liris.cima.gscl.commons.Device;
import fr.liris.cima.gscl.commons.ExecuteShellComand;
import fr.liris.cima.gscl.commons.constants.*;
import fr.liris.cima.gscl.commons.parser.*;
import fr.liris.cima.gscl.device.service.discovery.DiscoveryService;
import fr.liris.cima.gscl.device.service.*;

/**
 * Specific device discover, for discovering a device in the local network
 * @author madiallo
 *
 */
public class DeviceDiscovery implements DiscoveryService{

	private static Log LOGGER = LogFactory.getLog(DeviceDiscovery.class);
	public static final String ADMIN_REQUESTING_ENTITY = System.getProperty("org.eclipse.om2m.adminRequestingEntity","admin/admin");

	// A rest client service
	private RestClientService clientService;
	// A device managed service
	private ManagedDeviceService deviceService;

	private Map<String, String> mapConnectedAddresses;

	public DeviceDiscovery() {

	}
	/**
	 * Make an DeviceDiscovery object with client Service and a device service.
	 * @param clientService - The client service like http, coap, ...
	 * @param deviceService - The device service for adding device, deleting device, ...
	 */
	public DeviceDiscovery(RestClientService clientService, ManagedDeviceService deviceService) {
		this.clientService = clientService;
		this.deviceService = deviceService;
		this.mapConnectedAddresses= new HashMap<>(); 
	}

	/**
	 * Lookup a connected address in the local network by arp scan
	 * @return
	 */
	private Set<String> lookUp() {
		return ExecuteShellComand.getAllIpAddress(Constants.COMMAND_ARP_FOR_IP, Constants.IP_PREFIX);
	}

	/**
	 * Check a connected device, 
	 */
	private void  handleAlwaysConnected() {
		int i = 0;
		Set<String> listIpAddress = lookUp();
		Set<String> connectedAddresses = mapConnectedAddresses.keySet();
		for(String address : connectedAddresses) {
			if(!listIpAddress.contains(address)) {
				do {
					try {
						Thread.sleep(1000);
						listIpAddress = lookUp();
						i++;
						LOGGER.info("i#####################"+i+"######################address"+address);
						LOGGER.info(listIpAddress.contains(address));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}while(!listIpAddress.contains(address) && i < 10);
				if(listIpAddress.contains(address)) return;
				i = 0;
				LOGGER.info("**********Handle connected devices **********" + address);
				String deviceId = mapConnectedAddresses.get(address);
				notifyDisconnectionToInfController(deviceService.getDevice(deviceId));
				mapConnectedAddresses.remove(address);
				deviceService.removeDevice(deviceId);
			}
		}
	}

	/**
	 * Send a notification to the NSCL for device disconnecting.
	 * @param device -The that is disconnected.
     * @return The generic returned response.
	 */
	private ResponseConfirm notifyDisconnectionToInfController(Device device) {
		LOGGER.info("SEND NOTIFICATION FOR DEVICE" + device.getId());
		LOGGER.info("representation obix = "+device.toObixFormat());
		RequestIndication requestIndication = new RequestIndication();
		requestIndication.setRepresentation(device.toObixFormat());
		requestIndication.setProtocol("http");
		requestIndication .setMethod(Constants.METHOD_DELETE);
		requestIndication.setBase("http://127.0.0.1:8080/om2m");
		requestIndication.setTargetID("/nscl/applications/CIMANSCL/devices/"+device.getId());
		requestIndication.setRequestingEntity(ADMIN_REQUESTING_ENTITY);

		/**
		 * Send notification to Infrasctrucure controller
		 */
		ResponseConfirm responseConfirm = clientService.sendRequest(requestIndication);
		return responseConfirm;
	}

	/**
	 * disocvery  devices in the local network.
	 */
	@Override
	public void doDiscovery() {
		RequestIndication requestIndication = new RequestIndication("RETRIEVE", "", "", "");
		requestIndication.setMethod("RETRIEVE");
		requestIndication.setProtocol("http");
		requestIndication.setTargetID(":8080/infos");


		Set<String> addresses = lookUp();
		LOGGER.info("in do discovery" +lookUp());

		for(String address : addresses) {

			handleAlwaysConnected();
			if(mapConnectedAddresses.containsKey(address)) continue;

			requestIndication.setBase(address);
			try {
				ResponseConfirm responseConfirm = null ;

				responseConfirm = clientService.sendRequest(requestIndication);
				if(responseConfirm.getStatusCode() == null ) continue;
				LOGGER.info("getStatusCode "+responseConfirm.getStatusCode());
				if(responseConfirm.getStatusCode().equals(StatusCode.STATUS_OK) || 
						responseConfirm.getStatusCode().equals(StatusCode.STATUS_ACCEPTED)) {
					String representation = responseConfirm.getRepresentation();

					Device device =  Parser.parseXmlDevice(representation);
					deviceService.addDevice(device);
					mapConnectedAddresses.put(address, device.getId());

					requestIndication.setRepresentation(device.toObixFormat());

					requestIndication.setMethod(Constants.METHOD_CREATE);
					requestIndication.setBase("http://127.0.0.1:8080/om2m");
					requestIndication.setTargetID("/nscl/applications/CIMANSCL/devices");
					requestIndication.setRequestingEntity(ADMIN_REQUESTING_ENTITY);

					/**
					 * Envoi des infos du device au controleur du nscl
					 */
					responseConfirm = clientService.sendRequest(requestIndication);

					
					new CIMAInternalCommunication().sendInfos(device.getContactInfo().getCloud_port()+"-8080-192.168.0.2");
				}
				LOGGER.info(responseConfirm);
			}catch(Exception e) {
			}
		}
	}
}