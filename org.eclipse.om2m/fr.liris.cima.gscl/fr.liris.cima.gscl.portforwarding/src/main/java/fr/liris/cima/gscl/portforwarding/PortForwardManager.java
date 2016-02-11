package fr.liris.cima.gscl.portforwarding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
//import com.mongodb.MongoClient;
//import com.mongodb.client.MongoDatabase;

/**
 * implements PortForwardingInterface.java, and store allocated port and deviceId into a Map<String, Integer>.
 * Created by dasilvafrederic on 22/10/15.
 */
public class PortForwardManager implements PortForwardingInterface {


    private Map<String, Integer> PFmanagerTCP = new HashMap<String, Integer>();
    private Map<String, Integer> PFmanagerUDP = new HashMap<String, Integer>();
    private Map<Integer, Integer> pidAndPort = new HashMap<>();



    public void askNewPortForwarding(String address, int port, String deviceID, String tcpOrUdp){
        System.out.println("INFO: Ask fo a new port forwarding : " + address + " " + port + " " + deviceID + " " +tcpOrUdp);
        PortForwardingProcessLauncher portForwardingProcessLauncher;
        if("UDP".equals(tcpOrUdp))
            portForwardingProcessLauncher  = new PortForwardingProcessLauncher(this, port, deviceID, address, PortForwardingProcessLauncher.PROTOCOL_TCP);
        else
            portForwardingProcessLauncher = new PortForwardingProcessLauncher(this, port, deviceID, address, PortForwardingProcessLauncher.PROTOCOL_UDP);
        portForwardingProcessLauncher.start();
    }



    public int getPortForwarding(String deviceId, String tcpOrUdp){

        if(tcpOrUdp.equals("UDP"))
            return PFmanagerUDP.get(deviceId);
        else
            return PFmanagerTCP.get(deviceId);
    }



    public  void addPortForwarding(String m, String deviceId, int protocol){

        try {
            int port = getPortFromJSon(m);
            int pid = getPIDFromJson(m);

            System.out.println("Message port recu :" + port);
            //TODO success or fail ???
            if(protocol == PortForwardingProcessLauncher.PROTOCOL_TCP)
                this.PFmanagerTCP.put(deviceId, port);
            else if(protocol == PortForwardingProcessLauncher.PROTOCOL_UDP)
                this.PFmanagerUDP.put(deviceId, port);

            this.pidAndPort.put(pid, port);
            //TODO : remove when jsonld will show ports
            this.printPF();
        }
        catch(Exception e){
            System.out.println("ERROR LORS DU PARSING : " + e + " "+ m);
        }

    }


    //{"type":"succes" , "port" : "16324","id":"idnumero" }
    //TODO: better parsing
    private String getTypeFromJSon(String m){
        return m.split(",")[0].split(":")[1].replace("\"", "").replace(" ", "");
    }

    private void printPF(){
        Set<String> keys = PFmanagerTCP.keySet();
        Iterator<String> iter = keys.iterator();
        System.out.println("PORT FORWARDING TABLE : TCP ");
        System.out.println("================================");
        while(iter.hasNext()){
            String key = iter.next();
            System.out.println("\t"+key+"|\t"+PFmanagerTCP.get(key));
        }
        System.out.println("================================");


        keys = PFmanagerUDP.keySet();
        iter = keys.iterator();
        System.out.println("PORT FORWARDING TABLE : UDP ");
        System.out.println("================================");
        while(iter.hasNext()){
            String key = iter.next();
            System.out.println("\t"+key+"|\t"+PFmanagerUDP.get(key));
        }
        System.out.println("================================");
    }

    //{"port" : "10002" , "pid" : "11338"}


    private int getPortFromJSon(String m){
        return Integer.parseInt(m.split(":")[1].split(",")[0].replace("\"", "").replace(" ", "").replace("}", ""));
    }


    private int getPIDFromJson(String m){
        return Integer.parseInt(m.split(",")[1].split(":")[1].replace("\"", "").replace(" ", "").replace("}", ""));
    }


}
