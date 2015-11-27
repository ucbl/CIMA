package fr.liris.cima.gscl.portforwarding;

/**
 * Created by Maxime on 24/11/2015.
 */
public class PortForwardManagerThread implements  PortForwardingInterface, Runnable {


    PortForwardingInterface portForwarding;

    @Override
    public void askNewPortForwarding(String address, int port, String deviceID) {
        portForwarding.askNewPortForwarding(address,port,deviceID);
    }

    @Override
    public int getPortForwarding(String deviceId) {
        return portForwarding.getPortForwarding(deviceId);
    }

    @Override
    public void addPortForwarding(String m) {
        portForwarding.addPortForwarding(m);
    }

    @Override
    public void run() {
        portForwarding = new PortForwardManager();
    }
}
