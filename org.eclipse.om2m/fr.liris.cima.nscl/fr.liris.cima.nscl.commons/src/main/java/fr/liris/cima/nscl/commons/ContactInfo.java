package fr.liris.cima.nscl.commons;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import fr.liris.cima.nscl.commons.encoder.JsonEncoder;

/**
 * This class represent a contact information for a device in NSCL
 * @author madiallo
 *
 */
public class ContactInfo implements JSONAware {

	/**
	 * the Device ID
	 */
	private String deviceId;

	/**
	 * 	port in the gateway that allow a client to communicate with device.
	 */
	private int cloud_port;

	public ContactInfo(String deviceId, int cloud_port) {
		this.deviceId  = deviceId;
		this.cloud_port = cloud_port;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ContactInfo (");
		sb.append("deviceId = " + deviceId + ", ");
		sb.append("cloud_port = " + cloud_port);
		sb.append(" )");
		
		return sb.toString();

	}

	public int getCloud_port() {
		return cloud_port;
	}

	public void setCloud_port(int cloud_port) {
		this.cloud_port = cloud_port;
	}
	
	

	public String toJSONString() {
		JSONObject obj=new JSONObject();
		obj.put("deviceId", this.deviceId);
		obj.put("cloud_port", this.cloud_port);
		return obj.toJSONString();
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


/**
	@Override
	public String toJSONString() {
		 StringBuffer sb = new StringBuffer();
         
         sb.append("{");
        
         sb.append(JSONObject.escape("gateway_address"));
         sb.append(":");
         sb.append("\"" + JSONObject.escape(gatewayAddress) + "\"");
         
         sb.append(",");

         
         sb.append(JSONObject.escape("deviceId"));
         sb.append(":");
         sb.append("\"" + JSONObject.escape(deviceId) + "\"");
         
         sb.append(",");
         
         sb.append(JSONObject.escape("cloud_port"));
         sb.append(":");
         sb.append(cloud_port);
         
         sb.append("}");
         
         return sb.toString();
	}
	**/
}
