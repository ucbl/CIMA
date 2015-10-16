package fr.liris.cima.gscl.commons;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.liris.cima.gscl.commons.parser.Parser;
import fr.liris.cima.gscl.commons.util.Utils;
import obix.Bool;
import obix.Int;
import obix.Obj;
import obix.Str;
import obix.io.ObixDecoder;
import obix.io.ObixEncoder;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.*;

/**
 * Encode an given object into xml, json, obix format.
 * @author madiallo
 *
 */
public class Encoder {

	private static Logger LOGGER = Logger.getLogger(Encoder.class.getName());
	private  static  Handler fh ;


	static Map<String, List<Capability>>mapPortManager = new HashMap<>();

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
		try{
			fh = new FileHandler("log/gsclCommons.log", true);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


		LOGGER.info("******************dans encoder ***********");
		Obj objDevice = new Obj("device");
		Obj obj = new Obj();

		DeviceDescription deviceDescription = device.getDeviceDescription();
		System.out.println("device desc : " + deviceDescription);
		objDevice.add(new Str("id",deviceDescription.getId()));
		objDevice.add(new Str("name", deviceDescription.getName()));
		objDevice.add(new Str("uri",deviceDescription.getUri()));
		objDevice.add(new Str("modeConnection", deviceDescription.getModeConnection()));
		objDevice.add(new Str("dateConnection", deviceDescription.getDateConnection()));
		objDevice.add(new Str("configuration", device.getConfiguration()));
		objDevice.add(new Bool("known", device.isKnown()));


//		LOGGER.info("******************dans encoder capabilities ***********");

		obix.List obixCapabilities = new obix.List("capabilities");
		for(Capability capability : device.getCapabilities()) {
			obixCapabilities.add(encodeCapabilityToObixObj(capability));
		}

		objDevice.add(obixCapabilities);
		obj.add(objDevice);
		//objDevice.add(encodeContactInfoToObixObj(device.getContactInfo()));
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
		try{
			fh = new FileHandler("log/gsclCommons.log", true);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


		Obj obj = new Obj();

		obj.add(new Str("id",capability.getName()));
		obj.add(new Int("cloudPort",capability.getCloudPort()));
		obj.add(new Str("configuration", ((capability.getConfiguration()!=null)?capability.getConfiguration():"automatic")));
		LOGGER.info("***************** ADD a Capability");
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
			System.out.println("parameter = '"+entry.getValue().trim()+"'");
			objProtocol.add(new Str(entry.getKey(),entry.getValue().trim()));
		}
		return objProtocol;
	}

	@SuppressWarnings("unchecked")
	public static JSONObject encodeToJson(Map<String, Object> parameters) {

		JSONObject jsonObject = new JSONObject();

		for(Map.Entry<String, Object> entry : parameters.entrySet() ) {
			jsonObject.put( entry.getKey(), entry.getValue());
		}

		return jsonObject;
	}

	public static void main(String args[]) {
		System.out.println(new Date());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", "nom");
		parameters.put("port", 8080);

		//System.out.println(encodeToJson(parameters));

		String representation = "<device>"+
				"<name>ev3</name>"+
				"<modeConnection>http</modeConnection>"+
				"<dateConnection>mercredi, oct. 22, 2014 13:52:20 PM</dateConnection>"+
				"<uri>192.168.43.34</uri> "+
				"<capabilities> "
				+ "<capability>"
				+ "<id> ev3back</id>"
				+ "<protocol>"
				+ "<protocolName>" + "http" + "</protocolName>"
				+ "<method> "+ "PUT" +"</method>"
				+ "<port>8080</port>"
				+ "<uri>" + "/capabilities" + "</uri>"
				+ "<body>body</body>"
				+ "</protocol>"
				+ "<keywords>"
				+ "<keyword>ev3</keyword>"
				+ "</keywords>"
				+ "</capability>"
				+ "</capabilities>"
				+"</device>";

		Device device = Parser.parseXmlToDevice(representation);
		//	System.out.println("device = "+device);
		///   System.out.println(encodeDeviceToObix(device));

		//	System.exit(0);
		// DeviceDescription

		DeviceDescription deviceDescription = new DeviceDescription("ev3", "http://192.168.0.02:/infos/", "ip");
		//System.out.println(encodeDeviceDescriptionToObix(deviceDescription));



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



		System.out.println("*************************************************");
		System.out.println(encodeDeviceToJSONPortForwarding(device));
//		System.out.println(mapPortManager);
		// Protocol

		// ContactInfo

		ContactInfo contactInfo = new ContactInfo("DEVICE_0", 6001);

		//	System.out.println(encodeContactInfoToObix(contactInfo));

				 device = new Device(deviceDescription);
				device.addCapability(capability);
				System.out.println(encodeDeviceToObix(device));

		//		String xmlFormat = "<device><id>DEVICE_0</id><name>ev3</name><uri>http://192.168.0.02:/infos/</uri> "
		//				+ "<modeConnection>ip</modeConnection></device>";
		//		System.out.println(Parser.parseXmlDevice(xmlFormat));
		//		System.out.println(deviceMetaDataToXml(device));
//
//		List<String> listeIds = new ArrayList<>();
//		listeIds.add("DEVICE_0_8080");
//		System.out.println(JsonDeviceDisconnectionInfoToPortForwading(listeIds));
	}


	public static String encodeDeviceToJSONPortForwarding(Device device) {
		Map<String, Object> parameters = new HashMap<>();

		JSONParser parser = new JSONParser();

		int countNbconnectedCapability = 0;

		JSONObject jsonObjectConnection = new JSONObject();
		JSONArray jsonArrayConnection = new JSONArray();

		List<Capability> capabilities = device.getCapabilities();

		for(Capability capability : capabilities) {
			int port = Integer.parseInt(capability.getProtocol().getParameterValue("port"));
			if (mapPortManager.containsKey(device.getId()+ "_"+port)) {
				mapPortManager.get(device.getId()+ "_"+port).add(capability);
			}
			else {

				parameters.put("id", device.getId()+ "_"+port);
				parameters.put("ip", Utils.extractIpAdress(device.getUri()));
				parameters.put("port", ""+port);
				parameters.put("transport", "TCP");

				jsonArrayConnection.add(Encoder.encodeToJson(parameters));

				String id = device.getId()+ "_"+port;
				List<Capability> subList = new ArrayList<>();
				subList.add(capability);
				mapPortManager.put(id, subList);
			}
		}
		jsonObjectConnection.put("c", jsonArrayConnection);
		return jsonObjectConnection.toJSONString();
	}

	public static Map<String, Integer> decodeJson(String jsonString) {
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) new JSONParser().parse(jsonString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		String id;
		int port;
		Map<String, Integer> map  = new HashMap<String, Integer>();

		JSONArray jsonArray = (JSONArray) jsonObject.get("c");
		for(Object object : jsonArray) {
			id = (String) ((JSONObject)object).get("id");
			port = (int) ((JSONObject)object).get("port");

			map.put(id, port);
		}
		return map;
	}

	public static String JsonDeviceDisconnectionInfoToPortForwading(List<String> ids) {
		JSONObject jsonDisconnection = new JSONObject();
		JSONArray array = new JSONArray();

		for(String id : ids) {
			array.add(id);
		}
		jsonDisconnection.put("d", array);

		return jsonDisconnection.toJSONString();
	}

	public static Map<String, String> decodeResponseDisconnection(String jsonString) {

		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) new JSONParser().parse(jsonString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		String id;
		String status;
		Map<String, String> map  = new HashMap<>();

		JSONArray jsonArray = (JSONArray) jsonObject.get("d");
		for(Object object : jsonArray) {
			id = (String) ((JSONObject)object).get("id");
			status = (String) ((JSONObject)object).get("status");

			map.put(id, status);
		}
		return map;
	}
}
