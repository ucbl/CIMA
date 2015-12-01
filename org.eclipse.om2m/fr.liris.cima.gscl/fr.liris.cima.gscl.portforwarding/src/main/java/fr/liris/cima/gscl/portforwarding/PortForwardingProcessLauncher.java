package fr.liris.cima.gscl.portforwarding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Maxime on 30/11/2015.
 */
public class PortForwardingProcessLauncher
{

    public static final int PROTOCOL_TCP = 0;
    public static final int PROTOCOL_UDP = 1;


    private int userPort, objectPortInPF, objectPort;
    private String deviceId, address;
    private int protocol;
    PortForwardManager portForwardManager;

    private static Logger logger = Logger.getLogger(PortForwardingProcessLauncher.class.getName());


    public PortForwardingProcessLauncher(PortForwardManager pf , int objectPort, String deviceId, String address, int protocol) {
        this.objectPort = objectPort;
        this.deviceId = deviceId;
        this.address = address;
        this.protocol = protocol;
        this.portForwardManager = pf;
    }


    public  Map.Entry<String, Integer> startPortForwarding(){


        String line;
        Process process = null;

        //Start the program
        try {process = Runtime.getRuntime().exec("./test");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not execute program to create a new port forwarding.");
        }

        //try to listen the procces out
        Reader inStreamReader = new InputStreamReader(process.getInputStream());
        BufferedReader in = new BufferedReader(inStreamReader);

        logger.log(Level.INFO, "Strating listenig process out on " + this.getDescription());
        try {
            while((line = in.readLine()) != null) {
                this.analyseAndDoProcessOutLine(line);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO, "Ending listening precess out on " + this.getDescription());

        return null;
    }

    private String getDescription(){
        return "Device : " + deviceId + " protocol (0 TCP, 1 UDP) : " + this.protocol + " port :" + this.objectPort;
    }



    private void analyseAndDoProcessOutLine(String line){
        if('[' == line.charAt(0)){ //just a log
            logger.log(Level.INFO, "FROM PORT FORWARDING PROCESS : " + line);
        }
        else
            portForwardManager.addPortForwarding(line);

    }
}
