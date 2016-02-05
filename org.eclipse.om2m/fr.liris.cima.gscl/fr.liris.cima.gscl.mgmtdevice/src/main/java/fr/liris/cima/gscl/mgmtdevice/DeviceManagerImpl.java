package fr.liris.cima.gscl.mgmtdevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import obix.Obj;
import obix.io.ObixDecoder;
import obix.io.ObixEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.commons.resource.Application;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.core.service.SclService;

import fr.liris.cima.gscl.commons.Capability;
import fr.liris.cima.gscl.commons.ContactInfo;
import fr.liris.cima.gscl.commons.Device;
import fr.liris.cima.gscl.commons.DeviceDescription;
import fr.liris.cima.gscl.commons.Encoder;
import fr.liris.cima.gscl.commons.Protocol;
import fr.liris.cima.gscl.commons.constants.Constants;
import fr.liris.cima.gscl.commons.parser.Parser;
import fr.liris.cima.gscl.commons.util.Utils;
import fr.liris.cima.gscl.device.service.ManagedDeviceService;
import fr.liris.cima.gscl.device.service.capability.CapabilityManager;
import fr.liris.cima.gscl.portforwarding.PortForwardingInterface;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.*;

public class DeviceManagerImpl implements ManagedDeviceService {

	/** Logger */
	private static Logger LOGGER = Logger.getLogger(DeviceManagerImpl.class.getName());
	private  static  Handler fh ;

	public final static String DATA = "DATA";
	/** Descriptor container id */
	public final static String DESC = "DESCRIPTOR";

	/** Capabilities container */
	public final static String CAPABS = "CAPABILITIES";

	/** Discovered SCL service*/
	static SclService SCL;
	
	private PortForwardingInterface portForwardingService;

	private static Encoder encoder;

	static List<Device> devices;

	static List<Device> unknownDevices;

	static ConfigManagerImpl configManagerImpl;

	static CapabilityManager capabilityManager;

	public DeviceManagerImpl() {
		devices = new ArrayList<>();
		unknownDevices = new ArrayList<>();
		configManagerImpl = new ConfigManagerImpl();
		capabilityManager = new CapabilityManagerImpl();


	}

	public DeviceManagerImpl(SclService scl, PortForwardingInterface pf){
		SCL = scl;
		portForwardingService = pf;
		devices = new ArrayList<>();
		unknownDevices = new ArrayList<>();
		configManagerImpl = new ConfigManagerImpl();
		capabilityManager = new CapabilityManagerImpl();

		encoder = new Encoder(portForwardingService);
	}

	public static void init(SclService scl) {
		SCL = scl;
		devices = new ArrayList<>();
		unknownDevices = new ArrayList<>();
	}

	@Override
	public Device getDevice(String deviceId) {
		for(Device device: devices) {
			if(device.getDeviceDescription().getId().equals(deviceId))
				return device;
		}
		return null;
	}

	@Override
	public Device getDeviceByAddress(String address) {

		for(Device device : devices) {
			String ipAddress = Utils.extractIpAdress(device.getDeviceDescription().getUri());
			if(ipAddress.equals(address)) return device;
		}
		return null;
	}

	@Override
	public void addDevice(Device device) {
		devices.add(device);
		for(Capability capability : device.getCapabilities()){
			this.capabilityManager.add(capability);
		}
		this.askForPortForwarding(device);
	}

	private void askForPortForwarding(Device device){ //TODO : logique métier cote PF
		String deviceID = device.getId();
		int port = 8080; //TODO; //SELON CAPACITE
		String address = device.getUri(); //TODO verif
		
		System.out.println("SEND SEND SEND : "+ deviceID + " " + port + " " + address);
		portForwardingService.askNewPortForwarding(address, port, deviceID);
		System.out.println("SENDED SENDED");
	}
	
	@Override
	public void removeDevice(String deviceId) {
		Device  device = getDevice(deviceId);
		if(device != null) {
			devices.remove(device);
		}
	}
	@Override
	public void removeDevice(Device device) {
		devices.remove(device);
	}

	@Override
	public void start() {
		try{
			fh = new FileHandler("log/gsclMgmtDevice.log", true);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}

		LOGGER.info("Devices waiting for attachement..");
		createManagerResources("CIMA", "devices");
	}


	/**
	 * create a manager resources
	 * @param appId
	 * @param aPoCPath
	 */
	public void createManagerResources(String appId, String aPoCPath) {
		// Create the Application resource
		ResponseConfirm response = SCL.doRequest(new RequestIndication(Constants.METHOD_CREATE,Constants.SCLID+"/applications",Constants.REQENTITY,new Application(appId,aPoCPath)));
		// Create Application sub-resources only if application not yet created
		if(response.getStatusCode().equals(StatusCode.STATUS_CREATED)) {
			// Create DESCRIPTOR container sub-resource
			SCL.doRequest(new RequestIndication(Constants.METHOD_CREATE,Constants.SCLID+"/applications/"+appId+"/containers",Constants.REQENTITY,new Container(DESC)));

			// Create CAPABILITIES container sub-resource
			SCL.doRequest(new RequestIndication(Constants.METHOD_CREATE, Constants.SCLID+"/applications/"+appId+"/containers",Constants.REQENTITY,new Container(CAPABS)));

			String content, targetID;
			// Create DESCRIPTION contentInstance on the DESCRIPTOR container resource
			content = "";
			//Device.getDescriptorRep(SCLID, appId);
			targetID= Constants.SCLID+"/applications/"+appId+"/containers/"+DESC+"/contentInstances";
			SCL.doRequest(new RequestIndication(Constants.METHOD_CREATE,targetID,Constants.REQENTITY,new ContentInstance(content.getBytes())));

		}
	}

	@Override
	public void addKnownDevice(Device device) {
		devices.add(device);
	}

	@Override
	public void removeKnownDevice(Device device) {
		devices.remove(device);
	}

	@Override
	public Device getKnownDevice(String deviceId) {
		for(Device device: devices) {
			if(device.getDeviceDescription().getId().equals(deviceId))
				return device;
		}
		return null;
	}

	@Override
	public void removeUnknownDevice(Device device) {
		unknownDevices.remove(device);
	}

	@Override
	public void addUnknownDevice(Device device) {
		unknownDevices.add(device);
	}

	@Override
	public void removeUnknownDeviceById(String deviceId) {
		Device  device = getDevice(deviceId);
		if(device != null) {
			unknownDevices.remove(device);
		}
	}

	@Override
	public Device getUnknownDevice(String deviceId) {
		for(Device device: unknownDevices) {
			if(device.getDeviceDescription().getId().equals(deviceId))
				return device;
		}
		return null;
	}

	@Override
	public boolean switchUnknownToKnownDevice(Device device) {
		LOGGER.info("******"+unknownDevices);
		if(!unknownDevices.contains(device))
			return false;
		unknownDevices.remove(device);
		return devices.add(device);
	}

	//@Override
	public Capability getCapabilityToUnknownDevice(String deviceId, String capabilityId) {
		return configManagerImpl.getCapabilityToDevice(deviceId, capabilityId);
	}

	@Override
	public List<Capability> getUnknownDeviceCapabilities(String deviceId) {
		return configManagerImpl.getDeviceCapabilities(deviceId);
	}

	@Override
	public boolean removeCapabilityDevice(String deviceId, String capabilityId) {
		return configManagerImpl.removeCapabilityDevice(deviceId, capabilityId);
	}

	@Override
	public Capability updateDeviceCapability(String deviceId, Capability capability)  {
		this.capabilityManager.add(capability);
		return configManagerImpl.updateCapability(deviceId, capability);
	}


	private void populate() {

		String obixFormat = "<obj>"+
				"<obj name=\"device\">" +
				"<str name=\"id\" val=\"DEVICE_0\"/>"+
				"<str name=\"name\" val=\"http\"/>"+
				"<str name=\"uri\" val=\"192.168.43.34:/device/capabilities/\"/>"+
				"<str name=\"dateConnection\" val=\"mercredi, oct. 22, 2014 13:52:20 PM\"/>"+
				"<str name=\"modeConnection\" val=\"ip\"/>"+
				"<list name=\"capabilities\">"+
				"<obj>"+
				"<str name=\"id\" val=\"ev3Back\"/>"+
				"<obj name=\"protocol\">"+
				"<str name=\"protocoleName\" val=\"http\"/>"+
				"<str name=\"method\" val=\"post\"/>"+
				"<str name=\"port\" val=\"8080\"/>"+
				"<str name=\"uri\" val=\"uri\"/>"+
				"</obj>"+
				"</obj>"+
				"<obj>"+
				"<str name=\"id\" val=\"phone\"/>"+
				"<obj name=\"protocol\">"+
				"<str name=\"protocoleName\" val=\"http\"/>"+
				"<str name=\"method\" val=\"post\"/>"+
				"<str name=\"port\" val=\"8080\"/>"+
				"<str name=\"uri\" val=\"uri\"/>"+
				"</obj>"+
				"</obj>"+
				"</list>"+
				"</obj>"+
				"</obj>";


		DeviceDescription deviceDescription = new DeviceDescription("ev3", "http://192.168.0.2", "http");
		deviceDescription.setId("DEVICE_0");
		Device device = new Device(deviceDescription);

		//Device device = Parser.parseObixToDevice(obixFormat);
		unknownDevices.add(device);
		LOGGER.info("size unknown = "+unknownDevices.size());

	}

	@Override
	public void sendDeviceToNSCL(Device device, RestClientService clientService) {
		RequestIndication requestIndication = new RequestIndication();
		//requestIndication.setRepresentation(device.toObixFormat());
		String representation = encoder.encodeDeviceToObix(device);
		LOGGER.info("Send device to nscl  = "+representation);

		requestIndication.setRepresentation(representation);

		requestIndication.setMethod(Constants.METHOD_CREATE);
		requestIndication.setBase("http://127.0.0.1:8080/om2m");
		requestIndication.setTargetID("/nscl/applications/CIMANSCL/devices");
		requestIndication.setRequestingEntity(Constants.ADMIN_REQUESTING_ENTITY);

		/**
		 * Envoi des infos du device au controleur du nscl
		 */
		clientService.sendRequest(requestIndication);
	}

	@Override
	public void updateUnknonwDevice(String deviceId, Device newDevice) {
		Device device = getUnknownDevice(deviceId);
		device.setCapabilities(newDevice.getCapabilities());
		device.setDeviceDescription(newDevice.getDeviceDescription());
	}

	@Override
	public void updateDevice(String deviceId, Device newDevice) {
		Device device = getDevice(deviceId);
		device.setCapabilities(newDevice.getCapabilities());
		device.setDeviceDescription(newDevice.getDeviceDescription());
		for(Capability capability : device.getCapabilities()){
			this.capabilityManager.add(capability);
		}
	}

	@Override
	public  String devicesToObixFormat() {
		obix.List obixDevices = new obix.List("devices");
		Obj obj = new Obj();

		for(Device device : devices) {
			//obixDevices.add(device.toIntrinsequeObix());
			obixDevices.add(encoder.encodeDeviceToObixObj(device));
		}
		obj.add(obixDevices);

		return ObixEncoder.toString(obj);
	}

	@Override
	public  String devicesToObixFormat(List<Device> devices) {
		obix.List obixDevices = new obix.List("devices");
		Obj obj = new Obj();

		for(Device device : devices) {
			//obixDevices.add(device.toIntrinsequeObix());
		}
		obj.add(obixDevices);

		return ObixEncoder.toString(obj);
	}


	@Override
	public  String capabilitiesToObixFormat(List<Capability> capabilities) {
		obix.List obixCapabilities = new obix.List("capabilities");
		Obj obj = new Obj();

		for(Capability capability : capabilities) {
			obixCapabilities.add(encoder.encodeCapabilityToObixObj(capability));
		}
		obj.add(obixCapabilities);

		return ObixEncoder.toString(obj);
	}


	@Override
	public void invokeCapability(String deviceId, Capability capability, RestClientService clientService) {
		System.out.println("**************************************************************** Requete Test\n");
		RequestIndication requestIndication = new RequestIndication();
		Protocol protocol = capability.getProtocol();
		Device device = null;

		String uri;
		String base = "";
		String protocolName = "http";		//TODO pas mettre en dur
		//String protocolName = protocol.getParameterValue("protocolName");

		if(getDevice(deviceId) != null) {
			device = getDevice(deviceId);
		} else if(getUnknownDevice(deviceId) != null){
			device = getUnknownDevice(deviceId);
		}

		if (device.getUri().contains("://")) {
			String tab[] = device.getUri().split("://");
			base = tab[1];
		} else {
			base = device.getUri();
		}

		if (base.contains(":")) {
			String tab[] = base.split(":");
			base = tab[0];
		}

		uri = protocolName + "://" + base + ":" + protocol.getParameterValue("port");

		String capabilityURI = capability.getProtocol().getParameterValue("uri");
		if(capabilityURI.startsWith("/")) {
			capabilityURI = capabilityURI.substring(1);
		}

		uri += "/" + capabilityURI;

		LOGGER.info(uri);
		LOGGER.info(protocolName);
		LOGGER.info(capability.getProtocol().getParameterValue("body"));
		LOGGER.info("capabilityURI = "+capabilityURI);

		requestIndication.setBase(uri);
		requestIndication.setMethod(protocol.getParameterValue("method").trim());
		requestIndication.setProtocol(protocolName);
		requestIndication.setTargetID("");
		requestIndication.setRepresentation(capability.getProtocol().getParameterValue("body"));
	//	requestIndication.setRequestingEntity(Constants.ADMIN_REQUESTING_ENTITY);

		System.out.println(requestIndication.toString());

		clientService.sendRequest(requestIndication);

		System.out.println("**************************************** URI = " + uri);

		System.out.println("**************************************** request " + requestIndication);

		System.out.println("**************************************************************** Fin Requete Test\n");

	}

	public static String devicesToObixFormat1() {
		obix.List obixDevices = new obix.List("devices");
		Obj obj = new Obj();

		for(Device device : unknownDevices) {
			obixDevices.add(encoder.encodeDeviceToObixObj(device));

		}
		obj.add(obixDevices);
		return ObixEncoder.toString(obj);
	}


	@Override
	public List<Device> getDevices(boolean all) {
		List<Device> devices = new ArrayList<>();
		if (all) {
			return devices;
		}
		else {
			for( Device device : devices) {
				if(device.isKnown()) {
					devices.add(device);
				}
			}
		}
		return devices;
	}


	@Override
	public List<Capability> getDeviceCapabilities(String deviceId) {
		return this.getDevice(deviceId).getCapabilities();
	}

	public Capability getCapabilityDevice(String deviceId, String capabilityName) {
		Device device = getDevice(deviceId);
		for(Capability capability : device.getCapabilities()) {
			if(capability.getName().equals(capabilityName)) {
				return capability;
			}
		}
		return null;
	}


}
