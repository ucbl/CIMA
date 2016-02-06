package fr.liris.cima.gscl.commons.parser;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import obix.Obj;
import obix.Str;
import obix.io.ObixDecoder;
import obix.io.ObixEncoder;
import obix.tools.Obixc;
import obix.xml.XElem;
import obix.xml.XParser;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import fr.liris.cima.comm.protocol.ProtocolResolver;
import fr.liris.cima.gscl.commons.*;
import fr.liris.cima.gscl.commons.util.Utils;

/**
 * This class represent a parser for parsing exchanging data
 * @author madiallo
 *
 */
public class Parser {

	public static ProtocolResolver protocolResolver;

	public static String parseOwlToObix(String owlStr) {
		return "";
	}

	//NOT UPDATED after changement of capabilities (which now containts params and result), because it's not used anywhere
	public static String parseSimpleXmlToObix(String representation, String newElement, String value) {
		try {

			SAXBuilder sb  = new SAXBuilder();
			Document doc = sb.build(new StringReader(representation));
			Element root =  doc.getRootElement();
			Element idElement = new Element(newElement);
			idElement.setText(value);
			root.getChildren().add(0, idElement);

			List<Element> elements = root.getChildren();

			// oBIX
			Obj obj = new Obj();	        
			for(Element element : elements) {
				obj.add(new Str(element.getName(), element.getValue()));
			}	
			return ObixEncoder.toString(obj);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	//NOT UPDATED after changement of capabilities (which now containts params and result), because it's not used anywhere
	public static String parseSimpleXmlToObix(String representation) {
		try {

			SAXBuilder sb  = new SAXBuilder();
			Document doc = sb.build(new StringReader(representation));
			Element root =  doc.getRootElement();

			List<Element> elements = root.getChildren();

			// oBIX
			Obj obj = new Obj();	        
			for(Element element : elements) {
				obj.add(new Str(element.getName(), element.getValue()));
			}	
			return ObixEncoder.toString(obj);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static DeviceDescription parseXmlToDeviceDescription(String representation) {
		DeviceDescription deviceDescription = new DeviceDescription();

		try {
			SAXBuilder sb  = new SAXBuilder();
			Document doc = sb.build(new StringReader(representation));
			Element root =  doc.getRootElement();
			String uri ="";
			String modeConnection="";
			String name = "";


			List<Element> childrenElement = root.getChildren();
			for(Element element : childrenElement) {
				if(element.getName().equals("uri") || element.getName().equals("ip")) {
					uri = element.getText().trim();
				}
				if(element.getName().equals("modeConnection")) {
					modeConnection = element.getText().trim();
				}
				if(element.getName().equals("name")) {
					name = element.getText().trim();
				}
			}
			deviceDescription.setUri(uri);
			deviceDescription.setModeConnection(modeConnection);
			deviceDescription.setName(name);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return deviceDescription;
	}

	public static Protocol elementToProtocol(Element protocolElement) throws JDOMException, IOException {

//		Class http = protocolResolver.getProtocol("http");;
//		Protocol protocol = null;
//		try {
//			protocol = (Protocol) http.newInstance();
//		} catch (InstantiationException | IllegalAccessException e) {
//			e.printStackTrace();
//			return null;
//		}

		Protocol protocol = new Protocol();

		List<Element> childrenElement = protocolElement.getChildren();
		for(Element element : childrenElement) {
			if(element.getName().equals("protocolName")) {
				protocol.setName(element.getText());
			} else {
				protocol.addParameter(element.getName(), element.getText());
			}
		}
		return protocol;
	}

	public static Capability elementToCapability(Element capabilityElement) throws JDOMException, IOException {
		Capability capability = new Capability();

		Element idElement = capabilityElement.getChild("id");
		if(idElement != null) {
			capability.setName( idElement.getText());
		}
		Element elementProtocol = capabilityElement.getChild("protocol");
		Element elementKeywords = capabilityElement.getChild("keywords");
		if(elementKeywords != null) {
			for(Element keywordElement : elementKeywords.getChildren()) {
				capability.addKeyword(keywordElement.getText());
			}
		}
		if( elementProtocol!= null) {
			capability.setProtocol(elementToProtocol(elementProtocol));
		}

		Element elementParameters = capabilityElement.getChild("params");
		if (elementParameters != null) {
			for(Element paramElement : elementParameters.getChildren()) {
				capability.addParameter(elementToParameter(paramElement));
			}
		}

		Element elementResult = capabilityElement.getChild("result");
		if (elementResult != null) {
			capability.setResult(elementToResult(elementResult));
		} else {
			capability.setResult(null);
		}

		return capability;
	}

	public static Parameter elementToParameter(Element paramElement) throws JDOMException, IOException {

		Parameter param = new Parameter();

		param.setIdP(paramElement.getChildText("idp"));
		param.setDesc(paramElement.getChildText("desc"));
		param.setType(paramElement.getChildText("type"));

		return param;
	}

	public static Result elementToResult(Element elementResult) throws JDOMException, IOException {

		Result res = null;

		if ("true".equals(elementResult.getAttributeValue("hasresult"))) {
			res = new Result();

			res.setType(elementResult.getChildText("type"));
			res.setDesc(elementResult.getChildText("desc"));
		}

		return res;
	}


	public static Device parseXmlToDevice(String representation) {

		System.out.println("+++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ ");
		System.out.println("+++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ ");
		System.out.println("Cas XXX" + representation.toString());
		System.out.println("+++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ ");
		System.out.println("+++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ ");
		DeviceDescription deviceDescription = new DeviceDescription();
		Device device = null;
		List<Capability> capabilities = new ArrayList<>();

		try {
			SAXBuilder sb  = new SAXBuilder();
			Document doc = sb.build(new StringReader(representation));
			Element root =  doc.getRootElement();
			String uri ="";
			String modeConnection="";
			String name = "";

			String capabilityId = "";
			Protocol protocol;
			List<Element> childrenElement = root.getChildren();
			for(Element element : childrenElement) {
				if(element.getName().equals("uri")) {
					uri = element.getText().trim();
				}
				if(element.getName().equals("modeConnection")) {
					modeConnection = element.getText().trim();
				}
				if(element.getName().equals("name")) {
					name = element.getText().trim();
				}
				if(element.getName().equals("capabilities")) {
					List<Element> childrenCapabilityElement = element.getChildren("capability");

					for(Element capabilityElement : childrenCapabilityElement) {
						Capability capability = elementToCapability(capabilityElement);
						capabilities.add(capability);
					}
				}

			}
			deviceDescription.setUri(uri);
			deviceDescription.setModeConnection(modeConnection);
			deviceDescription.setName(name);
			device = new Device(deviceDescription);
			device.setCapabilities(capabilities);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return device;
	}

	/*
	public static Device parseObixToDevice(String obixFormat) {

		Device device = null;
		String id = "",  name = "", uri = "",  modeConnection=""; 
		Date dateConnection = null;

		try {
			XParser parser = XParser.make(obixFormat.trim());
			XElem root = parser.parse();
			XElem deviceElem = root.elem(0);
			//XElem capabilitiesElem = deviceElem.get(arg0)

			for(XElem xElem : deviceElem.elems()){
				if(xElem.attrValue(0).equals("id")) {
					id = xElem.attrValue(1);
					//	System.out.println( xElem.attrValue(1));
				}
				if(xElem.attrValue(0).equals("name")) {
					name = xElem.attrValue(1);
					//System.out.println( xElem.attrValue(1));
				}
				if(xElem.attrValue(0).equals("uri")) {
					uri = xElem.attrValue(1);
					//	System.out.println( xElem.attrValue(1));
				}
				if(xElem.attrValue(0).equals("dateConnection")) {
					dateConnection = Utils.StrToDate(xElem.attrValue(1));
					System.out.println( "date = "+xElem.attrValue(1));
				}
				if(xElem.attrValue(0).equals("modeConnection")) {
					modeConnection = xElem.attrValue(1);
					//	System.out.println( xElem.attrValue(1));
				}

				DeviceDescription deviceDescription = new DeviceDescription(name, uri, modeConnection);
				device = new Device(deviceDescription);
				if(deviceDescription.getId() != null) {
					deviceDescription.setId(id);
				}

				if(xElem.get("name").equals("capabilities")) {
					//System.out.println("OK");
					for(XElem capabilityElem : xElem.elems()) {
						String capabilityName = capabilityElem.elem(0).attrValue(1);
						Capability capability = new Capability(capabilityName);
						if(capabilityElem.elem(1).get("name").equals("protocol")) {
							XElem protocolChildElems []= capabilityElem.elem(1).elems();

							String protocolName = protocolChildElems[0].attrValue(1);
							Protocol protocol = new Protocol(protocolName);
							for(int i = 0; i < protocolChildElems.length ; i++) {
								String key =  protocolChildElems[i].attrValue(0);
								String value =  protocolChildElems[i].attrValue(1);
								protocol.addParameter(key, value);
							}
							capability.setProtocol(protocol);
							//System.out.println(capability);
						}
						if(capabilityElem.elem(2).get("name").equals("keywords")){
							XElem keywordsChildElems [] = capabilityElem.elem(2).elems();
							List<String> keywords = new ArrayList<>();
							for(XElem xe : keywordsChildElems){
								keywords.add(xe.attrValue(0));
							}
							capability.setKeywords(keywords);
						}
						// Add capability to device
						device.addCapability(capability);
					}
				}
			}

		}catch(Exception e) {
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
		
//		Class http = protocolResolver.getProtocol("http");;
//		Protocol protocol = null;
//		try {
//			protocol = (Protocol) http.newInstance();
//		} catch (InstantiationException | IllegalAccessException e) {
//			e.printStackTrace();
//			return null;
//		}

		Obj capabilityObj = ObixDecoder.fromString(obixFormat);

		Obj protocolObj = capabilityObj.get("protocol");
		protocol.setName(protocolObj.get("protocolName").getStr());
		protocol.addParameter("method", protocolObj.get("method").getStr());
		protocol.addParameter("port", protocolObj.get("port").getStr());
		protocol.addParameter("uri", protocolObj.get("uri").getStr());
		String bodySTR = (protocolObj.get("body") != null) ? protocolObj.get("body").getStr() : "";
		protocol.addParameter("body", bodySTR);

		int cloudPort = (int) capabilityObj.get("cloudPort").getInt();

		Obj [] objs = capabilityObj.get("keywords").list();
		List<String> keywords = new ArrayList<>();
		for(Obj o : objs){
			keywords.add(o.getStr());
		}

		Obj [] objsParams = capabilityObj.get("params").list();
		List<Parameter> params = new ArrayList<>();
		for(Obj o : objsParams){
			Parameter p = new Parameter(o.get("idp").getStr(), o.get("desc").getStr(), o.get("type").getStr());
			params.add(p);
		}

		Obj objResult = capabilityObj.get("result");
		Result r = null;
		if (objResult != null) {
			r = new Result(objResult.get("type").getStr(), objResult.get("desc").getStr());
		}

		name = capabilityObj.get("id").getStr();
		Capability capa = new Capability(name, protocol, keywords,cloudPort, params, r);
		System.out.println("***************************************************");
		System.out.println(capabilityObj.get("configuration").getStr());
		System.out.println("***************************************************");
		capa.setConfiguration(capabilityObj.get("configuration").getStr());
		return capa;
	}

	public static Device parseObixToDevice(String obixFormat) {
		System.out.println("OBIX FORMAT : " + obixFormat);
		Device device;
		List<Capability> capabilities = new ArrayList<>();
		String id = null, name = null, uri = null, modeConnection = null, dateConnection = null;

		Obj objRoot = ObixDecoder.fromString(obixFormat);
		Obj objDevice = objRoot.get("device");
		if(objDevice == null) objDevice = objRoot.get("Device");
		if(objDevice == null) objDevice = objRoot;
		id = objDevice.get("id").getStr();
		name = objDevice.get("name").getStr();
		uri = objDevice.get("uri").getStr();
		modeConnection = objDevice.get("modeConnection").getStr();
		dateConnection  = objDevice.get("dateConnection").getStr();
		

		DeviceDescription deviceDescription = new DeviceDescription(name, uri, modeConnection);
		if(id != null) {
			deviceDescription.setId(id);
		}
		if(dateConnection != null) {
			deviceDescription.setDateConnection(dateConnection);
		}
		deviceDescription.setName(name);
		deviceDescription.setUri(uri);
		deviceDescription.setModeConnection(modeConnection);

		device = new Device(deviceDescription);

		obix.List obixCapabilities = (obix.List)objDevice.get("capabilities");
		if(obixCapabilities == null) obixCapabilities = (obix.List)objDevice.get("Capabilities");

		if(obixCapabilities != null) {
			System.out.println("OBJ HAVE CAPABILITIES");
			System.out.println(ObixEncoder.toString(obixCapabilities));
			for(Obj objCapability : obixCapabilities.list()) {
				System.out.println(ObixEncoder.toString(objCapability));
				Capability capability = parseObixToCapability(ObixEncoder.toString(objCapability));
				if(capability != null) {
					device.addCapability(capability);
				}
			}
		}

		Obj objContactInfo = objDevice.get("contactInfo");
		if(objContactInfo != null) {
			System.out.println(objContactInfo.get("cloud_port"));
			long cloudPort = objContactInfo.get("cloud_port").getInt();
			ContactInfo contactInfo = new ContactInfo(id, (int)cloudPort);
			device.setContactInfo(contactInfo);
		}

		return device;	
	}


	public static String parseXmlDeviceGateway(String representation) {
		try {
			SAXBuilder sb  = new SAXBuilder();
			Document firstDoc = sb.build(new StringReader(representation));
			Element firstRoot =  firstDoc.getRootElement();
			Element root = new Element("infos");
			firstDoc.setRootElement(root);
			root.addContent(firstRoot);

			List<Element> childrenElement = root.getChildren();
			for(Element element : childrenElement) {
				System.out.println(element.getValue());
			}
			return "";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}


	public static void main(String args[]) throws Exception {
		//		String representation = "<device>"+
		//				"<name>ev3</name>"+
		//				"<modeConnection>http</modeConnection>"+
		//				"<dateConnection>mercredi, oct. 22, 2014 13:52:20 PM</dateConnection>"+
		//				"<uri>192.168.43.34</uri> "+
		//				"<capabilities> "
		//				+ "<capability>"
		//				+ "<id> ev3back</id>"
		//				+ "<protocol>"
		//				+ "<protocolName>" + "http" + "</protocolName>"
		//				+ "<method> "+ "PUT" +"</method>"
		//				+ "<port>8080</port>"
		//				+ "<uri>" + "/capabilities" + "</uri>"
		//				+ "<body>body</body>"
		//				+ "</protocol>"
		//				+ "<keywords>"
		//				+ "<keyword>ev3</keyword>"
		//				+ "</keywords>"
		//				+ "</capability>"
		//				+ "</capabilities>"
		//				+"</device>";
		//
		//		Device device = new Device("ev3", "localhost", "http", new ContactInfo());
		//		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
		//		System.out.println(Parser.parseXmlDevice(representation));
		//System.out.println(formatter.format(new Date()));

		//		System.out.println("device = "+parseXmlToDevice(representation));

		String obixFormat = "<obj>"+
				"<obj name=\"device\">" +
				"<str name=\"id\" val=\"DEVICE_0\"/>"+
				"<str name=\"name\" val=\"http\"/>"+
				"<str name=\"uri\" val=\"192.168.43.34:/device/capabilities/\"/>"+
				"<str name=\"dateConnection\" val=\"mercredi, oct. 22, 2014 13:52:20 PM\"/>"+
				"<str name=\"modeConnection\" val=\"ip\"/>"+
				"<list name=\"capabilities\">"+
				"<obj>"+
				"<str name=\"id\" val=\"ev3Back\"/>"+
				"<str name=\"cloudPort\" val=\"8080\" />"+
				"<obj name=\"protocol\">"+
				"<str name=\"protocolName\" val=\"http\"/>"+ 
				"<str name=\"method\" val=\"post\"/>"+
				"<str name=\"port\" val=\"8080\"/>"+
				"<str name=\"uri\" val=\"uri\"/>"+
				"<str name=\"body\" val=\"body\"/>"+
				"</obj>"+
				"<list name=\"keywords\">"+
				"<str val=\"ev3\" />"+
				"<str val=\"back\" />"+
				"</list>"+
				"</obj>"+
				"</list>"+  
				"<obj name=\"contactInfo\">"+
				" <str name=\"deviceId\" val=\"DEVICE_0\"/>"+
				" <int name=\"cloud_port\" val=\"14948\"/> " +
				"</obj>" +
				"</obj>"+
				"</obj>";

		//System.out.println(obixFormat);
		Device device = parseObixToDevice(obixFormat);
		System.out.println(device);
		//	System.out.println(Encoder.encodeDeviceToObix(device));



		//
		//		String capabilityFormat = "<obj>"+
		//				"<str name=\"id\" val=\"ev3Back\"/>"+
		//                                "<str name=\"cloudPort\" val=\"8080\" />"+
		//				"<obj name=\"protocol\">"+
		//				"<str name=\"protocolName\" val=\"http\"/>"+ 
		//				"<str name=\"method\" val=\"post\"/>"+
		//				"<str name=\"port\" val=\"8080\"/>"+
		//				"<str name=\"uri\" val=\"uri\"/>"+
		//                                "<str name=\"body\" val=\"body\"/>"+
		//				"</obj>"+
		//				"<list name=\"keywords\">"+
		//				"<str val=\"ev3\" />"+
		//				"<str val=\"back\" />"+
		//				"</list>"+
		//				"</obj>";
		//		//	System.out.println(capabilityFormat);
		//				System.out.println(parseObixToCapability(capabilityFormat).toString());
		//
		//	
		//		System.out.println(parseObixToCapability(capabilityFormat).toObixFormat());
		//
		//


		//	System.out.println(obixFormat);

		//	Device device2 = parseObixToDevice(obixFormat);
		//System.out.println(device2);
		//System.out.println(device2.toIntrinsequeObixFormat());
		//		System.out.println( device2.toIntrinsequeObixFormat().equals(obixFormat));


		//		obixFormat = device2.toIntrinsequeObixFormat();

		//		Device	device3 = parseObixToDevice(obixFormat);



		//System.out.println(device2.getCapabilities());

	}
}