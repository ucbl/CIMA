
package fr.liris.cima.gscl.discovery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import fr.liris.cima.gscl.device.service.ManagedDeviceService;
import fr.liris.cima.gscl.device.service.discovery.DiscoveryService;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.File;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.*;
/**
 *  Manages the starting and stopping of the bundle.
 *  @author madiallo
 */
public class Activator implements BundleActivator {
	/** Logger */
	private static Logger logger = Logger.getLogger(Activator.class.getName());
	private  static  Handler fh ;
	private ServiceRegistration serviceRegistration;

	/** Rest client tracker */
	private ServiceTracker<Object, Object> restClientServiceTracker;

	/** Device Managed tracker */
	private ServiceTracker<Object, Object> deviceServiceServiceTracker;

	/** a device managed service*/
	private ManagedDeviceService managedDeviceService;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		try{
		fh = new FileHandler("log/gsclDiscovery.log", false);
		logger.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


        // track the managed device service
		deviceServiceServiceTracker = new ServiceTracker<Object, Object>(bundleContext, ManagedDeviceService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("ManagedDeviceService removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("managedDeviceService discovered in cima : discovery");
				managedDeviceService = (ManagedDeviceService) this.context.getService(reference);
				logger.info(managedDeviceService.getClass().getName());
				logger.info("DiscoveryService registered successfully");
				return managedDeviceService;
			}
		};
        // open the managed device service tracker

		deviceServiceServiceTracker.open();

        // track the managed device service
		restClientServiceTracker = new ServiceTracker<Object, Object>(bundleContext, RestClientService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("RestClientService removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("RestClientService discovered in cima : discovery");
				RestClientService restClientService = (RestClientService) this.context.getService(reference);
				logger.info("*************ManagedDeviceService*********"+managedDeviceService);
				if(reference.getProperty("fr.liris.cima.gscl.comm.plateform") !=null) {
					serviceRegistration = this.context.registerService(DiscoveryService.class.getName(), new DeviceDiscovery(restClientService, managedDeviceService), null);
					logger.info("DiscoveryService registered successfully");
				}
				return restClientService;
			}
		};
        // open the rest client service tracker
		restClientServiceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger.severe("Unregistered DiscoveryService");
		serviceRegistration.unregister();
	}
}
