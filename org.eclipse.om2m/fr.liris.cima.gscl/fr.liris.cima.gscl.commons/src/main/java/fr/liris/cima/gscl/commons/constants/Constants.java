package fr.liris.cima.gscl.commons.constants;

public class Constants {

	public static final String COMMAND_ARP_FOR_IP = "sudo arp-scan --interface=wlan0 192.168.0.1/24";
	public static final  String IP_PREFIX = "192.168";
	public static final  String MOD_IP= "ip";
	
	public final static String METHOD_CREATE = "CREATE";
	public final static String METHOD_DELETE = "DELETE";

	/** Generic execute method name */
	public final static String METHOD_EXECUTE = "EXECUTE";
	public final static String METHOD_RETRIEVE = "RETRIEVE";
	public static String REQENTITY = System.getProperty("org.eclipse.om2m.adminRequestingEntity","");
	public final static String SCLID = System.getProperty("org.eclipse.om2m.sclBaseId","");
	public static final String ADMIN_REQUESTING_ENTITY = System.getProperty("org.eclipse.om2m.adminRequestingEntity","admin/admin");

	
	public final static String PATH_CAPABILITIES = "capabilities";
	public final static String PATH_INVOKE = "invoke";
	public static final String PATH_UNKNOWN_DEVICES="unknown";
	public static final String PATH_DEVICES_ALL="all";

	
	public static final String UNKNOWN_DEVICES_URI="/gscl/applications/CIMA/devices/unknown";
	public final static String PATH_TEST_INVOKE = "test"; 
	//public final static String PATH_CAPABILITIES = "capabilities";	
}