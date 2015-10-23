package fr.liris.cima.gscl.usbdiscovery;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * Created by Maxime on 22/10/2015.
 */
public class Activator implements BundleActivator {



    private ServiceRegistration serviceRegistration;

    public void start(BundleContext context) throws Exception{

        System.out.println("COUCOU DE l'Activator");

//        try {
//            Context usbcontext = new Context();
//            int result = LibUsb.init(usbcontext);
//            if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to initialize libusb.", result);
//
//
//            DeviceList list = new DeviceList();
//            int r = LibUsb.getDeviceList(null, list);
//            if (result < 0) throw new LibUsbException("Unable to get device list", r);
//
//
//            System.out.println("== :== :== :== :== :== :== :== :== :== :== :== :== :== :== :");
//            System.out.println("POPOL : " + list.toString());
//            System.out.println("== :== :== :== :== :== :== :== :== :== :== :== :== :== :== :");
//        }catch (Exception e){
//            System.out.println("coucou exception :" + e);
//        }

    }


    public void stop(BundleContext context) throws Exception{
        System.out.println("Je MEURS");
    }
}
