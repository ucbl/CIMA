package fr.liris.cima.gscl.dao;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

    /** SCL service tracker */
    private ServiceTracker<Object, Object> sclServiceTracker;

    private ServiceRegistration serviceRegistration;


    public void start(BundleContext bundlecontext) throws Exception{

        System.out.println("BON BON BON BON BON BON BON BON");

    }


    public void stop(BundleContext context) throws Exception{
        //serviceRegistration.unregister();
    }
}
