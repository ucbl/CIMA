package fr.liris.cima.nscl.commons.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import obix.xml.XElem;
import obix.xml.XParser;
import java.io.FileReader;
import java.util.Iterator;
import obix.Obj;
import obix.Str;
import obix.io.ObixEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import fr.liris.cima.nscl.commons.Device;
import fr.liris.cima.nscl.commons.ContactInfo;
import fr.liris.cima.nscl.commons.subscriber.ClientSubscriber;
import java.util.Iterator;

public class Parser {
    public static Obj parseJSON_To_Obix_Device(JSONObject jsonObject) {
         // oBIX
			Obj obj_Device = new Obj("Device");
         
         obj_Device.add(new Str("id", (String) jsonObject.get("id")));
         obj_Device.add(new Str("name", (String) jsonObject.get("name")));
         obj_Device.add(new Str("uri", (String) jsonObject.get("uri")));
         obj_Device.add(new Str("dateConnection", (String) jsonObject.get("dateConnection")));
         obj_Device.add(new Str("modeConnection", (String) jsonObject.get("modeConnection")));
         obix.List list = new obix.List("Capabilities");
        
		try {
			JSONArray capabilities= (JSONArray) jsonObject.get("capabilities");
			Iterator capacity = capabilities.iterator();
                           int i=0;
			
			while (capacity.hasNext()) {
                                JSONObject capacity_tmp = (JSONObject) capacity.next();
                                list.add(parseJSON_To_Obix_Capacity(capacity_tmp));
                                        
			
		} }
		catch (Exception e) {
			e.printStackTrace();
		}
                  obj_Device.add(list);
		return obj_Device;
	}
    
     public static Obj parseJSON_To_Obix_Capacity(JSONObject capacity) {
                     Obj obj_capacity = new Obj();
        
		try {
                                obj_capacity.add(new Str("id",(String)capacity.get("id")));
                                obj_capacity.add(parseJSON_To_Obix_Protocol((JSONObject) capacity.get("protocol")));
			
		} 
     
		catch (Exception e) {
			e.printStackTrace();
		}
		return obj_capacity;
	}
      public static Obj parseJSON_To_Obix_Protocol(JSONObject protocol_info) {
        
                Obj obj_Protocol = new Obj();
        
		try {           
                                obj_Protocol.setName("protocol");
                                obj_Protocol.add(new Str("protocoleName",(String)protocol_info.get("protocoleName")));
                                JSONArray parameters=(JSONArray)protocol_info.get("parameters");
                                
                                    Iterator parameter = parameters.iterator();
                                        while (parameter.hasNext()) {
                                            JSONObject parameter_tmp = (JSONObject) parameter.next();
                                            String nam = (String)parameter_tmp.get("name");
                                            String value = (String)parameter_tmp.get("value");
                                            obj_Protocol.add(new Str(nam,value));
                                           }
                                        
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return obj_Protocol;
	}
           
	public static Device parseObixToDevice(String obixFormat)  {

		Device device = null;
		try {
			XParser parser = XParser.make(obixFormat.trim());
			String id = null, url = null, protocol = null;

			XElem root = parser.parse();
			XElem deviceElem = root.elem(0);
			XElem contactInfoElem = root.elem(1);


			/**
			 * Parse device part
			 */
			for(XElem xElem : deviceElem.elems()){
				if(xElem.attrValue(0).equals("id")) {
					id = xElem.attrValue(1);
				}
				if(xElem.attrValue(0).equals("protocol")) {
					protocol = xElem.attrValue(1);
				}
				if(xElem.attrValue(0).equals("url")) {
					url = xElem.attrValue(1);
				}

			}
			String  deviceId = null;
			int cloud_port = 0;

			/**
			 * Parse contactInfo part
			 */
			for(XElem xElem : contactInfoElem.elems()){
				if(xElem.attrValue(0).equals("deviceId")) {
					deviceId = xElem.attrValue(1);
				}	
				if(xElem.attrValue(0).equals("cloud_port")) {
					cloud_port = Integer.parseInt(xElem.attrValue(1));
				}	
			}
			if(id != null && protocol != null && url != null) {
				device = new Device(id, url, protocol, new ContactInfo(deviceId, cloud_port));
				System.out.println(device);
			}
			return device;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return device;

	}

	public static ContactInfo parseObixToContactInfo(String obixFormat) {

		try {
			XParser parser = XParser.make(obixFormat);
			String  deviceId = null;
			int cloud_port = 0;
			XElem root = parser.parse();
			XElem gatewayElem = root.elem(1);

			/**
			 * Parse contactInfo part
			 */
			for(XElem xElem : gatewayElem.elems()){
				if(xElem.attrValue(0).equals("deviceId")) {
					deviceId = xElem.attrValue(1);
				}	
				if(xElem.attrValue(0).equals("cloud_port")) {
					cloud_port = Integer.parseInt(xElem.attrValue(1));
				}	
			}

			ContactInfo contactInfo = new ContactInfo(deviceId, cloud_port);
			System.out.println(contactInfo);

			return contactInfo;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	public static Device parseXmlToDevice(String representation) {

		try {
			SAXBuilder sb  = new SAXBuilder();
			Document doc = sb.build(new StringReader(representation));
			Element root =  doc.getRootElement();
			String url ="";
			String protocol="";
			String id = "";

			Element deviceElement = root.getChild("device");

			List<Element> childrenElement = deviceElement.getChildren();
			for(Element element : childrenElement) {
				if(element.getName().equals("id")) {
					id = element.getText();
				}
				if(element.getName().equals("url")) {
					url = element.getText();
				}
				if(element.getName().equals("protocol")) {
					protocol = element.getText();
				}
			}


			return new Device(id, url, protocol);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}**/
	/**
	public static ContactInfo parseXmlToGatewayInfo(String representation) {
		SAXBuilder sb  = new SAXBuilder();
		Document doc;
		String url = null;
		try {
			doc = sb.build(new StringReader(representation));
			Element root =  doc.getRootElement();
			Element gatewayElement = root.getChild("contactInfo");
			url =  gatewayElement.getValue();
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return new ContactInfo(url, 0, 0);

	}
	 */
	public static ClientSubscriber parseXmlToClientSubscriber(String representation) {
		SAXBuilder sb  = new SAXBuilder();
		Document doc;
		String url = null;
		try {
			doc = sb.build(new StringReader(representation));
			Element root =  doc.getRootElement();
			Element urlElement = root.getChild("url");
			url =  urlElement.getValue();
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(url);
		return new ClientSubscriber(url);
	}


	public static void main(String args[]) throws Exception {
		String strClient = "<subscriber> <url>localhost</url></subscriber>";
		String representation = "<infos>"+
				"<device>"+ 
				"<id>DEVICE_0</id>"+
				"<url>192.168.43.34:/infos/</url>"+ 
				"<protocol>http</protocol>"+
				"</device>"+
				"<gateway>"+
				"<url>http://localhost:8282</url>"+
				"</gateway>"+
				"</infos>";

		String obixFormat = "<obj>"+
				"<obj name=\"device\">" +
				"<str name=\"id\" val=\"DEVICE_0\"/>"+
				"<str name=\"protocol\" val=\"http\"/>"+
				"<str name=\"url\" val=\"192.168.43.34:/device/capabilities/\"/>"+
				"</obj>"+
				"<obj name=\"contactInfo\">" +
				"<str name=\"deviceId\" val=\"DEVICE_0\"/>"+
				"<int name=\"cloud_port\" val=\"6000\"/>"+
				"</obj>"+
				"</obj>";
		

		parseObixToContactInfo(obixFormat);
		parseObixToDevice(obixFormat.trim());
		System.out.println(parseXmlToClientSubscriber(strClient));
		//	System.out.println(parseXmlToDevice(representation));
		//System.out.println(parseXmlToGatewayInfo(representation));
		//parseObix();
	}
}

/**
 * <obj>
    <str name="id" val="DEVICE_0"/>
    <str name="protocol" val="http"/>
    <str name="url" val="http://localhost:8080/om2m/nscl/applications/DEVICE_PROTO/proto"/>
</obj>


<infos>
	<device> 
		<id>DEVICE_0</id>
		<url>192.168.43.34:/infos/</url> 
		<protocol>http</protocol>
	</device>

	<gateway>
		<url>http://localhost:8282</url>
	</gateway>
</infos>
 */
