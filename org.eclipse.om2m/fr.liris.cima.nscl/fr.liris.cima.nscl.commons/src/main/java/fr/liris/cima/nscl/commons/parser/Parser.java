package fr.liris.cima.nscl.commons.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import obix.xml.XElem;
import obix.xml.XParser;

import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Iterator;

import obix.Int;
import obix.Obj;
import obix.Str;
import obix.io.ObixDecoder;
import obix.io.ObixEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.liris.cima.comm.protocol.AbstractProtocol;
import fr.liris.cima.comm.protocol.ProtocolResolver;
import fr.liris.cima.nscl.commons.Protocol;
import fr.liris.cima.nscl.commons.Capability;
import fr.liris.cima.nscl.commons.Device;
import fr.liris.cima.nscl.commons.DeviceDescription;
import fr.liris.cima.nscl.commons.ContactInfo;
import fr.liris.cima.nscl.commons.constants.Configuration;
import fr.liris.cima.nscl.commons.subscriber.ClientSubscriber;

import java.util.Iterator;

public class Parser {
	
	public static String parseJSONToObixStringDevice(String jsonString) {
		Obj ret = parseJSONToObixDevice(jsonString);
		String stret = ObixEncoder.toString(ret);
		return stret;
	}

	public static Obj parseJSONToObixDevice(String jsonString) {
		// JSON
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(jsonString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return parseJSONToObixDevice(jsonObject);
	}

	public static Obj parseJSONToObixDevice(JSONObject jsonObject) {
		// oBIX
		Obj obj_Device = new Obj("Device");

		obj_Device.add(new Str("id", (String) jsonObject.get("id")));
		obj_Device.add(new Str("name", (String) jsonObject.get("name")));
		obj_Device.add(new Str("uri", (String) jsonObject.get("uri")));
		obj_Device.add(new Str("dateConnection", (String) jsonObject
				.get("dateConnection")));
		obj_Device.add(new Str("modeConnection", (String) jsonObject
				.get("modeConnection")));
		obj_Device.add(new Str("configuration", ((jsonObject.get("configuration")!= null)?(String)jsonObject.get("configuration"):"manual")));

		obix.List list = new obix.List("Capabilities");

		try {
			JSONArray capabilities = (JSONArray) jsonObject.get("capabilities");
			Iterator capability = capabilities.iterator();
			int i = 0;

			while (capability.hasNext()) {
				JSONObject capability_tmp = (JSONObject) capability.next();
				list.add(parseJSONToObixCapability(capability_tmp));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		obj_Device.add(list);
		return obj_Device;
	}

	public static String parseJSONToObixStringCapability(String jsonString) {
		Obj ret = parseJSONToObixCapability(jsonString);
		String stret = ObixEncoder.toString(ret);
		return stret;
	}
	
	public static Obj parseJSONToObixCapability(String jsonString) {
		// JSON
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(jsonString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return parseJSONToObixCapability(jsonObject);
	}

	public static Obj parseJSONToObixCapability(JSONObject capability) {
		Obj obj_capability = new Obj();

		try {
			String name = capability.get("id").toString();
			obj_capability.add(new Str("id", (String) capability.get("id")));
			obj_capability.add(new Int("cloudPort", Integer.valueOf((String) ((capability.get("cloudPort") != null)?capability.get("cloudPort"):"-1") )));
			obj_capability.add(new Str("configuration", ((capability.get("configuration") != null)?(String) capability.get("configuration"):"manual") ));

			
			obj_capability.add(parseJSONToObixProtocol((JSONObject) capability
					.get("protocol")));
			
			obix.List keywords = new obix.List("keywords");
			JSONArray jsonKeywords = (JSONArray) capability.get("keywords");
			if(jsonKeywords != null){
				for(Object k : jsonKeywords.toArray()){
					keywords.add(new Str((String) k));
				}
			}
			obj_capability.add(keywords);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj_capability;
	}

	public static Obj parseJSONToObixProtocol(String jsonString) {
		// JSON
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(jsonString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return parseJSONToObixProtocol(jsonObject);
	}

	public static Obj parseJSONToObixProtocol(JSONObject protocol_info) {

		Obj obj_Protocol = new Obj();

		try {
			obj_Protocol.setName("protocol");
			obj_Protocol.add(new Str("protocolName", (String) protocol_info.get("protocolName")));
			JSONArray parameters = (JSONArray) protocol_info.get("parameters");

			Iterator parameter = parameters.iterator();
			while (parameter.hasNext()) {
				JSONObject parameter_tmp = (JSONObject) parameter.next();
				String nam = (String) parameter_tmp.get("name");
				String value = (String) parameter_tmp.get("value");
				obj_Protocol.add(new Str(nam, value));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj_Protocol;
	}

	public static String parseObixToJSONStringProtocol(String obj_info) {
		return parseObixToJSONProtocol(obj_info).toJSONString();
	}

	public static JSONObject parseObixToJSONProtocol(String obj_info) {

		JSONObject jsonObject = new JSONObject();
		try {
			SAXBuilder sxb = new SAXBuilder();
			Document document = sxb.build(new StringReader(obj_info));

			Element racine = document.getRootElement();
			List list_obj = racine.getChildren();
			Iterator noeud = list_obj.iterator();
			JSONArray json_paramets = new JSONArray();
			while (noeud.hasNext()) {
				Element courant = (Element) noeud.next();
				// System.out.println(courant.getChildren());
				JSONObject json_paramet = new JSONObject();
				if (!courant.getAttributeValue("name").equalsIgnoreCase(
						"protocolName")) {
					// System.out.println("**"+courant.getAttributeValue("name")+"   "+courant.getAttributeValue("val")+"***");
					json_paramet.put("name", courant.getAttributeValue("name"));
					json_paramet.put("value", courant.getAttributeValue("val"));
					json_paramets.add(json_paramet);
				} else {
					// System.out.println("**"+courant.getAttributeValue("name")+"   "+courant.getAttributeValue("val")+"***");
					jsonObject.put(courant.getAttributeValue("name"),
							courant.getAttributeValue("val"));
				}

			}
			jsonObject.put("parameters", json_paramets);
			System.out.println("**"+jsonObject.toJSONString()+"***");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static String parseObixToJSONStringCapabilities(String obj_info) {
		return parseObixToJSONCapabilities(obj_info).toJSONString();
	}

	public static JSONArray parseObixToJSONCapabilities(String obj_info) {
		JSONArray jsonObject = new JSONArray();
		try {
			SAXBuilder sxb = new SAXBuilder();
			Document document = sxb.build(new StringReader(obj_info));
			Element racine = document.getRootElement();
			List<Element> list_obj = racine.getChildren();
			Iterator<Element> noeudObj = list_obj.iterator();
			Element courant;
			String s;
			while (noeudObj.hasNext()) {
				courant = (Element) noeudObj.next();
				s = new XMLOutputter()
				.outputString(courant);
				jsonObject.add(parseObixToJSONCapability(s));
			}
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	public static String parseObixToJSONStringCapability(String obj_info) {
		return parseObixToJSONCapability(obj_info).toJSONString();
	}

	public static JSONObject parseObixToJSONCapability(String obj_info) {

		JSONObject jsonObject = new JSONObject();
		try {
			SAXBuilder sxb = new SAXBuilder();
			Document document = sxb.build(new StringReader(obj_info));
			Element racine = document.getRootElement();
			List<Element> list_obj = racine.getChildren();
			Iterator<Element> noeud = list_obj.iterator();
			while (noeud.hasNext()) {
				Element courant = (Element) noeud.next();
				if (courant.getAttributeValue("name") != null && courant.getAttributeValue("name").equalsIgnoreCase("keywords")) {
					List<Element> xmlKeywords = courant.getChildren();
					JSONArray keywords = new JSONArray();
					for(Element e : xmlKeywords){
						keywords.add(e.getAttributeValue("val"));
					}
				} else if(courant.getAttributeValue("name") != null && courant.getAttributeValue("name").equalsIgnoreCase("protocol")){
					String s = new XMLOutputter().outputString(courant);
					jsonObject.put("protocol", parseObixToJSONProtocol(s));

				} else {
					jsonObject.put(courant.getAttributeValue("name"),
							courant.getAttributeValue("val"));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static String parseObixToJSONStringDevices(String obj_info) {
		return parseObixToJSONDevices(obj_info).toJSONString();
	}

	public static JSONArray parseObixToJSONDevices(String obj_info) {
		JSONArray jsonDevices = new JSONArray();
		try {
			SAXBuilder sxb = new SAXBuilder();
			Document document = sxb.build(new StringReader(obj_info));
			Element racine = document.getRootElement();
			List<Element> list_obj = racine.getChildren().get(0).getChildren();
			Iterator<Element> noeudObj = list_obj.iterator();
			Element objCourant;
			String s;
			while (noeudObj.hasNext()) {
				objCourant = ((Element) noeudObj.next()).getChild("obj");
				s = new XMLOutputter().outputString(objCourant);
				System.out.println("************************* device : " + s + "*************************");
				jsonDevices.add(parseObixToJSONDevice(s));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonDevices;
	}

	public static String parseObixToJSONStringDevice(String obj_info) {
		return parseObixToJSONDevice(obj_info).toJSONString();
	}

	public static JSONObject parseObixToJSONDevice(String obj_info) {

		JSONObject jsonDeviceObject = new JSONObject();
		try {
			SAXBuilder sxb = new SAXBuilder();
			Document document = sxb.build(new StringReader(obj_info));
			Element racine = document.getRootElement();
			List<Element> list_capabilities;
			Iterator<Element> noeudAttr, noeud_capabilities;
			JSONArray jsonCapabilities = new JSONArray();
			Element objCourant = racine,attrCourant;
			noeudAttr = objCourant.getChildren().iterator();
			while (noeudAttr.hasNext()){
				attrCourant = (Element) noeudAttr.next();
				if (attrCourant.getAttributeValue("name") != null && !attrCourant.getAttributeValue("name").equalsIgnoreCase("Capabilities")) {
					jsonDeviceObject.put(attrCourant.getAttributeValue("name"), attrCourant.getAttributeValue("val"));
				} else {
					list_capabilities = attrCourant.getChildren();
					noeud_capabilities = list_capabilities.iterator();

					while (noeud_capabilities.hasNext()) {
						Element courant_capabilities = (Element) noeud_capabilities
								.next();
						String s = new XMLOutputter()
						.outputString(courant_capabilities);
						jsonCapabilities.add(parseObixToJSONCapability(s));
					}
					jsonDeviceObject.put("capabilities", jsonCapabilities);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonDeviceObject;
	}
	

	public static String parseProtocolsToJSONString(ProtocolResolver protocolResolver) {
		JSONArray jsonProtocols = new JSONArray();
		
		Map<String, Class<? extends AbstractProtocol>> listProtocols = protocolResolver.getAllProtocol();
		JSONObject jsonProtocol = null;
		JSONArray jsonParameters = null;
		JSONObject jsonParameter = null;
		for(String protocol : listProtocols.keySet()){
			jsonProtocol = new JSONObject();
			jsonParameters = new JSONArray();
			
			jsonProtocol.put("protocolName", protocol);
			for(Field param : listProtocols.get(protocol).getDeclaredFields()){
				jsonParameter = new JSONObject();
				
				if(!param.getName().contains("this$")){
					System.out.println("ajout de " + param.getName());
					jsonParameter.put("name", param.getName());
					jsonParameter.put("value", "");
					
					jsonParameters.add(jsonParameter);
				}
			}
			jsonProtocol.put("parameters", jsonParameters);
			
			jsonProtocols.add(jsonProtocol);
		}
		
		return jsonProtocols.toJSONString();
	}
	/*
	public static Device parseObixToDevice(String obixFormat) {

		Device device = null;
		try {
			XParser parser = XParser.make(obixFormat.trim());
			String id = null, url = null, protocol = null;

			XElem root = parser.parse();
			XElem deviceElem = root.elem(0);
			XElem contactInfoElem = root.elem(1);

			// /
			//  Parse device part
			//  
			for (XElem xElem : deviceElem.elems()) {
				if (xElem.attrValue(0).equals("id")) {
					id = xElem.attrValue(1);
				}
				if (xElem.attrValue(0).equals("protocol")) {
					protocol = xElem.attrValue(1);
				}
				if (xElem.attrValue(0).equals("url")) {
					url = xElem.attrValue(1);
				}

			}
			String deviceId = null;
			int cloud_port = 0;

	//		
	//		  Parse contactInfo part
	//		 
			for (XElem xElem : contactInfoElem.elems()) {
				if (xElem.attrValue(0).equals("deviceId")) {
					deviceId = xElem.attrValue(1);
				}
				if (xElem.attrValue(0).equals("cloud_port")) {
					cloud_port = Integer.parseInt(xElem.attrValue(1));
				}
			}
			if (id != null && protocol != null && url != null) {
				device = new Device(id, url, protocol, new ContactInfo(
						deviceId, cloud_port));
				System.out.println(device);
			}
			return device;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return device;

	}
	 */
	
	public static Capability parseObixToCapability(String obixFormat) {

		Device device = null;
		String id = "",  name = "", uri = "",  modeConnection=""; 

		Protocol protocol = new Protocol();
		Date dateConnection = null;

		Obj capabilityObj = ObixDecoder.fromString(obixFormat);

		Obj protocolObj = capabilityObj.get("protocol");
		protocol.setName(protocolObj.get("protocolName").getStr());
		protocol.addParameter("method", protocolObj.get("method").getStr());
		protocol.addParameter("port", protocolObj.get("port").getStr());
		protocol.addParameter("uri", protocolObj.get("uri").getStr());

		int cloudPort = (int) capabilityObj.get("cloudPort").getInt();
		String configuration = (String) capabilityObj.get("configuration").getStr();
		
                
		Obj [] objs = capabilityObj.get("keywords").list();
		List<String> keywords = new ArrayList<>();
		for(Obj o : objs){
			keywords.add(o.getStr());
		}

		name = capabilityObj.get("id").getStr();
		Capability capability = new Capability(name, protocol, keywords,cloudPort);
		capability.setConfiguration(configuration);
		
		return capability;
	}
	
	public static Device parseObixToDevice(String obixFormat) {
		Device device;
		List<Capability> capabilities = new ArrayList<Capability>();
		String id = null, name = null, uri = null, modeConnection = null, dateConnection = null, configuration;
		boolean known;
		
		ContactInfo contactInfo = null;

		Obj objRoot = ObixDecoder.fromString(obixFormat);
		Obj ObjDevice = objRoot.get("device");

		id = ObjDevice.get("id").getStr();
		name = ObjDevice.get("name").getStr();
		uri = ObjDevice.get("uri").getStr();
		modeConnection = ObjDevice.get("modeConnection").getStr();
		dateConnection  = ObjDevice.get("dateConnection").getStr();
		configuration = ObjDevice.get("configuration").getStr();
		known =  ObjDevice.get("known").getBool();

		DeviceDescription deviceDescription = new DeviceDescription();
		deviceDescription.setId(id);
		deviceDescription.setName(name);
		deviceDescription.setUri(uri);
		deviceDescription.setModeConnection(modeConnection);

		obix.List obixCapabilities = (obix.List)ObjDevice.get("capabilities");

		if(obixCapabilities != null) {
			for(Obj objCapability : obixCapabilities.list()) {
				Capability capability = parseObixToCapability(ObixEncoder.toString(objCapability));
				if(capability != null) {
					capabilities.add(capability);
				}
			}
		}
		
		Obj objContactInfo = ObjDevice.get("contactInfo");
		if(objContactInfo != null) {
			System.out.println(objContactInfo.get("cloud_port"));
			long cloudPort = objContactInfo.get("cloud_port").getInt();
			contactInfo = new ContactInfo(id, (int)cloudPort);
		}
		
		device = new Device(deviceDescription);
		device.setCapabilities(capabilities);
		device.setConfiguration(configuration);
		device.setKnown(known);
		return device;	
	}

	public static ContactInfo parseObixToContactInfo(String obixFormat) {

		try {
			XParser parser = XParser.make(obixFormat);
			String deviceId = null;
			int cloud_port = 0;
			XElem root = parser.parse();
			XElem gatewayElem = root.elem(1);

			/**
			 * Parse contactInfo part
			 */
			for (XElem xElem : gatewayElem.elems()) {
				if (xElem.attrValue(0).equals("deviceId")) {
					deviceId = xElem.attrValue(1);
				}
				if (xElem.attrValue(0).equals("cloud_port")) {
					cloud_port = Integer.parseInt(xElem.attrValue(1));
				}
			}

			ContactInfo contactInfo = new ContactInfo(deviceId, cloud_port);
			System.out.println(contactInfo);

			return contactInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * public static Device parseXmlToDevice(String representation) {
	 * 
	 * try { SAXBuilder sb = new SAXBuilder(); Document doc = sb.build(new
	 * StringReader(representation)); Element root = doc.getRootElement();
	 * String url =""; String protocol=""; String id = "";
	 * 
	 * Element deviceElement = root.getChild("device");
	 * 
	 * List<Element> childrenElement = deviceElement.getChildren(); for(Element
	 * element : childrenElement) { if(element.getName().equals("id")) { id =
	 * element.getText(); } if(element.getName().equals("url")) { url =
	 * element.getText(); } if(element.getName().equals("protocol")) { protocol
	 * = element.getText(); } }
	 * 
	 * 
	 * return new Device(id, url, protocol); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return null;
	 * 
	 * }
	 **/
	/*
	 * public static ContactInfo parseXmlToGatewayInfo(String representation) {
	 * SAXBuilder sb = new SAXBuilder(); Document doc; String url = null; try {
	 * doc = sb.build(new StringReader(representation)); Element root =
	 * doc.getRootElement(); Element gatewayElement =
	 * root.getChild("contactInfo"); url = gatewayElement.getValue(); } catch
	 * (JDOMException | IOException e) { e.printStackTrace(); } return new
	 * ContactInfo(url, 0, 0);
	 * 
	 * }
	 */
	public static ClientSubscriber parseXmlToClientSubscriber(
			String representation) {
		SAXBuilder sb = new SAXBuilder();
		Document doc;
		String url = null;
		String port = null;
		try {
			doc = sb.build(new StringReader(representation));
			Element root = doc.getRootElement();
			Element urlElement = root.getChild("url");
			Element portElment = root.getChild("port");
			url = urlElement.getValue();
			port = portElment.getValue();
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(url+":"+port);
		return new ClientSubscriber(url,port);
	}

	public static void main(String args[]) throws Exception {

		ProtocolResolver protoTest = new ProtocolResolver();
		protoTest.addProtocol("http", ProtoTest.class);
		System.out.println(parseProtocolsToJSONString(protoTest));


	}
	
	protected class ProtoTest extends AbstractProtocol{
		private String aStr;
		private Object anObj;
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "test";
		}

		@Override
		public void sendMessage(String message) {
			System.out.println(message);
		}
		
	}
}

/*
 * <obj> <str name="id" val="DEVICE_0"/> <str name="protocol" val="http"/> <str
 * name="url"
 * val="http://localhost:8080/om2m/nscl/applications/DEVICE_PROTO/proto"/>
 * </obj>
 * 
 * 
 * <infos> <device> <id>DEVICE_0</id> <url>192.168.43.34:/infos/</url>
 * <protocol>http</protocol> </device>
 * 
 * <gateway> <url>http://localhost:8282</url> </gateway> </infos>
 */
