package fr.liris.cima.gscl.device.service;

import fr.liris.cima.gscl.commons.Capability;

public interface ConfigManager {

	public void addCapability(String deviceId, String capabilityId, Capability capability);
	public void removeCapability(String deviceId, String capabilityId );
	public void setCapability(String deviceId, String capabilityId, Capability capability);
	public void start();
}
