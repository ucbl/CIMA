package fr.liris.cima.nscl.mgmtdevice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.service.SclService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import fr.liris.cima.nscl.device.service.ManagedDeviceService;

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
	private static Logger LOGGER = Logger.getLogger(Activator.class.getName());
	private  static  Handler fh ;
	/** SCL service tracker */
	private ServiceTracker<Object, Object> sclServiceTracker;

	private ServiceRegistration serviceRegistration;


	@Override
	public void start(BundleContext bundleContext) throws Exception {
try{
		fh = new FileHandler("log/nsclMgmtDevice.log", false);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}

		/** track scl service*/
		sclServiceTracker = new ServiceTracker<Object, Object>(bundleContext, SclService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				LOGGER.info("SclService removed in mgmtdevice");
			}

			public Object addingService(ServiceReference<Object> reference) {
				LOGGER.info("SclService discovered in mgmtdevice");
				SclService sclService = (SclService) this.context.getService(reference);
				serviceRegistration = this.context.registerService(ManagedDeviceService.class.getName(), new DeviceManagerImpl(sclService) , null);
				LOGGER.info("ManagedDeviceService registered successfully");

				return sclService;
			}
		};
		/** open scl service tracker*/
		sclServiceTracker.open();

	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		LOGGER.severe("Unregistered ManagedDeviceService");
		serviceRegistration.unregister();
	}
}
