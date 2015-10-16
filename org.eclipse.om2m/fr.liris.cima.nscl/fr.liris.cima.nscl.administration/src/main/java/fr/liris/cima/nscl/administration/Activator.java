package fr.liris.cima.nscl.administration;

import java.util.Dictionary;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.core.service.SclService;
import org.eclipse.om2m.ipu.service.IpuService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import fr.liris.cima.comm.protocol.AbstractProtocol;
import fr.liris.cima.comm.protocol.ProtocolResolver;
import fr.liris.cima.nscl.commons.parser.Parser;
import fr.liris.cima.nscl.device.service.ManagedDeviceService;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.*;
/**
 *  Manages the starting and stopping of the bundle.
 *  @author Rémi Desmargez
 */
public class Activator implements BundleActivator {
	/** Logger */
	private static Logger logger = Logger.getLogger(Activator.class.getName());
	private  static  Handler fh ;
	/** Rest service  client tracker */
	private ServiceTracker<Object, Object> restServiceClientTracker;
	private ServiceTracker<Object, Object> protocolResolverServiceClientTracker;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		try{
		fh = new FileHandler("log/nsclAdministration.log", false);
		logger.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


		logger.info("register Service CIMA Administration .");

		bundleContext.registerService(IpuService.class.getName(), new AdministrationServer(), null);
		logger.info("open  ManagedDeviceService .");

		logger.info("get  restServiceClient .");
		restServiceClientTracker = new ServiceTracker<Object, Object>(bundleContext, RestClientService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("RestClientService removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("RestClientService discovered in cima nscl administration server");
				final RestClientService restClientService = (RestClientService) this.context.getService(reference);
				AdministrationServer.restClientService = restClientService;
				return restClientService;
			}
		};
		logger.info("open restServiceClient .");
		restServiceClientTracker.open();

		protocolResolverServiceClientTracker=  new ServiceTracker<Object, Object>(bundleContext, ProtocolResolver.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("ProtocolResolver removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("ProtocolResolver  in cima gscl commons");
				final ProtocolResolver protocolResolver = (ProtocolResolver) this.context.getService(reference);
				AdministrationServer.protocolResolver = protocolResolver;
				return protocolResolver;
			}
		};

		logger.info("open restServiceClient .");
		protocolResolverServiceClientTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger.severe("Stop IPU Phidgets monitor");
		try {
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Stop Phidgets error", e);

		}
	}
}
