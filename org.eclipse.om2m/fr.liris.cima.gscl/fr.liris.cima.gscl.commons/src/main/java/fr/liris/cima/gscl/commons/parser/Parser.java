package fr.liris.cima.gscl.commons.parser;

import java.io.StringReader;
import java.text.SimpleDateFormat;
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

import fr.liris.cima.gscl.commons.*;
import fr.liris.cima.gscl.commons.util.Utils;

/**
 * This class represent a parser for parsing exchanging data
 * @author madiallo
 *
 */
public class Parser {

	public static String parseOwlToObix(String owlStr) {
		return "";
	}

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

	public static Device parseXmlDevice(String representation) {
		Device device =  new Device();

		try {
			SAXBuilder sb  = new SAXBuilder();
			Document doc = sb.build(new StringReader(representation));
			Element root =  doc.getRootElement();
			String uri ="";
			String modeConnection="";
			String name = "";

			Date dateConnection = null;


			List<Element> childrenElement = root.getChildren();
			for(Element element : childrenElement) {
				if(element.getName().equals("uri")) {
					uri = element.getText();
				}
				if(element.getName().equals("modeConnection")) {
					modeConnection = element.getText();
				}
				if(element.getName().equals("name")) {
					name = element.getText();
				}

				if(element.getName().equals("dateConnection")) {
					dateConnection = Utils.StrToDate(element.getText());
				}
			}
			device.setUri(uri);
			device.setModeConnection(modeConnection);
			device.setName(name);
			device.setDateConnection(dateConnection);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return device;
	}

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

				device = new Device(name, uri, modeConnection, dateConnection, new ContactInfo());
				if(id != null)
					device.setId(id);

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

	public static Capability parseObixToCapability(String obixFormat) {

		Device device = null;
		String id = "",  name = "", uri = "",  modeConnection=""; 
		
		Protocol protocol = new Protocol();
		Date dateConnection = null;

		Obj capabilityObj = ObixDecoder.fromString(obixFormat);
		
		System.out.println("Eleme = "+capabilityObj.get("protocol"));
		Obj protocolObj = capabilityObj.get("protocol");
		protocol.setName(protocolObj.get("protocoleName").getStr());
		protocol.addParameter("method", protocolObj.get("method").getStr());
		protocol.addParameter("port", protocolObj.get("port").getStr());
		protocol.addParameter("uri", protocolObj.get("uri").getStr());
		
		name = capabilityObj.get("id").getStr();
		
		return new Capability(name, protocol);


	}

	public static Device ParseJsonToDevice(String jsonFormat) {
		Device device = new Device("ev3", "http://192.168.0.2", "http", new ContactInfo());
		device.setId("ev3");

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

	public static Device parseObixToDevice1(String obixFormat) throws Exception {

		try {
			XParser parser = XParser.make(obixFormat);
			String id = null, url = null, protocol = null;

			XElem root = parser.parse();
			XElem deviceElem = root.elem(0);

			/**
			 * Parse device part
			 */
			for(XElem xElem : deviceElem.elems()){
				if(xElem.attrValue(0).equals("modeConnection")) {
					protocol = xElem.attrValue(1);
				}
				if(xElem.attrValue(0).equals("uri")) {
					url = xElem.attrValue(1);
				}

			}
			Device device = new Device(url, protocol);
			System.out.println(device);
			return device;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String args[]) throws Exception {
		String representation = "<device>"+
				"<name>ev3</name>"+
				"<modeConnection>http</modeConnection>"+
				"<dateConnection>mercredi, oct. 22, 2014 13:52:20 PM</dateConnection>"+
				"<uri>192.168.43.34</uri> "+
				"<server>http://127.0.0.1:8282</server>"+
				"</device>";
		//
		//		Device device = new Device("ev3", "localhost", "http", new ContactInfo());
		//		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
		//		System.out.println(Parser.parseXmlDevice(representation));
		//System.out.println(formatter.format(new Date()));


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
				"<obj name=\"protocol\">"+
				"<str name=\"protocoleName\" val=\"http\"/>"+
				"<str name=\"method\" val=\"post\"/>"+
				"<str name=\"port\" val=\"8080\"/>"+
				"<str name=\"uri\" val=\"uri\"/>"+
				"</obj>"+
				"</obj>"+
				"<obj>"+
				"<str name=\"id\" val=\"phone\"/>"+
				"<obj name=\"protocol\">"+
				"<str name=\"protocoleName\" val=\"http\"/>"+
				"<str name=\"method\" val=\"post\"/>"+
				"<str name=\"port\" val=\"8080\"/>"+
				"<str name=\"uri\" val=\"uri\"/>"+
				"</obj>"+
				"</obj>"+
				"</list>"+                
				"</obj>"+
				"</obj>";


		String capabilityFormat = "<obj>"+
				"<str name=\"id\" val=\"ev3Back\"/>"+
				"<obj name=\"protocol\">"+
				"<str name=\"protocoleName\" val=\"http\"/>"+
				"<str name=\"method\" val=\"post\"/>"+
				"<str name=\"port\" val=\"8080\"/>"+
				"<str name=\"uri\" val=\"uri\"/>"+
				"</obj>"+
				"</obj>";
		
		System.out.println(parseObixToCapability(capabilityFormat).toObixFormat());


		//	System.out.println(obixFormat);

		Device device2 = parseObixToDevice(obixFormat);
		//	System.out.println(device2);
		//System.out.println(device2.toIntrinsequeObixFormat());
		//		System.out.println( device2.toIntrinsequeObixFormat().equals(obixFormat));


		obixFormat = device2.toIntrinsequeObixFormat();

		Device	device3 = parseObixToDevice(obixFormat);



		//System.out.println(device2.getCapabilities());


	}
}