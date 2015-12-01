package fr.liris.cima.gscl.portforwarding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by dasilvafrederic on 22/10/15.
 */
public class PortForwardManager implements PortForwardingInterface {


    private Map<String, Integer> PFmanager = new HashMap<String, Integer>();



    public void askNewPortForwarding(String address, int port, String deviceID){
        String message = "{\"type\":\"test\", \"address\" : \""+address+"\", \"port\" : \""+port+"\" , \"id\" : \""+deviceID+"\" }";

    	System.out.println("ASK for port forwarding : " + message);
    	
            //TcpManagerSender.sendMessage(message, this);
            PortForwardingProcessLauncher portForwardingProcessLauncher = new PortForwardingProcessLauncher(this, 1223, "devide", "dressss", PortForwardingProcessLauncher.PROTOCOL_TCP);
            portForwardingProcessLauncher.startPortForwarding();
    }

    public int getPortForwarding(String deviceId){
        return PFmanager.get(deviceId);
    }


    public  void addPortForwarding(String m){

        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRv " + m);
        try {
            String type = getTypeFromJSon(m),
                    id = getIdFromJSon(m);
            int port = getPortFromJSon(m);

            System.out.println("Message recu : " + type + " " + id + " " + port);
            //TODO success or fail ???
            this.PFmanager.put(id, port);
            //TODO : enlever
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
        Set<String> keys = PFmanager.keySet();
        Iterator<String> iter = keys.iterator();
        System.out.println("PORT FORWARDING TABLE : ");
        System.out.println("================================");
        while(iter.hasNext()){
           String key = iter.next();
           System.out.println("\t"+key+"|\t"+PFmanager.get(key));
        }
        System.out.println("================================");
    }

    private int getPortFromJSon(String m){
        return Integer.parseInt(m.split(",")[1].split(":")[1].replace("\"", "").replace(" ", ""));
    }


    private String getIdFromJSon(String m){
        return m.split(",")[2].split(":")[1].replace("\"", "").replace(" ", "").replace("}", "");
    }


}
