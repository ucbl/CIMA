package fr.liris.cima.gscl.discovery_ptut;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import java.io.*;


public class Activator implements BundleActivator {
	/** Logger */
	private static Logger logger = Logger.getLogger(Activator.class.getName());
	private  static  Handler fh ;

	private ServiceRegistration serviceRegistration;


	/** Device Managed tracker */
	private ServiceTracker<Object, Object> deviceServiceServiceTracker;

	/** a device managed service*/
	private ManagedDeviceService managedDeviceService;

	public void start(BundleContext context) throws Exception {
		try{
		fh = new FileHandler("log/gsclDiscoveryPut.log", false);
		logger.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


		System.out.println("Hello World!!");

		       // track the managed device service
				deviceServiceServiceTracker = new ServiceTracker<Object, Object>(context, ManagedDeviceService.class.getName(), null) {
					public void removedService(ServiceReference<Object> reference, Object service) {
						logger.info("ManagedDeviceService removed");
					}

					public Object addingService(ServiceReference<Object> reference) {
						logger.info("managedDeviceService discovered in cima : discovery");
						managedDeviceService = (ManagedDeviceService) this.context.getService(reference);
						serviceRegistration = this.context.registerService(DiscoveryService.class.getName(), new Discovery_ptut(), null);

						logger.info(managedDeviceService.getClass().getName());
						logger.info("DiscoveryService registered successfully");
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
