package fr.liris.cima.nscl.commons;

public class UID {

	private static int cpt = 0;
	
	private String uid; 
	private final String DEFAULT_PREFIX_UID = "DEVICE";
	public UID() {
		uid = DEFAULT_PREFIX_UID + "_" + (cpt++);
	}
	
	public UID(String prefix) {
		this.uid = prefix + "_"+cpt;
		cpt++;
	}

	/**
	 * @return the uid
	 */
	public final String getUid() {
		return uid;
	}
}
