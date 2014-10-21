package fr.liris.cima.nscl.commons.encoder;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fr.liris.cima.nscl.commons.ContactInfo;
import fr.liris.cima.nscl.commons.Device;
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
		obj.put("protocol", device.getProtocol());
		obj.put("url", device.getUrl());
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

	
	public static void main(String args[]) {
		ContactInfo contact = new ContactInfo("DEVICE_0", 6000);
		Device device = new Device("DEVICE_0", "", "", contact);

		List<Device> set = new ArrayList<>();
		set.add(device);

		System.out.println(deviceToJSONStr(device));
		System.out.println(contactInfoToJSONStr(contact));
	}
}