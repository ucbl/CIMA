package fr.liris.cima.gscl.commons;


import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import fr.liris.cima.gscl.commons.constants.Configuration;
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
	private List<String> keywords;
	private int cloudPort;
	private List<Parameter>  parameters;
	private Result result;

	/** Configuration's type of the Capability(automatic or manual) */
	private String configuration;

	public Capability(String name, Protocol protocol, List<String> keywords, int cloudPort, List<Parameter> parameters, Result result) {
		this.name = name;
		this.protocol = protocol;
		this.keywords = keywords;
		this.setConfiguration("automatic");
		this.cloudPort=cloudPort;
		this.parameters = parameters;
		this.result = result;
	}
	public Capability(String name, Protocol protocol, List<String> keywords, List<Parameter> parameters, Result result) {
		this.name = name;
		this.protocol = protocol;
		this.keywords = keywords;
		this.parameters = parameters;
		this.result = result;
	}

	public  Capability(String name) {
		this.name = name;
		protocol = new Protocol();
		keywords = new ArrayList<String>();
		parameters = new ArrayList<Parameter>();
		result = null;
	}

	public  Capability() {
		protocol = new Protocol();
		keywords = new ArrayList<String>();
		parameters = new ArrayList<Parameter>();
		result = null;
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

	//	public String toObixFormat() {
	//		return ObixEncoder.toString(this.toObj());
	//	}
	//	
	//	public Obj toObj() {
	//		Obj obj = new Obj();
	//
	//		obj.add(new Str("id",name));
	//		obj.add(protocol.toObj());
	//		obix.List keywords = new obix.List("keywords");
	//		obix.Str sK = null;
	//		for(String k : this.keywords){
	//			sK = new Str(k);
	//			keywords.add(sK);
	//		}
	//		obj.add(keywords);
	//		return obj;
	//	}

	public String toString() {
		String res = "capabilities (" + name + ", "  + protocol + ", " + keywords + ", ";
		
		for (Parameter p : parameters) {
			res += p.toString() + ", ";
		}

		res += result.toString() + ")";

		return  res;
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
		this.keywords.add(keyword);
	}

	public void addKeyword(List<String> keywords){
		this.keywords.addAll(keywords);
	}

	public void setKeywords(List<String>keywords){
		this.keywords = keywords;
	}

	public static void main(String args[]) {
		Capability capability = new Capability("ev3");
		Protocol protocol = new Protocol("http");
		protocol.addParameter("method", "post");
		capability.setProtocol(protocol);
		List<String> k = new ArrayList<>();
		k.add("ev3");
		k.add("back");
		k.add("robot");
		capability.setKeywords(k);

		//		System.out.println(ObixEncoder.toString(capability.toObj()));
		//	System.out.println(capability.toObixFormat());
	}

	public int getCloudPort() {
		return cloudPort;
	}

	public void setCloudPort(int cloudPort) {
		this.cloudPort = cloudPort;
	}
	public String getConfiguration() {
		return configuration;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public Result getResult() {
		return result;
	}

	public void addParameter(Parameter param) {
		parameters.add(param);
	}

	public void setResult(Result result) {
		this.result = result;
	}
}