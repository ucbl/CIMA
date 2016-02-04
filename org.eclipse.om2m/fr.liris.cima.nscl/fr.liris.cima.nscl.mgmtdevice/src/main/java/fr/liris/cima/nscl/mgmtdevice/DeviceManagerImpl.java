package fr.liris.cima.nscl.mgmtdevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.log.*;
import org.osgi.framework.FrameworkUtil;

/**
 * An implementation of ManagedDeviceService, to manage device service and capability using a scl service
 */
public class DeviceManagerImpl implements ManagedDeviceService{

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(DeviceManagerImpl.class);

	/** Logger OSGI*/
	private static ServiceTracker logServiceTracker;
	private static LogService logservice;

	public final static String DATA = "DATA";
	/** Descriptor container id */
	public final static String DESC = "DESCRIPTOR";

	/** Capabilities container */
	public final static String CAPABS = "CAPABILITIES";

	/** Discovered SCL service*/
	static SclService SCL;

	/** List of devices*/
	static List<Device> devices;

	/**
	 * Set of client suscribers
	 */
	static Set<ClientSubscriber> subscribers;

	// deviceId, contactInfo
	static Map<String, String> mapContactInfos;


	public DeviceManagerImpl() {
	}

	/**
	 * Instanciate a DeviceManagerImpl
	 * @param scl Scl Service
	 */
	public DeviceManagerImpl(SclService scl){
		SCL = scl;
		devices = new ArrayList<>();
		subscribers = new HashSet<>();
		mapContactInfos = new HashMap<String, String>();
	}

	public static void init(SclService scl) {
		SCL = scl;
		devices = new ArrayList<>();
	}

	/**
	 * Get a device by giving the deviceId
	 * @param deviceId the device ID
	 * @return the device
	 */
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

	/**
	 * Add a device into the device manager
	 * @param device the device
	 */
	@Override
	public void addDevice(Device device) {

		logServiceTracker = new ServiceTracker(FrameworkUtil.getBundle(DeviceManagerImpl.class).getBundleContext(), org.osgi.service.log.LogService.class.getName(), null);
			logServiceTracker.open();
			logservice = (LogService) logServiceTracker.getService();


		devices.add(device);
		LOGGER.info("Add device ..." + device.getId());
		logservice.log(LogService.LOG_ERROR, "Add device ..." + device.getId());

		LOGGER.info("Add device ..." + devices.size());
		logservice.log(LogService.LOG_ERROR, "Add device ..." + devices.size());

	}

	/**
	 * Remove a device from the device manager by giving its id
	 * @param deviceId the device ID
	 * @return the device if it has not been removed, else return true.
	 */
	@Override
	public Device removeDevice(String deviceId) {
		Device  device = getDevice(deviceId);
		if(device != null) {
			devices.remove(device);
		}
		return device;
	}

	/**
	 * Remove a device from the device manager
	 * @param device the device
	 */
	@Override
	public void removeDevice(Device device) {
		devices.remove(device);
	}

	@Override
	public void start() {


		logServiceTracker = new ServiceTracker(FrameworkUtil.getBundle(DeviceManagerImpl.class).getBundleContext(), org.osgi.service.log.LogService.class.getName(), null);
		logServiceTracker.open();
		logservice = (LogService) logServiceTracker.getService();
		LOGGER.info("Devices waiting for attachement..");
				logservice.log(LogService.LOG_ERROR, "Devices waiting for attachement..");
		createManagerResources("CIMANSCL", "devices");
		createManagerResources("CIMA", "administration");
	}

	@Override
	public List<Device> getDevices() {
		return devices;
	}

	/**
	 * Add a client subscriber to the device manager
	 * @param subscriber the client suscriber
	 * @return return 1 if the suscriber already exists, 0 if the suscriber has been added and 2 if nothing happened
	 */
	@Override
	public  int addSubscriber(ClientSubscriber subscriber) {
		if(subscribers.contains(subscriber)) return 1;
		else if(subscribers.add(subscriber)) return 0;
		else return 2;
	}

	/**
	 * Remove a client subscriber from the device manager
	 * @param subscriber the client suscriber
	 * @return 1 if the suscriber has been removed, else return 0
	 */
	@Override
	public  boolean removeSubscriber(ClientSubscriber subscriber) {
		return subscribers.remove(subscriber);
	}

	/**
	 * Get all the client subscribers of the device subscriber
	 * @return a set of client subscriber
	 */
	@Override
	public Set<ClientSubscriber> getSubscribers() {
		return subscribers;
	}

	/**
	 * Create the application resource to manage
	 * @param appId an application ID
	 * @param aPoCPath application point of contact
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

}
