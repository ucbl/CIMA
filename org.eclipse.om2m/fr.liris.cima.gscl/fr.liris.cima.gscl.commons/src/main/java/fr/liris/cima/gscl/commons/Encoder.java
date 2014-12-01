package fr.liris.cima.gscl.commons;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import obix.Int;
import obix.Obj;
import obix.Str;
import obix.io.ObixDecoder;
import obix.io.ObixEncoder;

/**
 * Encode an given object into xml, json, obix format.
 * @author madiallo
 *
 */
public class Encoder {

	/**
	 * DeviceDescription 
	 * @param deviceDescription
	 * @return
	 */
	public static String deviceDescriptionToXml(DeviceDescription deviceDescription) {

		String xmlFormat = "<device>"+
				"<id>"+deviceDescription.getId()+"</id>"+
				"<name>"+deviceDescription.getName()+"</name>"+
				"<uri>"+deviceDescription.getUri()+"</uri> "+
				"<dateConnection>"+deviceDescription.getDateConnection()+"</dateConnection>"+
				"<modeConnection>"+deviceDescription.getModeConnection()+"</modeConnection>"+
				"</device>";

		return  xmlFormat;
	}

	public static String encodeDeviceDescriptionToObix(DeviceDescription deviceDescription) {

		Obj objDevice = new Obj("device");
		Obj obj = new Obj();

		objDevice.add(new Str("id",deviceDescription.getId()));
		objDevice.add(new Str("name", deviceDescription.getName()));
		objDevice.add(new Str("uri",deviceDescription.getUri()));
		objDevice.add(new Str("modeConnection", deviceDescription.getModeConnection()));
		objDevice.add(new Str("dateConnection", deviceDescription.getDateConnection()));

		return ObixEncoder.toString(objDevice);
	}

	public static Obj encodeDeviceDescriptionToObixObj(DeviceDescription deviceDescription) {
		return ObixDecoder.fromString(encodeDeviceDescriptionToObix(deviceDescription));
	}

	public static String encodeDeviceToObix(Device device) {

		Obj objDevice = new Obj("device");
		Obj obj = new Obj();

		DeviceDescription deviceDescription = device.getDeviceDescription();

		objDevice.add(new Str("id",deviceDescription.getId()));
		objDevice.add(new Str("name", deviceDescription.getName()));
		objDevice.add(new Str("uri",deviceDescription.getUri()));
		objDevice.add(new Str("modeConnection", deviceDescription.getModeConnection()));
		objDevice.add(new Str("dateConnection", deviceDescription.getDateConnection()));

		obix.List obixCapabilities = new obix.List("capabilities");
		for(Capability capability : device.getCapabilities()) {
			obixCapabilities.add(encodeCapabilityToObixObj(capability));
		}

		objDevice.add(obixCapabilities);
		obj.add(objDevice);
		objDevice.add(encodeContactInfoToObixObj(device.getContactInfo()));

		return ObixEncoder.toString(obj);
	}

	public static Obj encodeDeviceToObixObj(Device device) {
		return ObixDecoder.fromString(encodeDeviceToObix(device));
	}
	
	public static String encodeDevicesToObixObj(List<Device> devices) {
		obix.List obixDevices = new obix.List("devices");
		Obj obj = new Obj();
		
		for(Device device : devices) {
			obixDevices.add(encodeDeviceToObixObj(device));
		}
		obj.add(obixDevices);
				
		return ObixEncoder.toString(obj);
	}
	
	
	
	
	public static Obj encodeCapabilityToObixObj(Capability capability) {
		Obj obj = new Obj();

		obj.add(new Str("id",capability.getName()));
		//obj.add(capability.getProtocol().toObj());
		obj.add(encodeProtocolObixObj(capability.getProtocol()));
		obix.List keywords = new obix.List("keywords");
		obix.Str sK = null;
		for(String k : capability.getKeywords()){
			sK = new Str(k);
			keywords.add(sK);
		}
		obj.add(keywords);
		return obj;
	}
	
	public static String encodeCapabilityToObix(Capability capability) {
		return ObixEncoder.toString(encodeCapabilityToObixObj(capability));
	}
	
	public static Obj encodeCapabilityToObixObj(List<Capability> capabilities) {
		obix.List obixCapabilities = new obix.List("capabilities");
		Obj obj = new Obj();
		for(Capability capability : capabilities) {
//			obj = new Obj();
//			obj.add(encodeCapabilityToObixObj(capability));
//			obixCapabilities.add(obj);
			obixCapabilities.add(encodeCapabilityToObixObj(capability));
		}				
		return obixCapabilities;
	}
	public static String encodeCapabilitiesToObix(List<Capability> capabilities) {
		return ObixEncoder.toString(encodeCapabilityToObixObj(capabilities));
	}
	
	public static String encodeContactInfoToObix(ContactInfo contactInfo) {
		return ObixEncoder.toString(encodeContactInfoToObixObj(contactInfo));
	}
	
	public static Obj encodeContactInfoToObixObj(ContactInfo contactInfo) {
		Obj obj = new Obj("contactInfo");
		obj.add(new Str("deviceId",contactInfo.getDeviceId()));
		obj.add(new Int("cloud_port", contactInfo.getCloud_port()));

		return obj;
	}
	
	public static String encodeProtocolToObix(Protocol protocol) {
		return ObixEncoder.toString(encodeProtocolObixObj(protocol));
	}
	
	public static Obj encodeProtocolObixObj(Protocol protocol) {
		Obj objProtocol = new Obj("protocol");
		objProtocol.add(new Str("protocolName", protocol.getName()));
		for(Entry<String, String> entry : protocol.getParameters().entrySet()) {
			objProtocol.add(new Str(entry.getKey(),entry.getValue()));
		}
		return objProtocol;
	}	
	
	public static void main(String args[]) {
		System.out.println(new Date());
		
		
		 // DeviceDescription
		
		DeviceDescription deviceDescription = new DeviceDescription("ev3", "http://192.168.0.02:/infos/", "ip");
		System.out.println(encodeDeviceDescriptionToObix(deviceDescription));
		
		
		
		 // Capability ....
		 
		Capability capability = new Capability("ev3");
		Protocol protocol = new Protocol("http");
		protocol.addParameter("method", "post");
		capability.setProtocol(protocol);
		List<String> k = new ArrayList<>();
		k.add("ev3");
		k.add("back");
		k.add("robot");
		capability.setKeywords(k);
		
	//	System.out.println(encodeCapabilityToObix(capability));
		List<Capability> capabilities = new ArrayList<Capability>();
		capabilities.add(capability);
		
	//	System.out.println(encodeCapabilitiesToObix(capabilities));

		
		
		// Protocol
		
		 // ContactInfo
		 
		ContactInfo contactInfo = new ContactInfo("DEVICE_0", 6001);
		
	//	System.out.println(encodeContactInfoToObix(contactInfo));
		
		Device device = new Device(deviceDescription);
		device.addCapability(capability);
		System.out.println(encodeDeviceToObix(device));
		
//		String xmlFormat = "<device><id>DEVICE_0</id><name>ev3</name><uri>http://192.168.0.02:/infos/</uri> "
//				+ "<modeConnection>ip</modeConnection></device>";
//		System.out.println(Parser.parseXmlDevice(xmlFormat));
//		System.out.println(deviceMetaDataToXml(device));
	}
}