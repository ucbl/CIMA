package fr.liris.cima.gscl.mgmtdevice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.liris.cima.gscl.commons.Capability;

public class ConfigManagerImpl {
	
	private Map<String, Map<String, Capability>> mapConfig;
	
	public ConfigManagerImpl() {
		mapConfig = new HashMap<>();
		
	}
	
	public void addCapability(String deviceId, String capabilityId,
			Capability capability) {

		Map<String, Capability> mapCapability = new HashMap<>();
		mapCapability.put(capabilityId, capability);
		mapConfig.put(deviceId, mapCapability);
	}

	
//	public void removeCapability(String deviceId, String capabilityId) {
//		mapConfig.get(deviceId).remove(capabilityId);
//	}
	
	public List<Capability> getDeviceCapabilities(String deviceId) {
		List<Capability> capabilities = new ArrayList<>();
		capabilities.addAll(mapConfig.get(deviceId).values());
		return capabilities;
	}
	
	public Capability getCapabilityToDevice(String deviceId, String capabilityId) {
		Map<String, Capability> mapCapa = mapConfig.get(deviceId);
		if(mapCapa != null) return mapCapa.get(capabilityId);
		else return null;
	}
	
	public boolean removeCapabilityDevice(String deviceId, String capabilityId) {
		Map<String, Capability> mapCapabilities = mapConfig.get(deviceId);
		int firstSize = mapCapabilities.size();
		mapCapabilities.remove(capabilityId);
		int secondSize = mapCapabilities.size();
		
		return firstSize - 1 == secondSize;
	}	
	
	public Capability updateCapability(String deviceId, Capability capability) {
		Map<String, Capability> mapCapabilities = mapConfig.get(deviceId);
		if (mapCapabilities == null){
			mapCapabilities = new HashMap<String, Capability>();
			mapConfig.put(deviceId, mapCapabilities);
		}
		mapCapabilities.put(capability.getName(), capability);
		return capability;
	}

	public void setCapability(String deviceId, String capabilityId,
			Capability capability) {

	} 

}
