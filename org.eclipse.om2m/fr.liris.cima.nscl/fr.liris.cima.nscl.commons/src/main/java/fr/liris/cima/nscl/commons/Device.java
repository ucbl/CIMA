package fr.liris.cima.nscl.commons;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import fr.liris.cima.nscl.commons.Capability;
import fr.liris.cima.nscl.commons.ContactInfo;
import fr.liris.cima.nscl.commons.DeviceDescription;
import fr.liris.cima.nscl.commons.constants.Configuration;
import fr.liris.cima.nscl.commons.util.Utils;

/**
 * Defines a device for CIMA GSCL
 * @author madiallo
 *
*/
public class Device {

	/** Logger */
	//private static Log LOGGER = LogFactory.getLog(Device.class);
	/** Application point of contact for the devices controller {@link DeviceController} */
	public final static String APOCPATH = "devices";
	/** Default Device type */
	public final static String TYPE = "DEVICE";

	/** capabilities of device*/
	private List<Capability> capabilities;
	
	/** Information for contacting the device in the cloudby-passing the OM2M layer*/
	private ContactInfo contactInfo;

	/** A simple device description*/
	private DeviceDescription deviceDescription;
	
	/** Configuration's type of the Device(automatic or manual) */
	private String configuration = "automatic";
	
	private boolean known;

	public boolean isKnown() {
		return known;
	}

	public void setKnown(boolean known) {
		this.known = known;
	}

	public List<Capability> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(List<Capability> capabilities) {
		this.capabilities = capabilities;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}


	public Device() {
		this(new DeviceDescription());
		this.known = false;
	}

	public Device(DeviceDescription deviceDescription) {
		this.deviceDescription = deviceDescription;
//		contactInfo = new ContactInfo(deviceDescription.getId(), PortGenerator.generatePort());
		capabilities = new ArrayList<>();
//		this.configuration = "MANUAL";
		this.known = false;
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



	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(deviceDescription);
		sb.append(", ");
		sb.append(this.known);
		sb.append(", ");
		sb.append(this.configuration);
		sb.append(", ");
//		sb.append(contactInfo);
		sb.append(", ");
		sb.append(capabilities);
		sb.append("]\n");
		return sb.toString();
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

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.deviceDescription.setUri(uri);
	}

	public String getDateConnection() {
		return this.deviceDescription.getDateConnection();
	}

	public void setDateConnection(Date dateConnection) {
		this.deviceDescription.setDateConnection(Utils.dateToStr(dateConnection));
	}

	public String getModeConnection() {
		return this.deviceDescription.getModeConnection();
	}

	public void setModeConnection(String modeConnection) {
		this.deviceDescription.setModeConnection(modeConnection);
	}

	public void addCapability(Capability capability) {
		capabilities.add(capability);
	}
	
	public Capability getCapability(String capabilityName) {
		for(Capability capability : capabilities) {
			if(capability.getName().equals(capabilityName)) {
				return capability;
			}
		}
		return null;
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

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
}