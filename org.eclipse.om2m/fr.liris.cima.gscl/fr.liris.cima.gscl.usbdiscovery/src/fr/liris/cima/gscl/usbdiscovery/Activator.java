package fr.liris.cima.gscl.usbdiscovery;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Created by Maxime on 22/10/2015.
 */
public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception{
        System.out.println("COUCOU C'EST MOI LE NOUVEAU BUNDLE");
    }


    public void stop(BundleContext context) throws Exception{
        System.out.println("Je MEURS");
    }
}
