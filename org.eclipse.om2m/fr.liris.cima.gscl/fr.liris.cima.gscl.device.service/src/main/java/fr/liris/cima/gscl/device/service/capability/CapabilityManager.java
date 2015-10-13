package fr.liris.cima.gscl.device.service.capability;

import java.util.List;

import fr.liris.cima.gscl.commons.Capability;
/**
 * Capability manager provide methods to manage capabilities in the GSCL
 * @author Rémi Desmargez <remi.desmargez@etu.univ-lyon1.fr>
 */
public interface CapabilityManager {
	/**
	 * Add a aviable Capability
	 * @param c the new Capability
	 * @return true if the capability is correctly added
	 */
	public boolean add(Capability c);
	
	/**
	 * Remove a Capability
	 * @param c ID of the Capability to remove
	 * @return true if the Capabiltiy is correctly removed
	 */
	public boolean remove(String c);
	
	/**
	 * Give the list of all avaibles Capabilities
	 * @return the list of capabilities
	 */
	public List<Capability> getCapabilities();
	
	/**
	 * Give all capabilities which match with the filter
	 * @param filter String of keywords separate with "+"
	 * @return the list wich match with filter
	 */
	public List<Capability> getCapabilities(String filter);
}
