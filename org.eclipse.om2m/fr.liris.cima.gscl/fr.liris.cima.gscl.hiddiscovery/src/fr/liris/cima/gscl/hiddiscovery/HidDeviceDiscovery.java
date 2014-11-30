package fr.liris.cima.gscl.hiddiscovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;

import fr.liris.cima.gscl.commons.Device;
import fr.liris.cima.gscl.commons.ExecuteShellComand;
import fr.liris.cima.gscl.commons.constants.*;
import fr.liris.cima.gscl.commons.parser.*;
import fr.liris.cima.gscl.device.service.discovery.DiscoveryService;
import fr.liris.cima.gscl.device.service.*;

import fr.liris.cima.gscl.*;

/**
 * Specific device discover, for discovering a device in the local network
 * @author madiallo
 *
 */
public class HidDeviceDiscovery implements DiscoveryService{

	private static Log LOGGER = LogFactory.getLog(HidDeviceDiscovery.class);
	public static final String ADMIN_REQUESTING_ENTITY = System.getProperty("org.eclipse.om2m.adminRequestingEntity","admin/admin");

	// A device managed service
	private ManagedDeviceService deviceService;

	

	public HidDeviceDiscovery() {

	}
	
	@Override
	public void doDiscovery() {
		 System.out.println("doDiscovery PIUX\n\n");
	        
		 DeviceFinder DF = new DeviceFinder();
			DF.FindAllNewUpdates();
	        
	}
}