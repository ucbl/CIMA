package fr.liris.cima.gscl.hiddiscovery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 *  Manages the starting and stopping of GSCL.HIDDISCOVERY bundle.
 */
public class Activator implements BundleActivator {
	/** Logger */
	private static Log logger = LogFactory.getLog(Activator.class);
	/** Logger OSGI*/
	private static ServiceTracker logServiceTracker;
	private static LogService logservice;
	private ServiceRegistration serviceRegistration;


	/** Device Managed tracker */
	private ServiceTracker<Object, Object> deviceServiceServiceTracker;

	/** a device managed service*/
	private ManagedDeviceService managedDeviceService;

	public void start(BundleContext context) throws Exception {

		logServiceTracker = new ServiceTracker(context, org.osgi.service.log.LogService.class.getName(), null);
				logServiceTracker.open();
				logservice = (LogService) logServiceTracker.getService();


		System.out.println("Hello World!!");

		       // track the managed device service
				deviceServiceServiceTracker = new ServiceTracker<Object, Object>(context, ManagedDeviceService.class.getName(), null) {
					public void removedService(ServiceReference<Object> reference, Object service) {
						logger.info("ManagedDeviceService removed");
												logservice.log(LogService.LOG_ERROR, "ManagedDeviceService removed");
					}

					public Object addingService(ServiceReference<Object> reference) {
						logger.info("managedDeviceService discovered in cima : discovery");
												logservice.log(LogService.LOG_ERROR, "managedDeviceService discovered in cima : discovery");
						managedDeviceService = (ManagedDeviceService) this.context.getService(reference);
						serviceRegistration = this.context.registerService(DiscoveryService.class.getName(), new HidDeviceDiscovery(), null);

						logger.info(managedDeviceService.getClass().getName());
						logservice.log(LogService.LOG_ERROR, managedDeviceService.getClass().getName());

						logger.info("DiscoveryService registered successfully");
						logservice.log(LogService.LOG_ERROR, "DiscoveryService registered successfully");
						return managedDeviceService;
					}
				};
		        // open the managed device service tracker

				deviceServiceServiceTracker.open();

	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
	}

}
