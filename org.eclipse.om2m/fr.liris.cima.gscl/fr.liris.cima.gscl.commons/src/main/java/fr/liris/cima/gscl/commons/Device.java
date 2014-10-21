package fr.liris.cima.gscl.commons;

import obix.Obj;
import obix.Str;
import obix.io.ObixEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.liris.cima.gscl.commons.port.PortGenerator;

public class Device {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(Device.class);
	/** Application point of contact for the devices controller {@link DeviceController} */
	public final static String APOCPATH = "devices";
	/** Default Device type */
	public final static String TYPE = "DEVICE";


	static int cpt = 0;

	private String id;
	private String protocol;
	private String uri;
	
	private ContactInfo contactInfo;
	
	private static String serverUri = "http://127.0.0.1:8282";

	public Device() {
		this.id = new UID().getUid();
		contactInfo = new ContactInfo(this.id, PortGenerator.generatePort());
	}

	public Device(String uri, String protocol) {
		this.id = new UID().getUid();
		this.uri = uri;
		this.protocol = protocol;
		contactInfo = new ContactInfo(this.id, PortGenerator.generatePort());
	}

	public String toObixFormat() {
		// oBIX
		Obj objDevice = new Obj("device");
		Obj obj = new Obj();
		objDevice.add(new Str("id",id));
		objDevice.add(new Str("protocol", protocol));
		objDevice.add(new Str("url",uri));
		
		obj.add(objDevice);
		obj.add(contactInfo.toBix());

		return ObixEncoder.toString(obj);
	}
	
	public String toXmlFormat() {
		return  "<device>"+
				"<id>"+id+"</id>"+
				"<protocol>"+protocol+"</protocol>"+
				"<uri>"+uri+"</uri> "+
				"<server>"+serverUri+"</server>"+
				"</device>";
	}
	public final String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Device[" + id + ", ");
		sb.append(uri + ", ");
		sb.append(protocol);
		sb.append(", ");
		sb.append(contactInfo);
		sb.append("]\n");
		return sb.toString();
	}

	
	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	
	public static void main(String args[]) throws InterruptedException {
		System.out.println("Hello");
		//Thread.sleep(10000);
		System.out.println(new Device("URI", "http").toObixFormat());
	}
}