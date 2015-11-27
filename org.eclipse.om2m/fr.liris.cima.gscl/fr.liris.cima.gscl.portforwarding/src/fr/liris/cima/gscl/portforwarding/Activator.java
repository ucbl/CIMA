package fr.liris.cima.gscl.portforwarding;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Created by DA SILVA Frédéric on 22/10/2015.
 */
public class Activator implements BundleActivator {

    /** SCL service tracker */
    private ServiceTracker<Object, Object> sclServiceTracker;

    private ServiceRegistration serviceRegistration;


    public void start(BundleContext bundlecontext) throws Exception{


        PortForwardManagerThread pf = new PortForwardManagerThread();


        this.serviceRegistration = bundlecontext.registerService(PortForwardingInterface.class.getName(),pf,null);

        pf.run();



        pf.askNewPortForwarding("dess", 66, "uuid");

    }


    public void stop(BundleContext context) throws Exception{
        serviceRegistration.unregister();
    }
}
