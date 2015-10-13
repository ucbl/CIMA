package fr.liris.cima.nscl.commons;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines generic, object to provide information for how to protocol use to invoke, get, etc capabilities.
 * @author madiallo
 *
 */
public class Protocol {

	/** Protocol name*/
	private String name;
	
	/** generics parameters */
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
}
