package fr.liris.cima.gscl.commons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import fr.liris.cima.comm.protocol.ProtocolResolver;
import fr.liris.cima.gscl.commons.parser.Parser;

public class Activator implements BundleActivator {

	private static Log logger = LogFactory.getLog(Activator.class);

	private ServiceTracker<Object, Object> protocolResolverServiceClientTracker;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		protocolResolverServiceClientTracker=  new ServiceTracker<Object, Object>(bundleContext, ProtocolResolver.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("ProtocolResolver removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("ProtocolResolver  in cima gscl commons");
				final ProtocolResolver protocolResolver = (ProtocolResolver) this.context.getService(reference);
				Parser.protocolResolver = protocolResolver;
				return protocolResolver;
			}
		};
		protocolResolverServiceClientTracker.open();
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
	}

}
