package fr.liris.cima.nscl.commons.encoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fr.liris.cima.nscl.commons.Capability;
import fr.liris.cima.nscl.commons.ContactInfo;
import fr.liris.cima.nscl.commons.Device;
import fr.liris.cima.nscl.commons.DeviceDescription;
import fr.liris.cima.nscl.commons.Protocol;
import fr.liris.cima.nscl.commons.constants.Constants;

/**
 * This class a given object to json format.
 * @author madiallo
 *
 */
public class JsonEncoder {

	/**
	 * Encode device to a json string
	 * @param device - a device to encode
	 * @return A string that represent the encodage of this device
	 */
	public static String deviceToJSONStr(Device device) {
		JSONObject obj=new JSONObject();
		obj.put("id", device.getId());
		obj.put("name", device.getName());
		obj.put("uri", device.getUri());
		obj.put("modeConnection", device.getModeConnection());
		obj.put("dateConnection", device.getDateConnection());
		
		
		JSONArray capabilitiesJson =  new JSONArray();
		
		for(Capability capability : device.getCapabilities()) {
		 capabilitiesJson.add(capabilityToJSONStr(capability));
		}
		obj.put("capabilities", capabilitiesJson);
		obj.put("contactInfo", contactInfoToJSONStr(device.getContactInfo()));
		
		return obj.toJSONString();
	}

	/**
	 * Encode contactInfo to a json string
	 * @param contactInfo - a contactInfo to encode
	 * @return A string that represent the encodage of this contactInfo
	 */
	public static String contactInfoToJSONStr(ContactInfo contactInfo) {
		JSONObject obj=new JSONObject();
		obj.put("deviceId", contactInfo.getDeviceId());
		obj.put("cloud_port", contactInfo.getCloud_port());
		return obj.toJSONString();
	}


	/**
	 * Encode devices to a json string
	 * @param devices - a devices list to encode
	 * @return A string that represent the encodage of  devices
	 */
	public static String devicesToJSONStr(List<Device> devices) {
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		String result = "";
		List<String> devicesStr = new ArrayList<>();
		for(Device device : devices) {
			devicesStr.add(deviceToJSONStr(device));
		}
		obj.put("devices", devicesStr);
		return obj.toJSONString();
	}

	public static String allContactInfoToJSONStr(List<Device> devices) {
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		String result = "";
		List<String> devicesStr = new ArrayList<>();
		for(Device device : devices) {
			devicesStr.add(contactInfoToJSONStr(device.getContactInfo()));
		}
		obj.put("devices", devicesStr);
		return obj.toJSONString();
	}

	public static String protocolToJSONStr(Protocol protocol) {
		JSONObject obj=new JSONObject();
		
		obj.put("protocoleName", protocol.getName());
		JSONArray jsonParameters = new JSONArray();
		JSONObject jsonParameter = new JSONObject();

		for(Entry<String, String> entry : protocol.getParameters().entrySet()) {
			jsonParameter.put(entry.getKey(), entry.getValue());
			jsonParameters.add(jsonParameter);
		}
		
		obj.put("parameters", jsonParameters);
		
		return obj.toJSONString();
	}
	
	public static String capabilityToJSONStr(Capability capability) {
		JSONObject obj=new JSONObject();
		
		obj.put("id", capability.getName());
		obj.put("protocol", protocolToJSONStr(capability.getProtocol()));
		JSONArray keyWords = new JSONArray();
		keyWords.addAll(capability.getKeywords());
		
		obj.put("keywords", keyWords);
		
		return obj.toJSONString();
	}
	
	
	


	public static void main(String args[]) {
		
		String s = "\"foo\" is not \"bar\". specials: \b\r\n\f\t\\/";

		  s = JSONObject.escape(s);
		  
		                
		  System.out.println(s);
		  
		ContactInfo contact = new ContactInfo("DEVICE_0", 6000);
		

		DeviceDescription deviceDescription = new DeviceDescription("DEVICE_0", "ev3", "uri", "modeConnection", "dateConnection");

		System.out.println(contactInfoToJSONStr(contact));
		
		Protocol protocol = new Protocol("http");
		protocol.addParameter("method", "post");
		
		System.out.println(protocolToJSONStr(protocol));
		
		Capability capability = new Capability("back");
		capability.setProtocol(protocol);
		capability.addKeyword("ev3");
		capability.addKeyword("robot");
		
		System.out.println(capabilityToJSONStr(capability));
		
		Device device = new Device(deviceDescription, contact);
		device.addCapability(capability);
		System.out.println(deviceToJSONStr(device));
		
		List<Device> devices = new ArrayList<Device>();
		devices.add(device);
		
		System.out.println(devicesToJSONStr(devices));

		


	}
}