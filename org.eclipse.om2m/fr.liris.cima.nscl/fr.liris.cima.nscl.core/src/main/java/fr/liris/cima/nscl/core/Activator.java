package fr.liris.cima.nscl.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.ipu.service.IpuService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import fr.liris.cima.nscl.device.service.ManagedDeviceService;

/**
 *  Manages the starting and stopping of the bundle.
 *  @author madiallo
 */
public class Activator implements BundleActivator {
	/** Logger */
	private static Log logger = LogFactory.getLog(Activator.class);
	/** Managed device service tracker */	
	private ServiceTracker<Object, Object> mgmtDeviceServiceTracker;
	/** Rest service  client tracker */
	private ServiceTracker<Object, Object> restServiceClientTracker;

	@Override
	public void start(BundleContext bundleContext) throws Exception {

		logger.info("get  ManagedDeviceService .");

		mgmtDeviceServiceTracker = new ServiceTracker<Object, Object>(bundleContext, ManagedDeviceService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("ManagedDevice removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("ManagedDevice discovered in cima gscl core");
				final ManagedDeviceService managedDeviceService = (ManagedDeviceService) this.context.getService(reference);
				this.context.registerService(IpuService.class.getName(), new InfrastructureController(managedDeviceService), null);

				new Thread(){
					public void run(){
						try {
							managedDeviceService.start();
						} catch (Exception e) {
							logger.error("IpuMonitor Sample error", e);
						}
					}
				}.start();
				return managedDeviceService;
			}
		};
		mgmtDeviceServiceTracker.open();

		restServiceClientTracker = new ServiceTracker<Object, Object>(bundleContext, RestClientService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("RestClientService removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("RestClientService discovered in cima nscl core");
				final RestClientService restClientService = (RestClientService) this.context.getService(reference);
				if(reference.getProperty("fr.liris.cima.comm.plateform") !=null) {
					InfrastructureController.restClientService = restClientService;
				}
				return restClientService;
			}
		};
		restServiceClientTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger.error("Stop IPU Phidgets monitor");
		try {
		} catch (Exception e) {
			logger.error("Stop Phidgets error", e);
		}
	}
}
