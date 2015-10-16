package fr.liris.cima.gscl.discovery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;


import fr.liris.cima.gscl.commons.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import fr.liris.cima.gscl.commons.constants.*;
import fr.liris.cima.gscl.commons.parser.*;
import fr.liris.cima.gscl.commons.util.*;
import fr.liris.cima.gscl.device.service.discovery.DiscoveryService;
import fr.liris.cima.gscl.device.service.*;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.*;


/**
 * Specific device discover, for discovering a device in the local network
 * @author madiallo
 *
 */
public class DeviceDiscovery implements DiscoveryService{

	private static Logger LOGGER = Logger.getLogger(DeviceDiscovery.class.getName());
	private  static  Handler fh ;
	public static final String ADMIN_REQUESTING_ENTITY = System.getProperty("org.eclipse.om2m.adminRequestingEntity","admin/admin");

	public static final String FORWARD_PORT = System.getProperty("fr.liris.cima.gscl.forwardPort");
	public static final String CIMA_ADDRESS = System.getProperty("fr.liris.cima.gscl.adress");
	public static final String DEFAULT_DEVICE_PATH_INFOS = System.getProperty("fr.liris.cima.gscl.defaultDevicePathInfos");
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


	/**
	 * key : deviceId_capabilityPort
	 * Value : generate port  by c part for contacting object from cloud
	 */
	Map<String, Integer>mapConnectionPortForwarding;

	/**
	 * key : deviceId_capabilityPort
	 * Value : status that indicate if port opening in the c part is closing or not
	 */
	Map<String, String>mapDisconnectionPortForwarding;


	public DeviceDiscovery() {

	}
	/**
	 * Make an DeviceDiscovery object with client Service and a device service.
	 * @param clientService - The client service like http, coap, ...
	 * @param deviceService - The device service for adding device, deleting device, ...
	 */
	public DeviceDiscovery(RestClientService clientService, ManagedDeviceService deviceService) {
		try{
			fh = new FileHandler("log/gsclDiscovery.log", true);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


		this.clientService = clientService;
		this.deviceService = deviceService;

		this.mapKnownAddresses = new HashMap<>();
		this.mapConfiguredAddresses = new HashMap<>();

		this.mapConnectedAddresses= new HashMap<>();

		this.mapConnectionPortForwarding = new HashMap<>();
		this.mapDisconnectionPortForwarding = new HashMap<>();

		cimaInternalCommunication = new CIMAInternalCommunication();
	}

	/**
	 * Lookup a connected address in the local network by arp scan
	 * @return
	 */
	private Set<String> lookUp() {
		//return ExecuteShellComand.getAllIpAddress(Constants.COMMAND_ARP_FOR_IP, Constants.IP_PREFIX);
		IPFinderManager.startIfNotStarted();

		Set<String> addresses = IPFinderManager.getAccesiblesIP();

		return addresses;
	}

	/**
	 * Send a notification to the NSCL for device disconnecting.
	 * @param device -The that is disconnected.
	 * @return The generic returned response.
	 */
	private ResponseConfirm notifyDisconnectionToInfController(DeviceDescription deviceDescription) {
		try{
			fh = new FileHandler("log/gsclDiscovery.log", true);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


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
		Device device = deviceService.getDevice(deviceDescription.getId());
	//	String data = Encoder.JsonDeviceDisconnectionInfoToPortForwading(device);

	//	cimaInternalCommunication.sendInfos(data);

		ResponseConfirm responseConfirm = clientService.sendRequest(requestIndication);
		return responseConfirm;
	}



	/**
	 * disocvery  devices in the local network.
	 */
	@Override
	public void doDiscovery() {
		try{
		fh = new FileHandler("log/gsclDeviceDiscovery.log", true);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}

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

				// Get ids that identifying  specifics ports to devices, in port forwarding part
//				List<String> ids = new ArrayList<>();
//				ids.addAll(mapConnectionPortForwarding.keySet());
				Device device = deviceService.getDeviceByAddress(address);
				// Generate port forwarding ids
				//Device device =deviceService.getDeviceByAddress(address);
				List<String> ids = generateIdsPortForwardingIds(device);

				// Send disconnection notification to port forwarding part
				String responseDisconnection = cimaInternalCommunication.sendInfos(Encoder.JsonDeviceDisconnectionInfoToPortForwading(ids));
				mapDisconnectionPortForwarding = Encoder.decodeResponseDisconnection(responseDisconnection);
				for(Map.Entry<String, String> entry : mapDisconnectionPortForwarding.entrySet() ) {
					if(! entry.getValue().equals("OK")) {
						String portForwadingId = entry.getKey();
						int cloudPort =  mapConnectionPortForwarding.get(portForwadingId);
						LOGGER.info("Error for disconnecting port "+cloudPort + " in port forwarding part");
					}
				}

				// update mapConnectedAddresses by removing the device address
				LOGGER.info(" Le device viens de se deconnecter  " + address);
				mapConnectedAddresses.remove(address);
			}

			requestIndication.setBase(address);
			boolean deviceCreated = false;
			try {
				// Create connected device
				if(this.handleNewDeviceConnection(address, requestIndication, ":8080/infos")){}
				//	else if(this.handleNewDeviceConnection(address, requestIndication, ":8080/simu/infos")){}
				else {
					LOGGER.info(" UNKNOWN DEVICE "+address);
					if( !mapConfiguredAddresses.containsKey(address)) {
						// Create unknown device
						LOGGER.info(" UNKNOWN DEVICE add successfully "+address);

						DeviceDescription deviceDescription = new DeviceDescription();
						deviceDescription.setModeConnection(Constants.MOD_IP);
						deviceDescription.setUri(address);
						Device device = new Device(deviceDescription);
						device.setKnown(false);
						deviceService.addDevice(device);
					//	deviceService.addUnknownDevice(device);
						mapConfiguredAddresses.put(address, deviceDescription.getId());
					}
				}
			}catch(Exception e) {
			}
		}
	}

	/**
	 * This method handle new device connection by sending a request for discovery of potential device.
	 * @param address
	 * @param requestIndication
	 * @param targetId
	 * @return
	 */
	protected boolean handleNewDeviceConnection(String address, RequestIndication requestIndication, String targetId){
		try{
		fh = new FileHandler("log/gsclDeviceDiscovery.log", true);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


		requestIndication.setTargetID(targetId);
		ResponseConfirm responseConfirm = null ;
		LOGGER.info("*****Client Service ******* : " + clientService);
		responseConfirm = clientService.sendRequest(requestIndication);

		LOGGER.info("device requestIndication = "+requestIndication);

		if(!(responseConfirm.getStatusCode() == null) && (responseConfirm.getStatusCode().equals(StatusCode.STATUS_OK) ||
				responseConfirm.getStatusCode().equals(StatusCode.STATUS_ACCEPTED)) ) {
			String representation = responseConfirm.getRepresentation();
			representation = representation.replace("<!--home oage-->\n", "");
			LOGGER.info("***************Representation******************* : \n" + representation);
			LOGGER.info("***************handleNewDeviceConnection*******************");

			// Create device from its xml representation
			Device device = Parser.parseXmlToDevice(representation);
			device.setKnown(true);
			device.setConfiguration("automatic");
			// Adding device to the device manager
			deviceService.addDevice(device);

			// Notify NSCL
			//deviceService.sendDeviceToNSCL(device, clientService);

			LOGGER.info("**SEND NOTIFICATION TO THE NSCL 1**" + device);


			RequestIndication client = new RequestIndication();

			client.setMethod(Constants.METHOD_CREATE);
			client.setBase("http://127.0.0.1:8080/om2m");
			client.setTargetID("/nscl/applications/CIMANSCL/devices");
			client.setRequestingEntity(Constants.ADMIN_REQUESTING_ENTITY);
			String encod = Encoder.encodeDeviceToObix(device);
			LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + encod);
			client.setRepresentation(encod);
			LOGGER.info("YYYYYYYYYYYYYYYYYYYY D E V I C E YYYYYYYYYYYYYYYYYYYY" + device);
			LOGGER.info("**SEND NOTIFICATION TO THE NSCL in obix**");




			// Send notification request to the nscl
			clientService.sendRequest(client);

			// Add the (key = IP address of the device, value = deviceId) to the map
			mapConnectedAddresses.put(address, device.getId());

			// Encode connection data to json for the c part
			String data = Encoder.encodeDeviceToJSONPortForwarding(device);

			// send connection data to the c part
			//String cResponse = cimaInternalCommunication.sendInfos(data);
			//String cResponse = cimaInternalCommunication.sendInfosUDP(data);
			String cResponse = cimaInternalCommunication.handleRequest(data);

			// Decode connection response from c part
			mapConnectionPortForwarding = Encoder.decodeJson(cResponse);

			return true;
		} else return false;
	}

	private  boolean checkKnownDeviceConnection(RequestIndication requestIndication) {
		try{
		fh = new FileHandler("log/gsclDeviceDiscovery.log", true);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


		requestIndication.setTargetID(":8080"+DEFAULT_DEVICE_PATH_INFOS);
		ResponseConfirm responseConfirm = clientService.sendRequest(requestIndication);
		LOGGER.info("***********isAlwaysConnected*************"+requestIndication.getUrl());
		if(responseConfirm.getStatusCode() != null && responseConfirm.getStatusCode().equals(StatusCode.STATUS_OK)) {
			String representation = responseConfirm.getRepresentation();
			LOGGER.info("device is connected");
			return true;
		}
		LOGGER.info("**********device is disconnected*****************");
		return false;
	}

	// Generates a list of id for the port forwarding section
	//each identifier is like deviceId_protocolPort
	private List<String> generateIdsPortForwardingIds(Device device) {
		List<String> ids = new ArrayList<>();

		for(Capability capability : device.getCapabilities()) {
			int port = Integer.parseInt(capability.getProtocol().getParameterValue("port"));
			ids.add(device.getId() + "_" + port);
		}
		return ids;
	}
}
