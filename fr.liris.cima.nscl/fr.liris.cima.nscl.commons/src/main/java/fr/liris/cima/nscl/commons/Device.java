package fr.liris.cima.nscl.commons;

import obix.Obj;
import obix.Str;
import obix.io.ObixEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class represent a connected device
 * @author madiallo
 *
 */
public class Device{ 

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(Device.class);
	
	private String id;
	private String protocol;
	private String url;
	// a contact info for device
	private ContactInfo contactInfo;

	public Device(String id, String url, String protocol, ContactInfo contactInfo) {
		this.id = id;
		this.url = url;
		this.protocol = protocol;
		this.contactInfo = contactInfo;
	}

	public String toObixFormat(String sclId) {
		// oBIX
		Obj obj = new Obj();
		obj.add(new Str("id",id));
		obj.add(new Str("protocol", protocol));
		obj.add(new Str("url",url));

		return ObixEncoder.toString(obj);
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
		sb.append("Device( "+id + ", ");
		sb.append(protocol + ", ");
		sb.append(url + ",  "+contactInfo);
		sb.append(")\n");
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
}