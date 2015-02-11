package fr.liris.cima.nscl.commons;

import java.util.HashSet;
import java.util.Set;

import fr.liris.cima.nscl.commons.constants.Configuration;


//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * Defines a device for CIMA NSCL
 * @author madiallo
 *
 */
public class Device {

	/** Logger */
//	private static Log LOGGER = LogFactory.getLog(Device.class);
	/** Application point of contact for the devices controller {@link DeviceController} */
	public final static String APOCPATH = "devices";
	/** Default Device type */
	public final static String TYPE = "DEVICE";


	/** capabilities of device*/
	private Set<Capability> capabilities;
	
	/** Information for contacting the device in the cloudby-passing the OM2M layer*/
	private ContactInfo contactInfo;

	/** A simple device description*/
	private DeviceDescription deviceDescription;
	
	/** Configuration's type of the Device(automatic or manual) */
	private Configuration configuration;
	

	public Set<Capability> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(Set<Capability> capabilities) {
		this.capabilities = capabilities;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Device(DeviceDescription deviceDescription, ContactInfo contactInfo) {
		this.deviceDescription = deviceDescription;
		this.contactInfo = contactInfo;
		capabilities = new HashSet<>();
	}
	public final String getId() {
		return this.deviceDescription.getId();
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.deviceDescription.setId(id);
	}

	public String getName() {
		return this.deviceDescription.getName();
	}

	public void setName(String name) {
		this.deviceDescription.setName(name);
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return deviceDescription.getUri();
	}

	public String getDateConnection() {
		return this.deviceDescription.getDateConnection();
	}

	public String getModeConnection() {
		return this.deviceDescription.getModeConnection();
	}

	public void addCapability(Capability capability) {
		capabilities.add(capability);
	}

	public void removeCapability(Capability capability) {
		capabilities.remove(capability);
	}

	public boolean equals(Object other) {
		Device device = (Device)other;

		return this.deviceDescription.getId().equals(device.getDeviceDescription().getId());
	}

	public DeviceDescription getDeviceDescription() {
		return deviceDescription;
	}

	public void setDeviceDescription(DeviceDescription deviceDescription) {
		this.deviceDescription = deviceDescription;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Device[");
		sb.append(deviceDescription);
		sb.append(", ");
		sb.append(contactInfo);
		sb.append(", ");
		sb.append(capabilities);
		sb.append("]\n");
		return sb.toString();
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}