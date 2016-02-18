package fr.liris.cima.nscl.avatarbuilder;

import java.util.HashSet;
import java.util.Set;

import obix.Obj;
import obix.Str;
import obix.io.ObixEncoder;

/**
 * Create Avatar instance by giving an device id, a protocol and an uri.
 */
public class Avatar {

	private String id;
	private String protocol;
	private String uri;
	
	private Set<Capability> capabilities;
	
	public Avatar(String id, String protocol, String uri) {
		this.id = id;
		this.protocol = protocol;
		this.uri = uri;
		this.capabilities = new HashSet<>();
	}
	
	public boolean addCapability(Capability capability) {
		return this.capabilities.add(capability);
	}
	
	public boolean removeCapability(Capability capability) {
		return this.capabilities.remove(capability);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	

	public String toObixFormat(String sclId) {
		// oBIX
		Obj obj = new Obj();
		obj.add(new Str("id",id));
		obj.add(new Str("protocol", protocol));
		obj.add(new Str("uri",uri));

		return ObixEncoder.toString(obj);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Avatar("+id + ","+protocol + ", " + uri + ",capabilities[ ");
		for(Capability capability : capabilities) {
			sb.append(capability);
		}
		sb.append("])");
		
		return sb.toString();
	}

	/**
	 * @return the capabilities
	 */
	public Set<Capability> getCapabilities() {
		return capabilities;
	}

	/**
	 * @param capabilities the capabilities to set
	 */
	public void setCapabilities(Set<Capability> capabilities) {
		this.capabilities = capabilities;
	}
}