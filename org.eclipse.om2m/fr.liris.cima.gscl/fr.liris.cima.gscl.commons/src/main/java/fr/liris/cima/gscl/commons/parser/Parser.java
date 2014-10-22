package fr.liris.cima.gscl.commons.parser;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import obix.Obj;
import obix.Str;
import obix.io.ObixEncoder;
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
	
	public static Device ParseJsonToDevice(String jsonFormat) {
		Device device = new Device("ev3", "http://192.168.0.2", "http");
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
				"<uri>192.168.43.34</uri> "+
				"<server>http://127.0.0.1:8282</server>"+
				"</device>";
		
		Device device = new Device("ev3", "localhost", "http");
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
		System.out.println(device.toObixFormat());
		System.out.println(formatter.format(new Date()));
		

	}
}
