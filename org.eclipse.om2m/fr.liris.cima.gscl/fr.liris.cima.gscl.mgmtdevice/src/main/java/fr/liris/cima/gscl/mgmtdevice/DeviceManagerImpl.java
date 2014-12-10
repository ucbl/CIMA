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

public class DeviceManagerImpl implements ManagedDeviceService {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(DeviceManagerImpl.class);

	public final static String DATA = "DATA";
	/** Descriptor container id */
	public final static String DESC = "DESCRIPTOR";

	/** Capabilities container */
	public final static String CAPABS = "CAPABILITIES";

	/** Discovered SCL service*/
	static SclService SCL;

	
	static List<Device> devices;
	
	static List<Device> unknownDevices;
	
	static ConfigManagerImpl configManagerImpl;
	
	static CapabilityManager capabilityManager;
	
	public DeviceManagerImpl() {
		devices = new ArrayList<>();
		unknownDevices = new ArrayList<>();
		configManagerImpl = new ConfigManagerImpl();
		

	}
	
	public DeviceManagerImpl(SclService scl){
		SCL = scl;
		devices = new ArrayList<>();
		unknownDevices = new ArrayList<>();
		configManagerImpl = new ConfigManagerImpl();
		

		DeviceDescription deviceDescription = new DeviceDescription("ev3", "http://192.168.0.02:/infos/", "ip");
		Device device = new Device(deviceDescription);
		devices.add(device);
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
	
	@Override
	public Capability getCapabilityToUnknownDevice(String deviceId, String capabilityId) {
		return configManagerImpl.getCapabilityToDevice(deviceId, capabilityId);
	}
	
	@Override
	public List<Capability> getUnknownDeviceCapabilities(String deviceId) {
		return configManagerImpl.getDeviceCapabilities(deviceId);
	}
	
	@Override
	public boolean removeCapabilityToUnknownDevice(String deviceId, String capabilityId) {
		return configManagerImpl.removeCapabilityDevice(deviceId, capabilityId);
	}
	
	
	@Override
	public Capability updateUnknownDeviceCapability(String deviceId, Capability capability)  {
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
		String representation = Encoder.encodeDeviceToObix(device);
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
	}
	
	@Override
	public  String unknownDevicesToObixFormat() {
		obix.List obixDevices = new obix.List("devices");
		Obj obj = new Obj();
		
		for(Device device : unknownDevices) {
			//obixDevices.add(device.toIntrinsequeObix());
			obixDevices.add(Encoder.encodeDeviceToObixObj(device));
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
			obixCapabilities.add(Encoder.encodeCapabilityToObixObj(capability));
		}
		obj.add(obixCapabilities);
				
		return ObixEncoder.toString(obj);
	}
	
	
	@Override
	public void invokeCapability(String deviceId, Capability capability, RestClientService clientService) {
		RequestIndication requestIndication = new RequestIndication();
		Protocol protocol = capability.getProtocol();
		String base = "";
		if(getDevice(deviceId) != null) base = getDevice(deviceId).getUri();
		else if(getUnknownDevice(deviceId) != null) base = getUnknownDevice(deviceId).getUri();
		LOGGER.info(base);
		LOGGER.info(protocol.getParameterValue("protocolName"));
		LOGGER.info(capability.getProtocol().getParameterValue("body"));
		requestIndication.setBase("http://" + base + ":" + capability.getProtocol().getParameterValue("port") + capability.getProtocol().getParameterValue("uri"));
		requestIndication.setMethod(protocol.getParameterValue("method"));
		requestIndication.setProtocol("http");
		requestIndication.setTargetID("");
		requestIndication.setRepresentation(capability.getProtocol().getParameterValue("body"));
		
		clientService.sendRequest(requestIndication);
		
	}
	
	public static String unknownDevicesToObixFormat1() {
		obix.List obixDevices = new obix.List("devices");
		Obj obj = new Obj();
		
		for(Device device : unknownDevices) {
			obixDevices.add(Encoder.encodeDeviceToObixObj(device));
			
		}
		obj.add(obixDevices);
		return ObixEncoder.toString(obj);
	}
	
	
	public static void main(String args[]) {
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
		
		Device device = Parser.parseObixToDevice(obixFormat);
		unknownDevices = new ArrayList<>();
		unknownDevices.add(device);
		System.out.println(unknownDevicesToObixFormat1());
		

	}
	
	
}