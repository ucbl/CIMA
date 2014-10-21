package fr.liris.cima.nscl.controller.encoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fr.liris.cima.nscl.controller.ContactInfo;
import fr.liris.cima.nscl.controller.Device;
import fr.liris.cima.nscl.controller.constants.Constants;

public class JsonEncoder {

	public static String encodeContactInfo(ContactInfo deviceContact) {
		JSONObject obj=new JSONObject();
		obj.put("gatewayAddress", Constants.CIMA_ADDRESS);
		obj.put("deviceId", deviceContact.getDeviceId());
		obj.put("cloud_port", deviceContact.getCloud_port());
		return obj.toJSONString();
	}

	public static String encodeContactInfo(Device device) {
		ContactInfo deviceContact = device.getContactInfo();
		JSONObject obj=new JSONObject();
		obj.put("gatewayAddress", Constants.CIMA_ADDRESS);
		obj.put("deviceId", deviceContact.getDeviceId());
		obj.put("cloud_port", deviceContact.getCloud_port());
		return obj.toJSONString();
	}

	public static String deviceToJSONStr(Device device) {
		JSONObject obj=new JSONObject();
		obj.put("id", device.getId());
		obj.put("protocol", device.getProtocol());
		obj.put("url", device.getUrl());
		obj.put("contactInfo", contactInfoToJSONStr(device.getContactInfo()));
		return obj.toJSONString();
	}

	public static String contactInfoToJSONStr(ContactInfo contactInfo) {
		JSONObject obj=new JSONObject();
		obj.put("deviceId", contactInfo.getDeviceId());
		obj.put("cloud_port", contactInfo.getCloud_port());
		return obj.toJSONString();
	}

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

	public static void main(String args[]) {
		ContactInfo contact = new ContactInfo("DEVICE_0", 6000);
		Device device = new Device("DEVICE_0", "", "", contact);

		List<Device> set = new ArrayList<>();
		set.add(device);
		
		System.out.println(deviceToJSONStr(device));
		System.out.println(contactInfoToJSONStr(contact));
	}
	
	public static String encodeContactInfo(List<Device> devices) {


		JSONArray infos = new JSONArray();
		JSONObject address = new JSONObject();
		StringBuffer sb = new StringBuffer();
		/**	sb.append("{");	         
		sb.append(JSONObject.escape("gatewayAddress"));
		sb.append(":");
		sb.append("\"" + JSONObject.escape(Constants.CIMA_ADDRESS) + "\"");
		sb.append("}");	         
		 */

		//infos.add(sb.toString());

		for(Device device : devices) {
			sb = new StringBuffer();
			sb.append("{");	         
			sb.append(JSONObject.escape("deviceId"));
			sb.append(":");
			sb.append("\"" + JSONObject.escape(device.getId()) + "\"");

			sb.append(",");

			sb.append(JSONObject.escape("cloud_port"));
			sb.append(":");
			sb.append(device.getContactInfo().getCloud_port());

			sb.append("}");

			infos.add(sb.toString());
		}
		return infos.toJSONString().replaceAll("\\\\", "");
	}
}