package fr.liris.cima.gscl.commons;

import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import fr.liris.cima.gscl.commons.util.Utils;

public class DeviceDescription {
	private static String TIMEFORMAT = "YY-MM-DD hh:mm:ssZ";
	private static TimeZone TIMEZONE = TimeZone.getTimeZone("Europe/Paris");
	private static String PLATFORM_URI = "localhost:8080/device/";

	private String id;
	private String name;
	private String ip;
	private String protocol;
	private String dateConnection;
	private String uri;
	private String description;

	public DeviceDescription() {
		this.id = new UID().getUid();
		//this.dateConnection = Utils.dateToStr(new Date());
		this.dateConnection = getCurrentTime(TIMEFORMAT, TIMEZONE);
		this.uri = PLATFORM_URI + id;
	} 
	
	public DeviceDescription(String name, String ip, String protocol) {
		this.id = new UID().getUid();
		this.name = name;
		this.ip = ip;
		this.protocol = protocol;
		//this.dateConnection = Utils.dateToStr(new Date());
		this.dateConnection = getCurrentTime(TIMEFORMAT, TIMEZONE);
		this.uri = PLATFORM_URI + id;
	}
	
	public String getId() {
		return (id != null) ? id : "";
	}
	
	public void setId(String id) {
		this.id = id;

		// update of device's uri
		this.uri = PLATFORM_URI + id;
	}
	
	public String getName() {
		return (name != null) ? name : "";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIp() {
		return (ip != null) ? ip : "";
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getProtocol() {
		return (protocol != null) ? protocol : "";
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getDateConnection() {
		return (dateConnection != null) ? dateConnection : "";
	}

	public void setDateConnection(String dateConnection) {
		this.dateConnection = dateConnection;
	}

	public String getUri() { return (uri != null) ? uri : ""; }

	public void setUri(String uri) { this.uri = uri; }

	public String getDescription() { return (description != null) ? description : ""; }

	public void setDescription(String description) { this.description = description; }

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("DeviceDescription[" 	+ id + ", ");
		sb.append(name + ", ");
		sb.append(ip + ", ");
		sb.append(protocol + ", ");
		sb.append(dateConnection + ", ");
		sb.append(uri + ", ");
		sb.append(description);
		sb.append("]");
		
		return sb.toString();
	}

	public String getCurrentTime(String timeFormat, TimeZone timeZone)
	{
      /* Specifying the format */
		DateFormat dateFormat = new SimpleDateFormat(timeFormat);
      /* Setting the Timezone */
		Calendar cal = Calendar.getInstance(timeZone);
		dateFormat.setTimeZone(cal.getTimeZone());
      /* Picking the time value in the required Format */
		String currentTime = dateFormat.format(cal.getTime());
		return currentTime;
	}
}
