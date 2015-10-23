package fr.liris.cima.gscl.portforwarding;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.service.SclService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import java.util.Dictionary;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Created by DA SILVA Frédéric on 22/10/2015.
 */
public class Activator implements BundleActivator {

    /** SCL service tracker */
    private ServiceTracker<Object, Object> sclServiceTracker;

    private ServiceRegistration serviceRegistration;


    public void start(BundleContext bundlecontext) throws Exception{
        //PortForwardManager p = new PortForwardManager();



        new Thread(){
                    public void run(){
                        new PortForwardManager();
                    }
        }.start();



//        sclServiceTracker = new ServiceTracker<Object, Object>(bundlecontext,PortForwardingInterface.class.getName() , null) {
//            public void removedService(ServiceReference<Object> reference, Object service) {
//                System.out.println("PF removed");
//            }
//
//            public Object addingService(ServiceReference<Object> reference) {
//                final ManagedDeviceService managedDeviceService = (ManagedDeviceService) this.context.getService(reference);
//
//                final PortForwardingInterface pfm = (PortForwardManager) this.context.getService(reference);
//                final CapabilityManager managedCapabilityManager = (CapabilityManager) this.context.getService(this.context.getServiceReference(CapabilityManager.class.getName()));
//                this.context.registerService(IpuService.class.getName(), new DeviceController(managedDeviceService,managedCapabilityManager), null);
//
//                new Thread(){
//                    public void run(){
//                        try {
//                            managedDeviceService.start();
//                        } catch (Exception e) {
//                            logger.log(Level.SEVERE,"ManagedDeviceService error", e);
//
//
//                        }
//                    }
//                }.start();
//                return managedDeviceService;
//            }
//        };
//        sclServiceTracker.open();








       // System.out.println("coucou");
        //Dictionary props=new Properties();
        //props.put("fr.liris.cima.gscl.portforwarding", "cima");
       // this.serviceRegistration = bundlecontext.registerService(PortForwardingInterface.class.getName(), new PortForwardManager(), null);
       // System.out.println("SERVICE OUVERT");
//
//        this.sclServiceTracker = new ServiceTracker<Object, Object>(bundlecontext, SclService.class.getName(), null) {
//            public void removedService(ServiceReference<Object> reference, Object service) {
//                System.out.println("service fermé.");
//            }
//
//            public Object addingService(ServiceReference<Object> reference) {
//
//                SclService sclService = (SclService) this.context.getService(reference);
//
//                serviceRegistration = this.context.registerService(PortForwardManager.class.getName(), new PortForwardManager() , null);
//                System.out.println("SERVICE OUVERT");
//
//                return sclService;
//            }
//        };
//        sclServiceTracker.open();
    }


    public void stop(BundleContext context) throws Exception{
        serviceRegistration.unregister();
    }
}
