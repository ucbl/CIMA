package fr.liris.cima.gscl.portforwarding;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Created by DA SILVA Frédéric on 22/10/2015.
 */
public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception{
        TcpManager t = new TcpManager();
        PortForwardManager p = new PortForwardManager(t);

        System.out.println("Bonjour je suis sur le port forwarding");
    }


    public void stop(BundleContext context) throws Exception{
        System.out.println("Je MEURS");
    }
}
