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
/**
 *  Manages the starting and stopping of the bundle.
 *  @author madiallo
 */
public class Activator implements BundleActivator {
	/** Logger */
	private static Log logger = LogFactory.getLog(Activator.class);
	/** SCL service tracker */
	private ServiceTracker<Object, Object> sclServiceTracker;
	
	private ServiceRegistration serviceRegistration;


	@Override
	public void start(BundleContext bundleContext) throws Exception {

		/** track scl service*/
		sclServiceTracker = new ServiceTracker<Object, Object>(bundleContext, SclService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("SclService removed in mgmtdevice");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("SclService discovered in mgmtdevice");
				SclService sclService = (SclService) this.context.getService(reference);
				serviceRegistration = this.context.registerService(ManagedDeviceService.class.getName(), new DeviceManagerImpl(sclService) , null);
				logger.info("ManagedDeviceService registered successfully");

				return sclService;
			}
		};
		/** open scl service tracker*/
		sclServiceTracker.open();

	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger.error("Unregistered ManagedDeviceService");
		serviceRegistration.unregister();
	}
}
