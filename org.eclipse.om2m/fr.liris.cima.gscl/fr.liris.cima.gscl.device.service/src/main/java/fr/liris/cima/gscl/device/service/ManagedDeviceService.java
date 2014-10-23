package fr.liris.cima.gscl.device.service;

import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.commons.rest.RequestIndication;


import fr.liris.cima.gscl.commons.Device;

/**
 * Managed Device  interface.
 * @author madiallo
 */
public interface ManagedDeviceService {

	/**
	 * Get a device via id
	 * @param deviceId - The device id.
	 * @return The returned device
	 */
	public Device getDevice(String deviceId);
	/**
	 * Get a device via address
	 * @param address - The device id.
	 * @return The returned device
	 */
	public Device getDeviceByAddress(String address);
	
	/**
	 * Add a device
	 * @param device - The device to add.
	 */
	public void addDevice(Device device);
	
	/**
	 * Remove a device via Id
	 * @param deviceId - The device id
	 */
	public void removeDevice(String deviceId);

	
	/**
	 * Remove a device
	 * @param device - The device to remove.
	 */
	public void removeDevice(Device device);
	/**
	 * Start the manager
	 */
	public void start();
	
	public void addKnownDevice(Device device);
	
	public void removeKnownDevice(Device device);
	
	public Device getKnownDevice(String deviceId);
	
	
	public void removeUnknownDevice(Device device);
	
	public void addUnknownDevice(Device device);
	
	public void removeUnknownDeviceById(String id);
	
	public void updateUnknonwDevice(String deviceId, Device newDevice);
	public void updateDevice(String deviceId, Device newDevice);
	
	
	public boolean switchUnknownToKnownDevice(Device device);
	
	public void sendDeviceToNSCL(Device device, RestClientService clientService);
	
}