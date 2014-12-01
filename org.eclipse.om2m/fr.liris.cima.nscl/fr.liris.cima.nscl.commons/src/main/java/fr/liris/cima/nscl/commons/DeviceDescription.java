package fr.liris.cima.nscl.commons;

/**
 * Defines a minimal required information for a device
 * @author madiallo
 *
 */
public class DeviceDescription {

	/** Device's id	 */
	private String id;
	
	/** Device's name*/
	private String name;
	
	/** Device's uri */
	private String uri;
	
	/** Connection mode required to communicate with device (like ip, usb, etc ...)*/
	private String modeConnection;
	
	/** Device date connection */
	private String dateConnection;

	public DeviceDescription(String id, String name, String uri, String modeConnection, String dateConnection) {
		this.id = id;
		this.name = name;
		this.uri = uri;
		this.modeConnection = modeConnection;
		this.dateConnection = dateConnection;
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
