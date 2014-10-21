package fr.liris.cima.nscl.mgmtdevice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.resource.Application;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.core.service.SclService;

import fr.liris.cima.nscl.commons.Device;
import fr.liris.cima.nscl.commons.constants.Constants;
import fr.liris.cima.nscl.commons.subscriber.ClientSubscriber;
import fr.liris.cima.nscl.device.service.ManagedDeviceService;

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
	
	static Set<ClientSubscriber> subscribers;


	public DeviceManagerImpl() {
	}
	
	public DeviceManagerImpl(SclService scl){
		SCL = scl;
		devices = new ArrayList<>();
		subscribers = new HashSet<>();
	}
	
	public static void init(SclService scl) {
		SCL = scl;
		devices = new ArrayList<>();
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
		
		return null;
	}

	@Override
	public void addDevice(Device device) {
		devices.add(device);
		LOGGER.info("Add device ..."+ device.getId());
		LOGGER.info("Add device ..."+ devices.size());
	}

	@Override
	public Device removeDevice(String deviceId) {
		Device  device = getDevice(deviceId);
		if(device != null) {
			devices.remove(device);
		}
		return device;
	}
	@Override
	public void removeDevice(Device device) {
		devices.remove(device);
	}
	
	@Override
	public void start() {
		LOGGER.info("Devices waiting for attachement..");
		createManagerResources("CIMANSCL", "devices");
	}
	
	@Override
	public List<Device> getDevices() {
		return devices;
	}
	
	@Override
	public  boolean addSubscriber(ClientSubscriber subscriber) {
		return subscribers.add(subscriber);
	}
	
	@Override
	public  boolean removeSubscriber(ClientSubscriber subscriber) {
		return subscribers.remove(subscriber);
	}
	
	@Override
	public Set<ClientSubscriber> getSubscribers() {
		return subscribers;
	}
	
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

}