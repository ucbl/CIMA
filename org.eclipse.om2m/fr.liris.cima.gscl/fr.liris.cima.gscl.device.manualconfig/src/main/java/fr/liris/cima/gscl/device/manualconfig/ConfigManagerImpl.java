package fr.liris.cima.gscl.device.manualconfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.resource.Application;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.core.service.SclService;

import fr.liris.cima.gscl.commons.Capability;
import fr.liris.cima.gscl.commons.constants.Constants;
import fr.liris.cima.gscl.device.service.ConfigManager;

public class ConfigManagerImpl implements ConfigManager{


	private static Log LOGGER = LogFactory.getLog(ConfigManagerImpl.class);

	public final static String DESC = "DESCRIPTOR";

	/** Capabilities container */
	public final static String CAPABS = "CAPABILITIES";


	Map<String, Map<String, Capability>> mapConfig;

	/** Discovered SCL service*/
	static SclService SCL;


	public ConfigManagerImpl(SclService sclService) {
		mapConfig = new HashMap<>();
		SCL = sclService;
		createManagerResources("manualconfig", "config");
	}
	
	@Override
	public void start() {
		LOGGER.info("config waiting for attachement..");
		createManagerResources("manualconfig", "devices");
	}

	@Override
	public void addCapability(String deviceId, String capabilityId,
			Capability capability) {

		Map<String, Capability> mapCapability = new HashMap<>();
		mapCapability.put(capabilityId, capability);
		mapConfig.put(deviceId, mapCapability);
	}

	@Override
	public void removeCapability(String deviceId, String capabilityId) {
		mapConfig.get(deviceId).remove(capabilityId);
	}

	@Override
	public void setCapability(String deviceId, String capabilityId,
			Capability capability) {

	}

	public void createManagerResources(String appId, String aPoCPath) {
		// Create the Application resource
		ResponseConfirm response = SCL.doRequest(new RequestIndication(Constants.METHOD_CREATE,Constants.SCLID+"/applications",Constants.REQENTITY,new Application(appId,aPoCPath)));
		// Create Application sub-resources only if application not yet created
		if(response.getStatusCode().equals(StatusCode.STATUS_CREATED)) {
			// Create DESCRIPTOR container sub-resource
			SCL.doRequest(new RequestIndication(Constants.METHOD_CREATE,Constants.SCLID+"/applications/"+appId+"/containers",Constants.REQENTITY,new Container(DESC)));

			// Create CAPABILITIES container sub-resource
			SCL.doRequest(new RequestIndication(Constants.METHOD_CREATE, Constants.SCLID+"/applications/"+appId+"/containers",Constants.REQENTITY,new Container(CAPABS)));

			String content, targetID;
			// Create DESCRIPTION contentInstance on the DESCRIPTOR container resource
			content = "";
			//Device.getDescriptorRep(SCLID, appId);
			targetID= Constants.SCLID+"/applications/"+appId+"/containers/"+DESC+"/contentInstances";
			SCL.doRequest(new RequestIndication(Constants.METHOD_CREATE,targetID,Constants.REQENTITY,new ContentInstance(content.getBytes())));
		}

	}
}
