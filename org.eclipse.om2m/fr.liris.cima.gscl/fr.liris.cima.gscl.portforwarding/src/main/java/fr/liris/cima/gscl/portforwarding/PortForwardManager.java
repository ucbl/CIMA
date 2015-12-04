package fr.liris.cima.gscl.portforwarding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
//import com.mongodb.MongoClient;
//import com.mongodb.client.MongoDatabase;
/**
 * Created by dasilvafrederic on 22/10/15.
 */
public class PortForwardManager implements PortForwardingInterface {


    private Map<String, Integer> PFmanager = new HashMap<String, Integer>();



public void askNewPortForwarding(String address, int port, String deviceID){
    String message = "{\"type\":\"test\", \"address\" : \""+address+"\", \"port\" : \""+port+"\" , \"id\" : \""+deviceID+"\" }";

   System.out.println("ASK for port forwarding : " + message);
        //TcpManagerSender.sendMessage(message, this);
        PortForwardingProcessLauncher portForwardingProcessLauncher = new PortForwardingProcessLauncher(this, port, deviceID, address, PortForwardingProcessLauncher.PROTOCOL_TCP);
        portForwardingProcessLauncher.start();
}



    public int getPortForwarding(String deviceId){
        return PFmanager.get(deviceId);
    }


    public  void addPortForwarding(String m, String deviceId){

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++++TEST DU MONGO TRUC++++++++++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//        MongoClient mongoClient = new MongoClient();
//        MongoDatabase db = mongoClient.getDatabase("test");


        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++++FIN  DU MONGO TRUC++++++++++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");


        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRv " + m);
        try {
            int port = getPortFromJSon(m);

            System.out.println("Message port recu :" + port);
            //TODO success or fail ???
            this.PFmanager.put(deviceId, port);
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
        return Integer.parseInt(m.split("=")[1].replace("\"", "").replace(" ", "").replace("}", ""));
    }


    private String getIdFromJSon(String m){
        return m.split(",")[2].split(":")[1].replace("\"", "").replace(" ", "").replace("}", "");
    }


}
