package fr.liris.cima.gscl.portforwarding;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dasilvafrederic on 22/10/15.
 */
public class PortForwardManager {


    private Map<String, Integer> PFmanager = new HashMap<String, Integer>();
    private TcpManagerListener manager;

    public PortForwardManager(){
            manager = new TcpManagerListener(this);
            manager.run();
    }

    public void askNewPortForwarding(String address, int port, String deviceID){

        String message = "{type:'open',address:'"+address+"',port:'"+port+"',ID:'"+deviceID+"' }";
        try {
            TcpManagerSender.sendMessage(message);
            TcpManagerListener m = new TcpManagerListener(this);
            m.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPortForwarding(String deviceId){
        return PFmanager.get(deviceId);
    }


    public  void addPortForwarding(String m){
        System.out.println("==> ==> ==>dans le port forward manager : " + m);

        try {
            String type = getTypeFromJSon(m),
                    id = getIdFromJSon(m);
            int port = getPortFromJSon(m);

            System.out.println("Message recu : " + type + " " + id + " " + port);
        }
        catch(Exception e){
            System.out.println("ERROR LORS DU PARSING : " + e);
        }

    }


    //{"type":"succes" , "port" : "16324","id":"idnumero" }

    private String getTypeFromJSon(String m){
        return m.split(",")[0].split(":")[1].replace("\"", "");
    }


    private int getPortFromJSon(String m){
        return Integer.parseInt(m.split(",")[1].split(":")[1].replace("\"", ""));
    }


    private String getIdFromJSon(String m){
        return m.split(",")[2].split(":")[1].replace("\"", "");
    }


}
