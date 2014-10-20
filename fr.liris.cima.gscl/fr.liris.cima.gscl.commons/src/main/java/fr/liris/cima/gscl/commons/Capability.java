package fr.liris.cima.gscl.commons;

import java.util.ArrayList;
import java.util.List;

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
	private List<String> arguments;
	private String path;
	private String functionalityImple;
	private String type;

	public Capability(String name, String type, String functionalityImpl) {
		this.name = name;
		this.type = type;
		this.functionalityImple = functionalityImpl;
		this.arguments = new ArrayList<>();
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


	/**
	 * @return the arguments
	 */
	public List<String> getArguments() {
		return arguments;
	}

	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
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

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the functionalityImple
	 */
	public String getFunctionalityImple() {
		return functionalityImple;
	}

	/**
	 * @param functionalityImple the functionalityImple to set
	 */
	public void setFunctionalityImple(String functionalityImple) {
		this.functionalityImple = functionalityImple;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	public String toString() {
		return "capabilities (" + name + ", " + type + "," + functionalityImple + ")";
	}	
}