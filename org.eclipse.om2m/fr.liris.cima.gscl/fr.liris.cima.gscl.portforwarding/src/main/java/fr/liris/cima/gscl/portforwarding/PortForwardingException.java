package fr.liris.cima.gscl.portforwarding;

/**
 * Created by Maxime on 30/11/2015.
 */
public class PortForwardingException extends Exception {

    public PortForwardingException(){
        super("Error during new port forwarding creation.");
    }
}
