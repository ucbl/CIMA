package fr.liris.cima.gscl.mgmtdevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.core.service.SclService;

import fr.liris.cima.gscl.commons.Capability;
import fr.liris.cima.gscl.device.service.capability.CapabilityManager;

public class CapabilityManagerImpl implements CapabilityManager {

	static Map<String,Capability> capabilities;
	
	/** Discovered SCL service*/
	static SclService SCL;

	public CapabilityManagerImpl() {
		capabilities = new HashMap<>();
	}

	public CapabilityManagerImpl(SclService scl) {
		SCL = scl;
		capabilities = new HashMap<>();
	}
	
	public static void init(SclService scl) {
		capabilities = new HashMap<>();
	}


	@Override
	public boolean add(Capability c) {
		if(capabilities.get(c.getName()) != null)
			return false;
		else{
			capabilities.put(c.getName(), c);
			return true;
		}
	}

	@Override
	public boolean remove(String c) {
		return capabilities.remove(c).getName() == c;
	}

	@Override
	public List<Capability> getCapabilities() {
		return new ArrayList<Capability>(capabilities.values());
	}

	@Override
	public List<Capability> getCapabilities(String filter) {
		// TODO Filter
		
		return new ArrayList<Capability>();
	}
}
