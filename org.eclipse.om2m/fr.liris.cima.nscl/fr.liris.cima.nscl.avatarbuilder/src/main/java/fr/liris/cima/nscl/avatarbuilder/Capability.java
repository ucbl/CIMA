package fr.liris.cima.nscl.avatarbuilder;

import obix.Contract;
import obix.Obj;
import obix.Op;
import obix.Str;
import obix.Uri;
import obix.io.ObixEncoder;

/**
 *  Capability class avalaible for avatar
 */
public class Capability {

	private String name;
	private String functionalityImpl;
	private String type;

	public Capability(String name, String functionalityImpl, String type) {
		this.name = name;
		this.functionalityImpl = functionalityImpl;
		this.type = type;
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
	 * @return the functionalityImple
	 */
	public String getFunctionalityImpl() {
		return functionalityImpl;
	}
	/**
	 * @param functionalityImpl the functionalityImple to set
	 */
	public void setFunctionalityImple(String functionalityImpl) {
		this.functionalityImpl = functionalityImpl;
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

	public String toObix(String sclId, String appId, String apocPath) {

		Obj obj = new Obj();

		// OP functionalityImpl
		obj.add(new Str("functionalityImpl",this.functionalityImpl));
		obj.add(new Str("type",this.type));


		// OP name
		Op op = new Op();
		op.setName(this.name);
		op.setHref(new Uri(sclId+"/"+"applications/"+appId+"/"+apocPath+"/"+this.name));
		op.setIs(new Contract("execute"));
		op.setIn(new Contract("obix:Nil"));
		op.setOut(new Contract("obix:Nil"));
		obj.add(op);

		return ObixEncoder.toString(obj);
	}

	public String toString() {
		return "Capability("+name +","+type + ", implements ==> "+functionalityImpl + ")";
	}

}
