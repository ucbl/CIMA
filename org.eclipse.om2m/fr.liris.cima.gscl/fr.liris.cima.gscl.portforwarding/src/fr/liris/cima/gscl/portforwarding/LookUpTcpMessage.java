package fr.liris.cima.gscl.portforwarding;

import java.util.TimerTask;

/**
 * Created by dasilvafrederic on 22/10/15.
 */

/* Allow the TCPmanager to look */
public class LookUpTcpMessage extends TimerTask{

    private TcpManager TCPmanager;
    private PortForwardManager PFmanager;


    public LookUpTcpMessage(TcpManager manageTCP, PortForwardManager managePF){
        super();
        TCPmanager = manageTCP;
        PFmanager = managePF;


    }

    @Override
    public void run(){
        String message = TCPmanager.getResponse();
        if(message!=null){
            System.out.println("========================================================================");
            System.out.println(message);
            System.out.println("========================================================================");
        }

    }
}
