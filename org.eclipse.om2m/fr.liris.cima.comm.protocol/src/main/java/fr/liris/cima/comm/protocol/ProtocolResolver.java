package fr.liris.cima.comm.protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to know and list the available protocols in the current instance of CIMA.
 * To add a protocol you need to create a class which extends {@code AbstractProtocol} and implement all non implemented methods.
 * @author remi
 *
 */
public class ProtocolResolver {
	
	/**
	 * protocols contains all available protocols classes.
	 */
	Map<String,Class<? extends AbstractProtocol>> protocols;
	
	/**
	 * Default constructor.
	 */
	public ProtocolResolver(){
		protocols = new HashMap<String,Class<? extends AbstractProtocol>>();
	}
	
	/**
	 * Add a protocol class to available protocols.
	 * @param protocolName name of the protocol typicaly {@code MyProtocol.class.getSimpleName().toLowerCase()}.
	 * @param protocol The class representing the protocol. This class must extends {@code AbstractProtocol}.
	 */
	public void addProtocol(String protocolName, Class<? extends AbstractProtocol> protocol){
		this.protocols.put(protocolName,protocol);
	}
	
	/**
	 * Remove a protocol from available protocols list.
	 * @param protocolName the of the protocol to remove.
	 */
	public void removeProtocol(String protocolName){
		this.protocols.remove(protocolName);
	}
	 /**
	  * GETTER
	  * @param protocolName the of the protocol to get.
	  * @return the Class of the protocol you want.
	  */
	public Class<? extends AbstractProtocol> getProtocol(String protocolName){
		return this.protocols.get(protocolName);
	}
	
	/**
	 * Get all available protocols.
	 * @return {@code Map<String,Class>} with key as the name of a protocol and value as a Class of the protocol key.
	 */
	public Map<String,Class<? extends AbstractProtocol>> getAllProtocol(){
		return this.protocols;
	}
}
