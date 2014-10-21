package fr.liris.cima.nscl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.resource.Application;
import org.eclipse.om2m.commons.resource.AttachedDevice;
import org.eclipse.om2m.commons.resource.AttachedDevices;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.SearchStrings;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.commons.utils.DateConverter;
import org.eclipse.om2m.core.service.SclService;

import fr.liris.cima.nscl.avatarbuilder.Avatar;
import fr.liris.cima.nscl.avatarbuilder.service.AvatarService;
import fr.liris.cima.nscl.controller.subscriber.ClientSubscriber;

public class DeviceManager {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(DeviceManager.class);
	/** Sclbase id */
	public final static String SCLID = System.getProperty("org.eclipse.om2m.sclBaseId","");
	/** Admin requesting entity */
	static String REQENTITY = System.getProperty("org.eclipse.om2m.adminRequestingEntity","");
	/** Generic create method name */
	public final static String METHOD_CREATE = "CREATE";
	/** Generic execute method name */
	public final static String METHOD_EXECUTE = "EXECUTE";
	/** State container id */
	public final static String DATA = "DATA";
	/** Descriptor container id */
	public final static String DESC = "DESCRIPTOR";

	/** Capabilities container */
	public final static String CAPABS = "CAPABILITIES";
	
	public static String gateContact;
	
	/** Discovered SCL service*/
	static SclService SCL;
	static AvatarService avatarService;

	static List<Device> devices;
	static List<Avatar> avatars;
	
	static Set<ClientSubscriber> subscribers;
	
	static ContactInfo gatewayInfo;

	public DeviceManager(SclService scl, AvatarService avtService) {
		SCL = scl;
		devices = new ArrayList<>();
		avatars = new ArrayList<>();
		avatarService = avtService;
		subscribers = new HashSet<>();
	}

	public static void setGatewayInfo(ContactInfo gInfo) {
		gatewayInfo = gInfo;
	}
	public static Device getDevice(String deviceId) {
		for(Device device: devices) {
			if(device.getId().equals(deviceId))
				return device;
		}
		return null;
	}

	public static boolean addSubscriber(ClientSubscriber subscriber) {
		return subscribers.add(subscriber);
	}
	
	public static boolean removeSubscriber(ClientSubscriber subscriber) {
		return subscribers.remove(subscriber);
	}
	
	public static boolean addDevice(Device device) {		
		return devices.add(device);
	}

	public static void removeDevice(Device device) {
		devices.remove(device);
	}
	
	public static Device removeDevice(String deviceId) {
		Device device = getDevice(deviceId);
		if(device != null) {
			devices.remove(device);
		}
		return device;
	}
	
	public static void addAvatar(Avatar avatar) {
		avatars.add(avatar);
	}
	
	public static void removeAvatar(Avatar avatar) {
		avatars.remove(avatar);
	}
	
	public static void createAvatar(String id, String protocol, String uri) {
		Avatar avatar = avatarService.createAvatar(id, protocol, uri);
		avatars.add(avatar);
		LOGGER.info("Avatar created successfuly ");
		LOGGER.info(avatar);
	}
	
	/**
	 * Starts monitoring and creating resources on the SCL
	 */
	public void start() {
		LOGGER.info("Devices waiting for attachement..");
		createDeviceResources("CIMANSCL", "devices");
	}

	public static void stop() {

	}

	public void createDeviceResources(String appId, String aPoCPath) {
		// Create the Application resource
		ResponseConfirm response = SCL.doRequest(new RequestIndication(METHOD_CREATE,SCLID+"/applications",REQENTITY,new Application(appId,aPoCPath)));
		// Create Application sub-resources only if application not yet created
		if(response.getStatusCode().equals(StatusCode.STATUS_CREATED)) {
			// Create DESCRIPTOR container sub-resource
			SCL.doRequest(new RequestIndication(METHOD_CREATE,SCLID+"/applications/"+appId+"/containers",REQENTITY,new Container(DESC)));

			// Create CAPABILITIES container sub-resource
			SCL.doRequest(new RequestIndication(METHOD_CREATE,SCLID+"/applications/"+appId+"/containers",REQENTITY,new Container(CAPABS)));

			String content, targetID;
			// Create DESCRIPTION contentInstance on the DESCRIPTOR container resource
			content = "";
			//Device.getDescriptorRep(SCLID, appId);
			targetID= SCLID+"/applications/"+appId+"/containers/"+DESC+"/contentInstances";
			SCL.doRequest(new RequestIndication(METHOD_CREATE,targetID,REQENTITY,new ContentInstance(content.getBytes())));
				

		}
	}

	public static void execute(String localTarget) {
		SCL.doRequest(new RequestIndication(METHOD_EXECUTE,SCLID+"/"+localTarget,REQENTITY, ""));
	}
	
}
