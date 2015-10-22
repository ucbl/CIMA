package fr.liris.cima.gscl.portforwarding;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Created by dasilvafrederic on 22/10/15.
 */
public class PortForwardManager {


    private Map<String, Integer> PFmanager = new HashMap<String, Integer>();
    private TcpManager TCPmanager;

    public PortForwardManager(TcpManager manageTCP){
            TCPmanager = manageTCP;
            TCPmanager.run();
            Timer timer = new Timer();
            timer.schedule(new LookUpTcpMessage(TCPmanager,this), 5000, 2000);
    }

    public void askNewPortForwarding(String address, int port, String deviceID){

        String message = "{type:'open',address:'"+address+"',port:'"+port+"',ID:'"+deviceID+"' }";
        TCPmanager.sendMessage(message);
    }

    public int getPortForwarding(){

        return 0;
    }
    public  void addPortForwarding(String m){

    }

}
