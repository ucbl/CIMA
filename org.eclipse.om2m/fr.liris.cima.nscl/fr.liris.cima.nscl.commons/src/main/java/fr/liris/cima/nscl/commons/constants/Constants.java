package fr.liris.cima.nscl.commons.constants;

public class Constants {

public static String APOCPATH = "devices";
	
	public final static String METHOD_CREATE = "CREATE";
	/** Generic execute method name */
	public final static String METHOD_EXECUTE = "EXECUTE";
	public final static String METHOD_RETRIEVE = "RETRIEVE";
	public final static String METHOD_UPDATE = "UPDATE";

	public static String REQENTITY = System.getProperty("org.eclipse.om2m.adminRequestingEntity","");
	public final static String SCLID = System.getProperty("org.eclipse.om2m.sclBaseId","");
	
	public static final String APOCPATH_SUBSCRIBERS = "subscribers";
	public static final String APOCPATH_DEVICES = "devices";
	public static final String CIMA_ADDRESS = "localhost";
}
