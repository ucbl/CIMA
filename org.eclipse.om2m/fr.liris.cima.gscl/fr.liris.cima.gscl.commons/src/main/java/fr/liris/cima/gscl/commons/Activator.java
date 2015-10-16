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

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.*;

public class Activator implements BundleActivator {

	private static Logger logger = Logger.getLogger(Activator.class.getName());
	private  static  Handler fh ;

	private ServiceTracker<Object, Object> protocolResolverServiceClientTracker;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		try{
		fh = new FileHandler("log/gsclCommons.log", false);
		logger.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


		logger.info("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");


		protocolResolverServiceClientTracker=  new ServiceTracker<Object, Object>(bundleContext, ProtocolResolver.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("ProtocolResolver removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("ProtocolResolver  in cima gscl commons");
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
				return protocolResolver;
			}
		};
		protocolResolverServiceClientTracker.open();
		logger.info("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
	}

}
