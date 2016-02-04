package fr.liris.cima.gscl.portforwarding;

/**
 * Interface to ask a new portForwarding, to get portForwarding for a device or to add a portForwarding to a device
 * Created by Maxime on 23/10/2015.
 */
public interface PortForwardingInterface {

    //maybe one day there will be comments here //TODO
    public void askNewPortForwarding(String address, int port, String deviceID);

    public int getPortForwarding(String deviceId);
    public  void addPortForwarding(String m, String deviceId);

}
