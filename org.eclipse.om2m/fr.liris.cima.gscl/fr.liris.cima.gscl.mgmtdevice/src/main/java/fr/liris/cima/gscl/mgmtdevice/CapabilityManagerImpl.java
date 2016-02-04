package fr.liris.cima.gscl.mgmtdevice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.core.service.SclService;

import fr.liris.cima.gscl.commons.Capability;
import fr.liris.cima.gscl.commons.Protocol;
import fr.liris.cima.gscl.device.service.capability.CapabilityManager;

/**
 * Manage in GSCL all the capability avalaible.
 */
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
		List<Capability> filtredCapabilities = new ArrayList<>();
		Collection<Capability> capabilities = this.capabilities.values();
		String [] filters = filter.split("\\+");
		List<String> capabilityKeywords = new ArrayList<>();
		boolean isFiltred = false;
		for(Capability c : capabilities){
			//init keywords list
			capabilityKeywords.add(c.getName());
			capabilityKeywords.addAll(c.getKeywords());
			
			for(String f : filters){
				// if a keyword filter is not in the capability keywords then the capability is not filtred
//				if(!capabilityKeywords.contains(f)){
//					isFiltred = false;
//				}
				for(String k : capabilityKeywords){
					if(k.toLowerCase().contains(f.toLowerCase())){
						isFiltred = true;
					}
				}
			}
			if(isFiltred){
				filtredCapabilities.add(c);
			}
			// reinit
			capabilityKeywords.clear();
			isFiltred = false;
		}
		
		return filtredCapabilities;
	}
	
	public static void main(String [] args){
		CapabilityManagerImpl capabilityManagerImpl = new CapabilityManagerImpl();
		Protocol p = new Protocol("http");
		List<String> l = new ArrayList<>();
		l.add("k1");
		l.add("k2");
		l.add("k3");
		Capability c = new Capability("test1", p, l, null, null);
		capabilityManagerImpl.add(c);
		l = new ArrayList<>();
		l.add("k1");
		l.add("k2");
		l.add("k3");
		l.add("k4");
		c = new Capability("test2", p, l, null, null);
		capabilityManagerImpl.add(c);
		
		List<Capability> filtredCapa = capabilityManagerImpl.getCapabilities("k1");
		System.out.println("filtre sur k1");
		System.out.println("  nombre de resultats : " + filtredCapa.size() + "/2");
		for(Capability capa : filtredCapa){
			System.out.println("  " + capa.getName());
		}
		
		filtredCapa = capabilityManagerImpl.getCapabilities("k1+k2+k3");
		System.out.println("filtre sur k1+k2+k3");
		System.out.println("  nombre de resultats : " + filtredCapa.size() + "/2");
		for(Capability capa : filtredCapa){
			System.out.println("  " + capa.getName());
		}

		filtredCapa = capabilityManagerImpl.getCapabilities("k1+k2+k3+k4");
		System.out.println("filtre sur k1+k2+k3+k4");
		System.out.println("  nombre de resultats : " + filtredCapa.size() + "/1");
		for(Capability capa : filtredCapa){
			System.out.println("  " + capa.getName());
		}
		
		filtredCapa = capabilityManagerImpl.getCapabilities("kapa");
		System.out.println("filtre sur kapa");
		System.out.println("  nombre de resultats : " + filtredCapa.size() + "/0");
		for(Capability capa : filtredCapa){
			System.out.println("  " + capa.getName());
		}
		
		filtredCapa = capabilityManagerImpl.getCapabilities("k1+k2+k3+kapa");
		System.out.println("filtre sur k1+k2+k3+kapa");
		System.out.println("  nombre de resultats : " + filtredCapa.size() + "/0");
		for(Capability capa : filtredCapa){
			System.out.println("  " + capa.getName());
		}
	}
}
