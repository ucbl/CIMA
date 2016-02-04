package fr.liris.cima.nscl.commons;

import fr.liris.cima.nscl.commons.constants.Configuration;
import obix.Contract;
import obix.Obj;
import obix.Op;
import obix.Str;
import obix.Uri;
import obix.io.ObixEncoder;

/**
 * A simple parameter (an identifier, a description, the parameter type)
 */
public class Parameter {
	private String idP;
	private String desc;
	private String type;

	public Parameter() {
		idP = "";
		desc = "";
		type = "";
	}

	public Parameter(String idP, String desc, String type) {
		this.idP = idP;
		this.desc = desc;
		this.type = type;
	}

	public String getIdP() {
		return idP;
	}

	public void setIdP(String idP) {
		this.idP = idP;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return "Parameter ( idP : " + idP + " desc : " + desc + " type : " + type + " )\n";
	}
}