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

import fr.liris.cima.gscl.commons.Capability;
import fr.liris.cima.gscl.commons.Device;
import fr.liris.cima.gscl.commons.Encoder;
import fr.liris.cima.gscl.commons.DeviceDescription;
import fr.liris.cima.gscl.commons.ExecuteShellComand;
import fr.liris.cima.gscl.commons.constants.*;
import fr.liris.cima.gscl.commons.parser.*;
import fr.liris.cima.gscl.commons.util.*;
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
	
	public static final String FORWARD_PORT = System.getProperty("fr.liris.cima.gscl.forwardPort");
	public static final String CIMA_ADDRESS = System.getProperty("fr.liris.cima.gscl.adress");
	public static final String DEFAULT_DEVICE_PATH_INFOS = System.getProperty("fr.liris.cima.gscl.adress.defaultDevicePathInfos");
	public static final String DISCOVERY_WAITING_TIMER = System.getProperty("fr.liris.cima.gscl.discoveryWaitingTimer");

	// A rest client service
	private RestClientService clientService;
	// A device managed service
	private ManagedDeviceService deviceService;
	
	private CIMAInternalCommunication cimaInternalCommunication ;

	/**
	 * the map, key is ip adress and value is the device id
	 * this map store the really connected device
	 */
	private Map<String, String> mapConnectedAddresses;



	/**
	 * the map, key is ip adress and value is the device id
	 * this map store the really connected device
	 */
	private Map<String, String> mapKnownAddresses;

	/**
	 * the map, key is ip adress and value is the device id
	 * this map store the unknown adress for configuration.
	 */
	private Map<String, String> mapConfiguredAddresses;
	
	Map<Integer, Pair<Integer, Integer>>mapPortManager;


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

		this.mapKnownAddresses = new HashMap<>();
		this.mapConfiguredAddresses = new HashMap<>();

		this.mapConnectedAddresses= new HashMap<>();
		
		this.mapPortManager = new HashMap<>();
		
		cimaInternalCommunication = new CIMAInternalCommunication();
	}

	/**
	 * Lookup a connected address in the local network by arp scan
	 * @return
	 */
	private Set<String> lookUp() {
		return ExecuteShellComand.getAllIpAddress(Constants.COMMAND_ARP_FOR_IP, Constants.IP_PREFIX);
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

		new CIMAInternalCommunication().sendInfos("d"+Utils.extractIpAdress(deviceDescription.getUri()));

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

		LOGGER.info("***************getProperty"+System.getProperty("org.eclipse.om2m.sclBaseId"));

		// Retrieve all connected address in local network
		Set<String> addresses = lookUp();
		LOGGER.info("addresses = " + addresses);

		for(String address : addresses) {
			requestIndication.setBase(address);

			// Check if device is in the network local network
			if(mapConnectedAddresses.containsKey(address)) {
				LOGGER.info("device is already connected maybe = " + address);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Check if device connected to the cima plateform.
				if (checkKnownDeviceConnection(requestIndication)) {
					// The device is always connected.
					LOGGER.info("CONTINUE  " + address);
					continue;
				}
				// The device is disconnected.
				//send notification to the Infrasctructure Controller.
				String deviceId = mapConnectedAddresses.get(address);
				LOGGER.info("****deviceId***  = "+deviceId);
				DeviceDescription deviceDescription  = deviceService.getDevice(deviceId).getDeviceDescription();
				LOGGER.info("***deviceDescription*** = "+deviceDescription);
				notifyDisconnectionToInfController(deviceDescription);

				// update mapConnectedAddresses by removing the device address
				LOGGER.info(" Le device viens de se deconnecter  " + address);
				mapConnectedAddresses.remove(address);
			}

			requestIndication.setBase(address);
			boolean deviceCreated = false;
			try {
				// Create connected device
				if(this.createDevice(address, requestIndication, ":8080/infos")){}
				//	else if(this.createDevice(address, requestIndication, ":8080/simu/infos")){}
				else {
					LOGGER.info(" UNKNOWN DEVICE "+address);
					if( !mapConfiguredAddresses.containsKey(address)) {
						// Create unknown device
						LOGGER.info(" UNKNOWN DEVICE add successfully "+address);

						DeviceDescription deviceDescription = new DeviceDescription();
						deviceDescription.setModeConnection(Constants.MOD_IP);
						deviceDescription.setUri(address);
						Device device = new Device(deviceDescription);
						deviceService.addUnknownDevice(device);
						mapConfiguredAddresses.put(address, deviceDescription.getId());
					}
				}
			}catch(Exception e) {
			}
		}
	}

	protected boolean createDevice(String address, RequestIndication requestIndication, String targetId){
		requestIndication.setTargetID(targetId);
		ResponseConfirm responseConfirm = null ;

		responseConfirm = clientService.sendRequest(requestIndication);
		LOGGER.info("device requestIndication = "+requestIndication);
		// TODO 
		if(!(responseConfirm.getStatusCode() == null) && (responseConfirm.getStatusCode().equals(StatusCode.STATUS_OK) || 
				responseConfirm.getStatusCode().equals(StatusCode.STATUS_ACCEPTED)) ) {
			String representation = responseConfirm.getRepresentation();


			Device device = Parser.parseXmlToDevice(representation);
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

			//TODO
			mapConnectedAddresses.put(address, device.getId());
			mapKnownAddresses.put(address, device.getId());

			// Envoi des infos du device a la partie C de CIMA : ipAdress-TCP-PORT
			LOGGER.info("***********extractIpAdress*************"+Utils.extractIpAdress(device.getUri()));
			new CIMAInternalCommunication().sendInfos("c"+Utils.extractIpAdress(device.getUri()) + ":8080"+ "-UDP-" +device.getContactInfo().getCloud_port());
			return true;
		} else return false;
	}

	private  boolean checkKnownDeviceConnection(RequestIndication requestIndication) {
		requestIndication.setTargetID(":8080"+DEFAULT_DEVICE_PATH_INFOS);
		ResponseConfirm responseConfirm = clientService.sendRequest(requestIndication);
		LOGGER.info("***********isAlwaysConnected*************"+responseConfirm.getStatusCode());
		if(responseConfirm.getStatusCode() != null && responseConfirm.getStatusCode().equals(StatusCode.STATUS_OK)) {
			String representation = responseConfirm.getRepresentation();
			LOGGER.info("device is connected");
			return true;
		}
		LOGGER.info("**********device is disconnected*****************");
		return false;
	}
	
	public void test(Device device) {
		
		int countNbconnectedCapability = 0;
	//	String infos
		
		List<Capability> capabilities = device.getCapabilities();
		for(Capability capability : capabilities) {
			int port = Integer.parseInt(capability.getProtocol().getParameterValue("port"));
			if( !mapPortManager.containsKey(port)) {
				cimaInternalCommunication.sendInfos(data);
				mapPortManager.put(port, new Pair<>(1, second));
			}
			else {
				
			}
		}
	
		
	}
}