package fr.liris.cima.gscl.device.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.commons.rest.RequestIndication;


import fr.liris.cima.gscl.commons.Capability;
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
	public String unknownDevicesToObixFormat();
	public Device getUnknownDevice(String deviceId);
	public void invokeCapability(String deviceId, Capability capability,
			RestClientService clientService);
	public List< Capability> getUnknownDeviceCapabilities(String deviceId);
	public String capabilitiesToObixFormat(List<Capability> capabilities);
	String devicesToObixFormat(List<Device> devices);
	Capability getCapabilityToUnknownDevice(String deviceId, String capabilityId);
	boolean removeCapabilityToUnknownDevice(String deviceId, String capabilityId);
	public Capability updateUnknownDeviceCapability(String deviceId,
			Capability capability);

}