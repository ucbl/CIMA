package fr.liris.cima.gscl.commons.parser;

import java.io.StringReader;
import java.util.List;


import obix.Obj;
import obix.Str;
import obix.io.ObixEncoder;
import obix.xml.XElem;
import obix.xml.XParser;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import fr.liris.cima.gscl.commons.*;

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
			String protocol="";

			List<Element> childrenElement = root.getChildren();
			for(Element element : childrenElement) {
				if(element.getName().equals("uri")) {
					uri = element.getText();
				}
				if(element.getName().equals("protocol")) {
					protocol = element.getText();
				}
			}
			device.setUri(uri);
			device.setProtocol(protocol);
		}
		catch (Exception e) {
			e.printStackTrace();
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
	
	public static Device parseObixToDevice(String obixFormat) throws Exception {

		try {
			XParser parser = XParser.make(obixFormat);
			String id = null, url = null, protocol = null;

			XElem root = parser.parse();
			XElem deviceElem = root.elem(0);

			/**
			 * Parse device part
			 */
			for(XElem xElem : deviceElem.elems()){
				if(xElem.attrValue(0).equals("protocol")) {
					protocol = xElem.attrValue(1);
				}
				if(xElem.attrValue(0).equals("url")) {
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
				"<protocol>http</protocol>"+
				"<uri>192.168.43.34</uri> "+
				"<server>http://127.0.0.1:8282</server>"+
				"</device>";
	/**	System.out.println(parseSimpleXmlToObix(representation));
		System.out.println(parseSimpleXmlToObix(representation, "id", "EV3"));
		System.out.println(parseXmlDevice(representation));*/
		//System.out.println(parseXmlDeviceGateway(representation));

	}
}
