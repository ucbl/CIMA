package fr.liris.cima.nscl.commons;

import fr.liris.cima.nscl.commons.constants.Configuration;
import obix.Contract;
import obix.Obj;
import obix.Op;
import obix.Str;
import obix.Uri;
import obix.io.ObixEncoder;

/**
 * Define the result (a type and a description) send by a device, when get request by NSCL
 */
public class Result {
	private String type;
	private String desc;

	public Result() {
		type = "";
		desc = "";
	}

	public Result(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return "Result ( desc : " + desc + " type : " + type + " )\n";
	}
}