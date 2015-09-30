package fr.liris.cima.gscl.commons;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The IPFinder TimerTask class look for all the available IP that can be found.
 * It iterate in all network interfaces, get the local ip on the network and iterate all relevant ip to know if there is somebody behind
 * It use many threads to let timeout on each irrelevant ips.
 *
 * Created by Maxime on 30/09/15.
 */
public class IPFinder extends TimerTask {

    //The thread pool
    ExecutorService threadPool = Executors.newFixedThreadPool(30);

    @Override
    public void run() {
        Enumeration<NetworkInterface> lne = null;
        try {
            lne = NetworkInterface.getNetworkInterfaces();

            //Iterate all the networks interfaces (wlan0 en0 wlan1 eth1 ...)
            while(lne.hasMoreElements()) {

                NetworkInterface networkInterface = lne.nextElement();

                Enumeration<InetAddress> ia = networkInterface.getInetAddresses();

                //Iterate all locals IP in this network interface
                while(ia.hasMoreElements()) {
                    InetAddress inetAddress = ia.nextElement();

                    //Only if this is a localSite IP, we can look for other ips
                    if(inetAddress.isSiteLocalAddress())
                    {
                        String myAdress = inetAddress.getHostAddress();
                        String myAdreesSplited[] = myAdress.split("\\.");
                        String adressStart = myAdreesSplited[0] +"."+ myAdreesSplited[1]+"." + myAdreesSplited[2]+".";

                        //We try all ips started by myIp and ended by [0,255] (yes, myIP too)
                        for(int i = 0; i!= 256; i++)
                        {

                            String adder = adressStart + i;
                            threadPool.submit(new IPChecker(this, adder));
                        }
                    }
                }

            }
        } catch (SocketException e) {
            //TODO
            e.printStackTrace();
        }

    }

    /**
     * Find Method
     * Called by IPChecker threads
     * Check if an IP exist or not
     * If exist, call the IPFinderManager method to add it in the accessible liste (else ..)
     * @param addr : String like 192.168.1.1 (ip)
     * @throws IOException : caused by InetAdress (staurated network ...) and isReachable (?)
     */
    public void find(String addr) throws IOException {

            if (InetAddress.getByName(addr).isReachable(500))
                IPFinderManager.setAccessible(addr);
            else
                IPFinderManager.setUnaccessible(addr);
    }


}
