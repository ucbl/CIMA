package fr.liris.cima.comm.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProtocolResolver {
	Map<String,Class<? extends AbstractProtocol>> protocols;
	
	public ProtocolResolver(){
		protocols = new HashMap<String,Class<? extends AbstractProtocol>>();
	}
	
	public void addProtocol(String protocolName, Class<? extends AbstractProtocol> protocol){
		this.protocols.put(protocolName,protocol);
	}
	
	public void removeProtocol(String protocolName){
		this.protocols.remove(protocolName);
	}
	
	public Class<? extends AbstractProtocol> getProtocol(String protocolName){
		return this.protocols.get(protocolName);
	}
	
	public Map<String,Class<? extends AbstractProtocol>> getAllProtocol(){
		return this.protocols;
	}
}
