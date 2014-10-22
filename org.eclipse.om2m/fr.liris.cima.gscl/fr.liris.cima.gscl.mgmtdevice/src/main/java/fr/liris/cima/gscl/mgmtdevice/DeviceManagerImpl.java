package fr.liris.cima.gscl.mgmtdevice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.resource.Application;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.core.service.SclService;

import fr.liris.cima.gscl.commons.Device;
import fr.liris.cima.gscl.commons.constants.Constants;
import fr.liris.cima.gscl.commons.util.Utils;
import fr.liris.cima.gscl.device.service.ManagedDeviceService;

public class DeviceManagerImpl implements ManagedDeviceService{

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
	
	public DeviceManagerImpl() {
		
	}
	
	public DeviceManagerImpl(SclService scl){
		SCL = scl;
		devices = new ArrayList<>();
		unknownDevices = new ArrayList<>();
	}
	
	public static void init(SclService scl) {
		SCL = scl;
		devices = new ArrayList<>();
		unknownDevices = new ArrayList<>();
	}

	@Override
	public Device getDevice(String deviceId) {
		for(Device device: devices) {
			if(device.getId().equals(deviceId))
				return device;
		}
		return null;
	}

	@Override
	public Device getDeviceByAddress(String address) {
		for(Device device : devices) {
			String ipAddress = Utils.extractIpAdress(device.getUri());
			if(ipAddress.equals(address)) return device;
		}
		return null;
	}

	@Override
	public void addDevice(Device device) {
		devices.add(device);
		LOGGER.info("Add device ..."+ device.getId());
		LOGGER.info("Add device ..."+ devices.size());
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
	//	createManagerResources("manualconfig", "manual");
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
			if(device.getId().equals(deviceId))
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
	
	public Device getUnknownDevice(String deviceId) {
		for(Device device: unknownDevices) {
			if(device.getId().equals(deviceId))
				return device;
		}
		return null;
	}

}
