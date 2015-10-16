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

import fr.liris.cima.comm.protocol.Protocol;
import fr.liris.cima.comm.protocol.ProtocolResolver;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.*;
/**
 *  Manages the starting and stopping of the bundle.
 *  @author hady
 */
public class Activator implements BundleActivator {
	/** Logger */
	private static Logger LOGGER = Logger.getLogger(Activator.class.getName());
	private  static  Handler fh ;

	/** SCL service tracker */
	private ServiceTracker<Object, Object> sclServiceTracker;

	private ServiceRegistration serviceRegistration;
	private ProtocolResolver protocolResolver;
	private Protocol pHttp;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		try{
		fh = new FileHandler("log/http.log", false);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}



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
		LOGGER.info("registered RestClientService cima Http");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		//LOGGER.log(Level.SEVERE,"Unregistered RestClientService cima Http", null);
		LOGGER.severe("Unregistered RestClientService cima Http");


		serviceRegistration.unregister();
		this.protocolResolver.removeProtocol("http");
	}
}
