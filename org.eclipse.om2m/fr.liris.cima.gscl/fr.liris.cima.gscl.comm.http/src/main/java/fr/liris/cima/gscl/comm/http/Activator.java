package fr.liris.cima.gscl.comm.http;

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

import fr.liris.cima.gscl.device.service.ManagedDeviceService;
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
	private static Logger logger = Logger.getLogger(Activator.class.getName());
	private  static  Handler fh ;
	/** SCL service tracker */
	private ServiceTracker<Object, Object> sclServiceTracker;

	private ServiceRegistration serviceRegistration;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		try{
		fh = new FileHandler("log/gsclHttp.log", false);
		logger.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


		Dictionary props=new Properties();
		props.put("fr.liris.cima.gscl.comm.plateform", "cima");
        this.serviceRegistration = bundleContext.registerService(RestClientService.class.getName(), new CIMARestHttpClient(), props);
		logger.info("registered RestClientService Http");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger.severe("Unregistered RestClientService Http");
		serviceRegistration.unregister();
	}
}
