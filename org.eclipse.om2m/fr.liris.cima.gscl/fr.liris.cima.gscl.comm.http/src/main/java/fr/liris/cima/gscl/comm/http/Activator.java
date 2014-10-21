package fr.liris.cima.gscl.comm.http;

import java.util.Dictionary;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.core.service.SclService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import fr.liris.cima.gscl.device.service.ManagedDeviceService;
/**
 *  Manages the starting and stopping of the bundle.
 *  @author hady
 */
public class Activator implements BundleActivator {
	/** Logger */
	private static Log logger = LogFactory.getLog(Activator.class);
	/** SCL service tracker */
	private ServiceTracker<Object, Object> sclServiceTracker;
	
	private ServiceRegistration serviceRegistration;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Dictionary props=new Properties();
		props.put("fr.liris.cima.gscl.comm.plateform", "cima");
        this.serviceRegistration = bundleContext.registerService(RestClientService.class.getName(), new CIMARestHttpClient(), props);
		logger.info("registered RestClientService Http");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger.error("Unregistered RestClientService Http");
		serviceRegistration.unregister();
	}
}