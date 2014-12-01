package fr.liris.cima.nscl.commons;


import java.util.ArrayList;
import java.util.List;
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

	/** The name of the capability*/
	private String name;
	/** Protocol for getting, invoking capability */
	private Protocol protocol;

	/** The key words associated  */
	private List<String> keywords;

	public Capability(String name, Protocol protocol, List<String> keywords) {
		this.name = name;
		this.protocol = protocol;
		this.keywords = keywords;
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
		Op op = new Op();
		op.setName(this.name);
		op.setHref(new Uri(sclId+"/"+"applications/"+appId+"/"+apocPath+"/"+this.name));
		op.setIs(new Contract("execute"));
		op.setIn(new Contract("obix:Nil"));
		op.setOut(new Contract("obix:Nil"));

		return op;
	}
	
	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}	

	public List<String> getKeywords(){
		return this.keywords;
	}

	public void addKeyword(String keyword){
		if(this.keywords == null) {
			this.keywords = new ArrayList<String>();
		}
		this.keywords.add(keyword);
	}

	public void addKeyword(List<String> keywords){
		this.keywords.addAll(keywords);
	}

	public void setKeywords(List<String>keywords){
		this.keywords = keywords;
	}
	
	public String toString() {
		return "capabilities (" + name + ", "  + protocol + ", " + keywords + ")";
	}
}