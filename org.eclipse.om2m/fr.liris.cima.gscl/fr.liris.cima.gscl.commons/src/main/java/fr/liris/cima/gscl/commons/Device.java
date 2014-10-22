package fr.liris.cima.gscl.commons;

import java.text.SimpleDateFormat;
import java.util.Date;

import obix.Obj;
import obix.Str;
import obix.io.ObixEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.liris.cima.gscl.commons.port.PortGenerator;
import fr.liris.cima.gscl.commons.util.Utils;

public class Device {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(Device.class);
	/** Application point of contact for the devices controller {@link DeviceController} */
	public final static String APOCPATH = "devices";
	/** Default Device type */
	public final static String TYPE = "DEVICE";



	private String id;
	private String name;
	private Date dateConnection;
	private String modeConnection;
	
	//private String protocol;
	private String uri;
	
	private ContactInfo contactInfo;
	
	private static String serverUri = "http://127.0.0.1:8282";

	public Device() {
		this.id = new UID().getUid();
		contactInfo = new ContactInfo(this.id, PortGenerator.generatePort());
	}

	public Device(String uri, String modeConnection) {
		this.id = new UID().getUid();
		this.uri = uri;
		this.modeConnection = modeConnection;
		contactInfo = new ContactInfo(this.id, PortGenerator.generatePort());
	}
	
	public Device(String name, String uri, String modeConnection) {
		this.id = new UID().getUid();
		this.name = name;
		this.uri = uri;
		this.modeConnection = modeConnection;
		this.dateConnection = Utils.StrToDate("mercredi, oct. 22, 2014 13:52:20 PM");
		
		contactInfo = new ContactInfo(this.id, PortGenerator.generatePort());

	}

	public String toObixFormat() {
		
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");

		Obj objDevice = new Obj("device");
		Obj obj = new Obj();
		
		objDevice.add(new Str("id",id));
		objDevice.add(new Str("name", name));
		objDevice.add(new Str("modeConnection", modeConnection));
		objDevice.add(new Str("url",uri));
		objDevice.add(new Str("dateConnection", formatter.format(dateConnection)));
		
		obj.add(objDevice);
		obj.add(contactInfo.toBix());

		return ObixEncoder.toString(obj);
	}
	
	public String toXmlFormat() {
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");

		return  "<device>"+
				"<id>"+id+"</id>"+
				"<name>"+name+"</name>"+
				"<modeConnection>"+modeConnection+"</modeConnection>"+
				"<dateConnection>"+dateConnection+"</dateConnection>"+
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
		sb.append(modeConnection);
		sb.append(", ");
		sb.append(dateConnection);
		sb.append(", ");
		sb.append(contactInfo);
		sb.append("]\n");
		return sb.toString();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateConnection() {
		return dateConnection;
	}

	public void setDateConnection(Date dateConnection) {
		this.dateConnection = dateConnection;
	}

	public String getModeConnection() {
		return modeConnection;
	}

	public void setModeConnection(String modeConnection) {
		this.modeConnection = modeConnection;
	}
	
	public boolean equals(Object other) {
		Device device = (Device)other;
		
		return this.id.equals(device.id);
	}
}