package fr.liris.cima.gscl.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.ipu.service.IpuService;

import fr.liris.cima.gscl.device.service.ConfigManager;

public class ConfigController implements IpuService {

	static String apocPath = "devices";
	
	private static Log LOGGER = LogFactory.getLog(ConfigController.class);

	
	public static RestClientService restClientService;
	private ConfigManager configManager;
	
	
	public ConfigController(ConfigManager manager) {
		this.configManager = manager;
	}
	
	@Override
	public ResponseConfirm doExecute(RequestIndication requestIndication) {
		return null;
	}

	@Override
	public ResponseConfirm doRetrieve(RequestIndication requestIndication) {
		//String path [] = requestIndication.getTargetID().split("/");
		
		LOGGER.info("*******RETRIEVE IN CONFIGCONTROLLER*****");
		
	
		return new ResponseConfirm();
	}

	@Override
	public ResponseConfirm doUpdate(RequestIndication requestIndication) {
		return null;
	}

	@Override
	public ResponseConfirm doDelete(RequestIndication requestIndication) {
		return null;
	}

	@Override
	public ResponseConfirm doCreate(RequestIndication requestIndication) {
		return null;
	}

	@Override
	public String getAPOCPath() {
		return "devices";
	}

}
