package fr.liris.cima.gscl.commons.constants;

public class Constants {

	public static final String COMMAND_ARP_FOR_IP = "sudo arp-scan --interface=wlan0 192.168.0.1/24";
	public static final  String IP_PREFIX = "192.168";
	
	public final static String METHOD_CREATE = "CREATE";
	public final static String METHOD_DELETE = "DELETE";

	/** Generic execute method name */
	public final static String METHOD_EXECUTE = "EXECUTE";
	public final static String METHOD_RETRIEVE = "RETRIEVE";
	public static String REQENTITY = System.getProperty("org.eclipse.om2m.adminRequestingEntity","");
	public final static String SCLID = System.getProperty("org.eclipse.om2m.sclBaseId","");
	
	public final static String PATH_CAPABILITIES = "capabilities";
	public final static String PATH_INVOKE = "invoke";
	//public final static String PATH_CAPABILITIES = "capabilities";	
}