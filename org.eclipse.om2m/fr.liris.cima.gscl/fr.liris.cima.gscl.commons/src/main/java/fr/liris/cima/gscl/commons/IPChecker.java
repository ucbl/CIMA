package fr.liris.cima.gscl.commons;

import java.io.IOException;

/**
 * Just a class call in different threads to call the "find" method in IPFinder
 *
 * Created by Maxime on 30/09/15.
 */
public class IPChecker implements Runnable {

    // The ip that we wan ti know if there is somebody behind
    String IPToCheck;

    //ref to the finder, allow to call his "find" method
    IPFinder finder;

    IPChecker(IPFinder finder, String ipToCheck){
        this.finder = finder;
        this.IPToCheck = ipToCheck;
    }

    @Override
    public void run() {
        try {
            finder.find(IPToCheck);
        } catch (IOException e) {
            //TODO : should we log something ? There are some error due to saturated network
        }
    }
}
