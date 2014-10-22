package fr.liris.cima.gscl.commons;


import obix.Contract;
import obix.Obj;
import obix.Op;
import obix.Uri;
import obix.io.ObixEncoder;

/**
 * This class represent a single capability for a device
 * @author madiallo
 *
 */
public class Capability {

	private String name;
	private Protocol protocol;

	public Capability(String name, Protocol protocol) {
		this.name = name;
		this.protocol = protocol;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	
	public Op toObix(String sclId, String appId, String apocPath) {

		// OP Hello
		Op op = new Op();
		op.setName(this.name);
		op.setHref(new Uri(sclId+"/"+"applications/"+appId+"/"+apocPath+"/"+this.name));
		op.setIs(new Contract("execute"));
		op.setIn(new Contract("obix:Nil"));
		op.setOut(new Contract("obix:Nil"));

		return op;
	}

	
	public String toString() {
		return "capabilities (" + name + ", "  + protocol + ")";
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}	
}