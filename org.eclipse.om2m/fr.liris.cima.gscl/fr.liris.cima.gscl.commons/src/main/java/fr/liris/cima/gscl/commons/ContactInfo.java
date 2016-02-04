package fr.liris.cima.gscl.commons;

import obix.Int;
import obix.Obj;
import obix.Str;
import obix.io.ObixEncoder;
/**
 * This class represent a contact information for a device in GSCL.
 * @author madiallo
 *
 */
public class ContactInfo {
	
	private String deviceId;

	/**
	 * 	port in the gateway that allow a client to send communicate with device.

	 */
	private int cloud_port;
	
	public ContactInfo() {
		deviceId="DEVICE_DEFAULT";
		cloud_port = 0;
	}

	public ContactInfo(String deviceId, int cloud_port) {
		this.deviceId = deviceId;
		this.cloud_port = cloud_port;
	}

	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public int getCloud_port() {
		return cloud_port;
	}

	public void setCloud_port(int cloud_port) {
		this.cloud_port = cloud_port;
	}
	
//	public String toBixFormat() {
//		Obj obj = new Obj();
//		obj.add(new Str("deviceId",deviceId));
//		obj.add(new Int("cloud_port", cloud_port));
//
//		return ObixEncoder.toString(obj);
//	}
//	
//	public Obj toBix() {
//		Obj obj = new Obj("contactInfo");
//		obj.add(new Str("deviceId",deviceId));
//		obj.add(new Int("cloud_port", cloud_port));
//
//		return obj;
//	}
	
	public String toString() {
		return "ContactInfo("+deviceId + ", " + cloud_port + ")";
	}
	
	public static void main(String args[]) {
		//System.out.println(new ContactInfo("DEVICE_0", 6001).toBixFormat());
	}
}