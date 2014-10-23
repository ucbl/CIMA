package fr.liris.cima.gscl.commons;


import java.util.Map.Entry;

import obix.Contract;
import obix.Obj;
import obix.Op;
import obix.Str;
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
	
	public  Capability(String name) {
		this.name = name;
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

	public String toObixFormat() {
		Obj objProtocol = new Obj();
		Obj obj = new Obj();

		obj.add(new Str("id",name));
		obj.add(protocol.toObj());
		return ObixEncoder.toString(obj);
	}
	
	public Obj toObj() {
		Obj obj = new Obj();

		obj.add(new Str("id",name));
		obj.add(protocol.toObj());
		return obj;
	}
	
	public String toString() {
		return "capabilities (" + name + ", "  + protocol + ")\n";
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}	
	
	public static void main(String args[]) {
		Capability capability = new Capability("ev3");
		Protocol protocol = new Protocol("http");
		protocol.addParameter("method", "post");
		capability.setProtocol(protocol);
		
		System.out.println(ObixEncoder.toString(capability.toObj()));
	}
}