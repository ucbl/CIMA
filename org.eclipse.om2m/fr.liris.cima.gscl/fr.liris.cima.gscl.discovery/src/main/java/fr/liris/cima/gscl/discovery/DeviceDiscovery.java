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
import fr.liris.cima.gscl.commons.Encoder;
import fr.liris.cima.gscl.commons.DeviceDescription;
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
						LOGGER.info(listIpAddress.contains(address));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}while(!listIpAddress.contains(address) && i < 10);
				if(listIpAddress.contains(address)) return;
				i = 0;
				LOGGER.info("**********Handle connected devices **********" + address);
				String deviceId = mapConnectedAddresses.get(address);
				notifyDisconnectionToInfController(deviceService.getDevice(deviceId).getDeviceDescription());
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
	private ResponseConfirm notifyDisconnectionToInfController(DeviceDescription deviceDescription) {
		LOGGER.info("SEND NOTIFICATION FOR DEVICE" + deviceDescription.getId());
		LOGGER.info("representation obix = "+Encoder.encodeDeviceDescriptionToObix(deviceDescription));
		RequestIndication requestIndication = new RequestIndication();
		requestIndication.setRepresentation(Encoder.encodeDeviceDescriptionToObix(deviceDescription));
		requestIndication.setProtocol("http");
		requestIndication .setMethod(Constants.METHOD_DELETE);
		requestIndication.setBase("http://127.0.0.1:8080/om2m");
		requestIndication.setTargetID("/nscl/applications/CIMANSCL/devices/"+deviceDescription.getId());
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


		Set<String> addresses = lookUp();
		//LOGGER.info("lookup in do discovery" +lookUp());
		
		for(String address : addresses) {

			handleAlwaysConnected();
			if(mapConnectedAddresses.containsKey(address)) {
				LOGGER.info("CONTINUE");
				continue;
			}

			requestIndication.setBase(address);
			boolean deviceCreated = false;
			try {
				if(this.createDevice(address, requestIndication, ":8080/infos")){}
				else if(this.createDevice(address, requestIndication, ":8080/simu/infos")){}
				else {
					DeviceDescription deviceDescription = new DeviceDescription();
					deviceDescription.setModeConnection(Constants.MOD_IP);
					deviceDescription.setUri(address);
					Device device = new Device(deviceDescription);
					deviceService.addUnknownDevice(device);
					mapConnectedAddresses.put(address, deviceDescription.getId());
					if(mapConnectedAddresses.containsKey(address)) {
						LOGGER.info("CONTINUE FOR UNKNOWN DEVICE");
						continue;
					}
				}
//				LOGGER.info(responseConfirm);
			}catch(Exception e) {
			}
		}
	}
	
	protected boolean createDevice(String address, RequestIndication requestIndication, String targetId){
		requestIndication.setTargetID(targetId);
		ResponseConfirm responseConfirm = null ;
		
		responseConfirm = clientService.sendRequest(requestIndication);
		LOGGER.info("device requestIndication = "+requestIndication);
//		LOGGER.info("getStatusCode "+responseConfirm.getStatusCode());
		// TODO 
		if(!(responseConfirm.getStatusCode() == null) && (responseConfirm.getStatusCode().equals(StatusCode.STATUS_OK) || 
				responseConfirm.getStatusCode().equals(StatusCode.STATUS_ACCEPTED)) ) {
			String representation = responseConfirm.getRepresentation();

			// TODO
		//	DeviceDescription deviceDescription  = Parser.parseXmlToDeviceDescription(representation);
		//	LOGGER.info("In if");
		//	DeviceDescription deviceDescription = new DeviceDescription("ev3", "http://192.168.0.02:/infos/", "ip");
			//Device device = new Device(deviceDescription);
			Device device = Parser.parseXmlToDevice(representation);
		//	LOGGER.info("device =  "+device);
			//LOGGER.info("device in doDiscorvery "+device);
			//LOGGER.info("representation in doDiscorvery "+representation);
		//	LOGGER.info("**********************OK1*************"+device.getId());
			deviceService.addDevice(device);
			//deviceService.sendDeviceToNSCL(device, clientService);
			
			LOGGER.info("rep = "+Encoder.encodeDeviceToObix(device));
			
			RequestIndication client = new RequestIndication();

			client.setMethod(Constants.METHOD_CREATE);
			client.setBase("http://127.0.0.1:8080/om2m");
			client.setTargetID("/nscl/applications/CIMANSCL/devices");
			client.setRequestingEntity(Constants.ADMIN_REQUESTING_ENTITY);
			client.setRepresentation( Encoder.encodeDeviceToObix(device));
			
			/**
			 * Envoi des infos du device au controleur du nscl
			 */
			clientService.sendRequest(client);
		//	LOGGER.info("**********************OK*************");

			mapConnectedAddresses.put(address, device.getId());


			// Envoi des infos du device a la partie C de CIMA
			//	new CIMAInternalCommunication().sendInfos(device.getContactInfo().getCloud_port()+"-8080-192.168.0.2");
			return true;
			} else return false;
	}
}