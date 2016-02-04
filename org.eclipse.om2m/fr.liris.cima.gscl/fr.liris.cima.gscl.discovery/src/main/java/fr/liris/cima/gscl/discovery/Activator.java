
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
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.log.*;
import org.osgi.framework.FrameworkUtil;

/**
 *  Manages the starting and stopping of GSCL.DISCOVERY bundle.
 *  @author madiallo
 */
public class Activator implements BundleActivator {
	/** Logger */
	private static Log logger = LogFactory.getLog(Activator.class);
	private ServiceRegistration serviceRegistration;
	/** Logger OSGI*/
	private static ServiceTracker logServiceTracker;
	private static LogService logservice;

	/** Rest client tracker */
	private ServiceTracker<Object, Object> restClientServiceTracker;

	/** Device Managed tracker */
	private ServiceTracker<Object, Object> deviceServiceServiceTracker;

	/** a device managed service*/
	private ManagedDeviceService managedDeviceService;

	@Override
	public void start(BundleContext bundleContext) throws Exception {

		logServiceTracker = new ServiceTracker(bundleContext, org.osgi.service.log.LogService.class.getName(), null);
			logServiceTracker.open();
			logservice = (LogService) logServiceTracker.getService();


        // track the managed device service
		deviceServiceServiceTracker = new ServiceTracker<Object, Object>(bundleContext, ManagedDeviceService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("ManagedDeviceService removed");
								logservice.log(LogService.LOG_ERROR, "ManagedDeviceService removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("managedDeviceService discovered in cima : discovery");
								logservice.log(LogService.LOG_ERROR, "managedDeviceService discovered in cima : discovery");
				managedDeviceService = (ManagedDeviceService) this.context.getService(reference);
				logger.info(managedDeviceService.getClass().getName());
								logservice.log(LogService.LOG_ERROR, managedDeviceService.getClass().getName());
								logger.info("DiscoveryService registered successfully");
												logservice.log(LogService.LOG_ERROR, "DiscoveryService registered successfully");
				return managedDeviceService;
			}
		};
        // open the managed device service tracker

		deviceServiceServiceTracker.open();

        // track the managed device service
		restClientServiceTracker = new ServiceTracker<Object, Object>(bundleContext, RestClientService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("RestClientService removed");
								logservice.log(LogService.LOG_ERROR, "RestClientService removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("RestClientService discovered in cima : discovery");
								logservice.log(LogService.LOG_ERROR, "RestClientService discovered in cima : discovery");
				RestClientService restClientService = (RestClientService) this.context.getService(reference);
				logger.info("*************ManagedDeviceService*********"+managedDeviceService.toString());
				logservice.log(LogService.LOG_ERROR, "*************ManagedDeviceService*********"+managedDeviceService.toString());

				if(reference.getProperty("fr.liris.cima.gscl.comm.plateform") !=null) {
					serviceRegistration = this.context.registerService(DiscoveryService.class.getName(), new DeviceDiscovery(restClientService, managedDeviceService), null);
					logger.info("DiscoveryService registered successfully");
					logservice.log(LogService.LOG_ERROR, "DiscoveryService registered successfully");

				}
				return restClientService;
			}
		};
        // open the rest client service tracker
		restClientServiceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger.error("Unregistered DiscoveryService");
				logservice.log(LogService.LOG_ERROR, "Unregistered DiscoveryService");
		serviceRegistration.unregister();
	}
}
