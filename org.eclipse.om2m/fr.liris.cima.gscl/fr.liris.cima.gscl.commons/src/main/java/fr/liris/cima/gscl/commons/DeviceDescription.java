package fr.liris.cima.gscl.commons;

import java.util.Date;

import fr.liris.cima.gscl.commons.util.Utils;

public class DeviceDescription {

	private String id;
	private String name;
	private String uri;
	private String modeConnection;
	private String dateConnection;


	public DeviceDescription() {
		this.id = new UID().getUid();
		this.dateConnection = Utils.dateToStr(new Date());
	} 
	
	public DeviceDescription(String name, String uri, String modeConnection) {
		this.id = new UID().getUid();
		this.name = name;
		this.uri = uri;
		this.modeConnection = modeConnection;
		this.dateConnection = Utils.dateToStr(new Date());
		

	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getModeConnection() {
		return modeConnection;
	}

	public void setModeConnection(String modeConnection) {
		this.modeConnection = modeConnection;
	}

	public String getDateConnection() {
		return dateConnection;
	}

	public void setDateConnection(String dateConnection) {
		this.dateConnection = dateConnection;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("DeviceDescription[" 	+ id + ", ");
		sb.append(name + ", ");
		sb.append(uri + ", ");
		sb.append(modeConnection);
		sb.append(", ");
		sb.append(dateConnection);
		sb.append("]");
		
		return sb.toString();
	}
	
	
}
