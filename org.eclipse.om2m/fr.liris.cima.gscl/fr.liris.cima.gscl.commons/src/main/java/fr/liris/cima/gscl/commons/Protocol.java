package fr.liris.cima.gscl.commons;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines generic, object to provide information for how to protocol use to invoke, get, etc capabilities.
 */
public class Protocol {

	private String name;
	private Map<String, String> parameters;

	public Protocol() {
		parameters = new HashMap<String, String>();
	}

	public Protocol(String name) {
		this.name = name;
		parameters = new HashMap<String, String>();
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addParameter(String key, String value) {
		parameters.put(key, value);
	}

	public void removeParameter(String key) {
		parameters.remove(key);
	}

	public String getParameterValue(String key) {
		return parameters.get(key);
	}

	public String toString() {
		String result = "";
		result = "Protocol("+name + ", " +parameters+")";

		return result;
	}

//	public String toObixFormat() {
//		Obj objProtocol = new Obj("protocol");
//
//		for(Entry<String, String> entry : parameters.entrySet()) {
//			objProtocol.add(new Str(entry.getKey(),entry.getValue()));
//		}
//		return ObixEncoder.toString(objProtocol);
//	}
//	
//	public Obj toObj() {
//		Obj objProtocol = new Obj("protocol");
//
//		for(Entry<String, String> entry : parameters.entrySet()) {
//			objProtocol.add(new Str(entry.getKey(),entry.getValue()));
//		}
//		return objProtocol;
//	}

}
