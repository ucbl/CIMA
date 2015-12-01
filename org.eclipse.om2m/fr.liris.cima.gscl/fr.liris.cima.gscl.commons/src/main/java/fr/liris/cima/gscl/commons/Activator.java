package fr.liris.cima.gscl.commons;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import fr.liris.cima.comm.protocol.AbstractProtocol;
import fr.liris.cima.comm.protocol.ProtocolResolver;
import fr.liris.cima.gscl.commons.parser.Parser;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.log.*;
import org.osgi.framework.FrameworkUtil;

public class Activator implements BundleActivator {

	private static Log logger = LogFactory.getLog(Activator.class);

	/** Logger OSGI*/
	private static ServiceTracker logServiceTracker;
	private static LogService logservice;

	private ServiceTracker<Object, Object> protocolResolverServiceClientTracker;

	@Override
	public void start(BundleContext bundleContext) throws Exception {

		logServiceTracker = new ServiceTracker(bundleContext, org.osgi.service.log.LogService.class.getName(), null);
		logServiceTracker.open();
		logservice = (LogService) logServiceTracker.getService();



		logger.info("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
		logservice.log(LogService.LOG_ERROR, "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");


		protocolResolverServiceClientTracker=  new ServiceTracker<Object, Object>(bundleContext, ProtocolResolver.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("ProtocolResolver removed");
				logservice.log(LogService.LOG_ERROR, "ProtocolResolver removed");

			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("ProtocolResolver  in cima gscl commons");
				logservice.log(LogService.LOG_ERROR, "ProtocolResolver  in cima gscl commons");

				final ProtocolResolver protocolResolver = (ProtocolResolver) this.context.getService(reference);
				Parser.protocolResolver = protocolResolver;
				Map<String, Class<? extends AbstractProtocol>> protocols = Parser.protocolResolver.getAllProtocol();
				String sout = "---------------------------\n"
						+ "list of aviables protocols :\n";
				for(String proto : protocols.keySet()){
					sout += proto + "\n";
				}
				sout += "---------------------------\n";
				logger.info(sout);
				logservice.log(LogService.LOG_ERROR, sout);

				return protocolResolver;
			}
		};
		protocolResolverServiceClientTracker.open();
		logger.info("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
		logservice.log(LogService.LOG_ERROR, "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");

	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
	}

}
