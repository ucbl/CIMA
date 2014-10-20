package fr.liris.cima.nscl.device.service;

import java.util.List;
import java.util.Set;

import fr.liris.cima.nscl.commons.Device;
import fr.liris.cima.nscl.commons.subscriber.ClientSubscriber;

/**
 Managed Device  interface.
 * @author madiallo
 *
 */
public interface ManagedDeviceService {

	/**
	 * Get a device via id
	 * @param deviceId - The device id.
	 * @return The returned device
	 */
	public Device getDevice(String deviceId);
	
	/**
	 * Get all devices
	 * @return The returned list of devices
	 */
	public List<Device> getDevices();
	
	
	/**
	 * Retrieve a device by address
	 * @param address - The address of device
	 * @return
	 */
	public Device getDeviceByAddress(String address);
	
	/**
	 * Add a device
	 * @param device - The device to add.
	 */
	public void addDevice(Device device);
	/**
	 * Remove a device
	 * @param device - The device to remove.
	 */
	public void removeDevice(Device device);
	public void start();
	/**
	 * Remove a device via Id
	 * @param deviceId - The device id
	 */
	Device removeDevice(String deviceId);
	
	/**
	 * Add a subscriber
	 * @param subscriber - The subscriber to add.
	 */
	public boolean addSubscriber(ClientSubscriber subscriber);
	/**
	 * Remove a subscriber
	 * @param subscriber - The subscriber to remove.
	 */
	boolean removeSubscriber(ClientSubscriber subscriber);
	
	/**
	 * Get all subscribers
	 * @return The subscribers
	 */
	public Set<ClientSubscriber> getSubscribers();

}
