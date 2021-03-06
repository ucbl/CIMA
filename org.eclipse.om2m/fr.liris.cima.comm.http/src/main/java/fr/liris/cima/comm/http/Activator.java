package fr.liris.cima.comm.http;

import java.util.Dictionary;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.core.service.SclService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.log.*;
import org.osgi.framework.FrameworkUtil;
import fr.liris.cima.comm.protocol.Protocol;

import fr.liris.cima.comm.protocol.ProtocolResolver;

/**
 *  Activator class for starting and stopping fr.liris.cima.comm.http bundle.
 *  @author hady
 */
public class Activator implements BundleActivator {
	/** Logger */
	private static Log logger = LogFactory.getLog(Activator.class);

	/** SCL service tracker */
	private ServiceTracker<Object, Object> sclServiceTracker;

	/** Logger OSGI*/
	private static ServiceTracker logServiceTracker;
	private static LogService logservice;

	private ServiceRegistration serviceRegistration;
	private ProtocolResolver protocolResolver;
	private Protocol pHttp;

	@Override
	public void start(BundleContext bundleContext) throws Exception {

		logServiceTracker = new ServiceTracker(bundleContext, org.osgi.service.log.LogService.class.getName(), null);
			logServiceTracker.open();
			logservice = (LogService) logServiceTracker.getService();


		Dictionary props=new Properties();
		props.put("fr.liris.cima.comm.plateform", "cima");
        this.serviceRegistration = bundleContext.registerService(RestClientService.class.getName(), new CIMARestHttpClient(), props);

     	// Now we add the HTTP protocol to the available protocols
    	ServiceReference refServ = bundleContext.getServiceReference(ProtocolResolver.class.getName());
    	if(refServ != null) {
    	    this.protocolResolver = (ProtocolResolver) bundleContext.getService(refServ);
    	    this.pHttp = new HTTP();
    	    this.protocolResolver.addProtocol("http", HTTP.class);
    	}
		logger.info("registered RestClientService cima Http");
		logservice.log(LogService.LOG_ERROR, "registered RestClientService cima Http");

	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger.error("Unregistered RestClientService cima Http");
			logservice.log(LogService.LOG_ERROR, "Unregistered RestClientService cima Http");


		serviceRegistration.unregister();
		this.protocolResolver.removeProtocol("http");
	}
}
