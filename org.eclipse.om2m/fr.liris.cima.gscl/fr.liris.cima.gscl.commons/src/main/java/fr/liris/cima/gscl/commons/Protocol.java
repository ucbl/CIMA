package fr.liris.cima.gscl.commons;

import java.util.HashMap;
import java.util.Map;

public class Protocol {

	private String name;
	private Map<String, String> parameters;
	
	public Protocol() {
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
	
}
