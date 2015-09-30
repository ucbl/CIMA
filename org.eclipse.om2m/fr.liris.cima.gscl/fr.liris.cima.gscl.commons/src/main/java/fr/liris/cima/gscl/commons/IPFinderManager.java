package fr.liris.cima.gscl.commons;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

/**
 *
 * IPFinderManager
 * Class that manage search of new IPs
 * It maintain a list of accesibles IPs
 *
 * Usage : call startIfNotStarted to start the search : it will automaticly seach every 5 sceconds
 * Call the accesiblesIP getter to have the list of accesibles IP at the time of calling.
 *
 * Created by Maxime on 30/09/15.
 */
public class IPFinderManager {

    //Use to not call many time the timer
    private static boolean timerIsRunning = false;

    //Arraylist of occessibles IP
    private static Set<String> accesiblesIP = new HashSet<>();

    //Timer to schedule searchs
    private static Timer timer;


    /**
     * Start search
     * start 0.5 seconds after calling, each 5s.
     */
    public static void startLooking(){

        timer = new Timer();
        timer.schedule(new IPFinder(), 500, 5000);
        timerIsRunning = true;
    }

    /**
     * Stop the search
     */
    public static void stopLooking(){
        timer.cancel();
        timerIsRunning = false;
    }

    /**
     * Start the search only if start was not call before.
     */
    public static void startIfNotStarted(){
        if(!timerIsRunning)
            startLooking();
    }

    /**
     * Called by IPFinder to add IP to the accesible List
     * @param ip : String like 192.168.1.1
     */
    public static void setAccessible(String ip){
        if(!accesiblesIP.contains(ip))
            accesiblesIP.add(ip);
    }

    /**
     * Called by IPFinder to remove IP of the accesible List
     * @param ip : String like 192.168.1.1
     */
    public static void setUnaccessible(String ip){
        accesiblesIP.remove(ip);
    }

    /**
     * To string, for this static object
     * @return : STring describing the accesible list
     */
    public static String toStaticString(){
        return "IPFINDERMANAGER : accessibles : " + accesiblesIP.toString();
    }


    /**
     * Get all the accesiblesIP found at this time.
     * @return : Set Of Sting like 192.168.1.1
     */
    public static Set<String> getAccesiblesIP() {
        return new HashSet<String>(accesiblesIP);
    }
}
