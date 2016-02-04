package fr.liris.cima.gscl.device.service;

import fr.liris.cima.gscl.commons.Capability;

/**
 * Interface which provide method to add, remove or manage capabilities on a device.
 */
public interface ConfigManager {

	public void addCapability(String deviceId, String capabilityId, Capability capability);
	public void removeCapability(String deviceId, String capabilityId );
	public void setCapability(String deviceId, String capabilityId, Capability capability);
	public void start();
}
