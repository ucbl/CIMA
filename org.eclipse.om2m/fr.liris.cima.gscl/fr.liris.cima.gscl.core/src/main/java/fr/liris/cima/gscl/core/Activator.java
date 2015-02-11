package fr.liris.cima.gscl.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.ipu.service.IpuService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import fr.liris.cima.gscl.device.service.ConfigManager;
import fr.liris.cima.gscl.device.service.ManagedDeviceService;
import fr.liris.cima.gscl.device.service.capability.CapabilityManager;
import fr.liris.cima.gscl.device.service.discovery.DiscoveryService;

/**
 *  Manages the starting and stopping of the bundle.
 *  @author madiallo
 */
public class Activator implements BundleActivator {
	/** Logger */
	private static Log logger = LogFactory.getLog(Activator.class);
	/** Management device service tracker */	
	private ServiceTracker<Object, Object> mgmtDeviceServiceTracker;
	private ServiceTracker<Object, Object> restServiceClientTracker;
	private ServiceTracker<Object, Object> discoverytServiceTracker;
	
	private ServiceTracker<Object, Object> configManualServiceTracker;


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
				final CapabilityManager managedCapabilityManager = (CapabilityManager) this.context.getService(this.context.getServiceReference(CapabilityManager.class.getName()));		
				this.context.registerService(IpuService.class.getName(), new DeviceController(managedDeviceService,managedCapabilityManager), null);

				new Thread(){
					public void run(){
						try {
							managedDeviceService.start();
						} catch (Exception e) {
							logger.error("ManagedDeviceService error", e);
						}
					}
				}.start();
				return managedDeviceService;
			}
		};
		mgmtDeviceServiceTracker.open();
		
//		configManualServiceTracker = new ServiceTracker<Object, Object>(bundleContext, ManagedDeviceService.class.getName(), null) {
//			public void removedService(ServiceReference<Object> reference, Object service) {
//				logger.info("ConfigManager removed");
//			}
//
//			public Object addingService(ServiceReference<Object> reference) {
//				logger.info("ConfigManager discovered in cima gscl core");
//				final ConfigManager configManager = (ConfigManager)this.context.getService(reference);
//				this.context.registerService(IpuService.class.getName(), new ConfigController(configManager), null);
//
//				configManager.start();
////				new Thread(){
////					public void run(){
////						try {
////							configManager.start();
////						} catch (Exception e) {
////							logger.error("ConfigManager error", e);
////						}
////					}
////				}.start();
//				return configManager;
//			}
//		};
//		configManualServiceTracker.open();


		restServiceClientTracker = new ServiceTracker<Object, Object>(bundleContext, RestClientService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("RestClientService removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("RestClientService discovered in cima gscl core");
				final RestClientService restClientService = (RestClientService) this.context.getService(reference);
				DeviceController.restClientService = restClientService;
			//	ConfigController.restClientService = restClientService;
				return restClientService;
			}
		};
		restServiceClientTracker.open();
		
		discoverytServiceTracker = new ServiceTracker<Object, Object>(bundleContext, DiscoveryService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("RestClientService removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info(" discovered DiscoveryService in cima gscl core");
				final DiscoveryService discoveryService = (DiscoveryService) this.context.getService(reference);
				new Thread(){
					public void run(){
//						while(true) {
							try {
								Thread.sleep(1000) ;
								discoveryService.doDiscovery();
								logger.info("do discovery ...");
							} catch (Exception e) {
								logger.error("Error for discovering devices", e);
							}
//						}
					}
				}.start();
				return discoveryService;
			}
		};
		discoverytServiceTracker.open();
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