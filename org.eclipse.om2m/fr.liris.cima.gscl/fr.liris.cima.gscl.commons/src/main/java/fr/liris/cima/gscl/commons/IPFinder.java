package fr.liris.cima.gscl.commons;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.InterfaceAddress;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import java.util.Enumeration;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;

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
            while (lne.hasMoreElements()) {

                NetworkInterface networkInterface = lne.nextElement();

                Enumeration<InetAddress> ia = networkInterface.getInetAddresses();
                List<InterfaceAddress> inter = networkInterface.getInterfaceAddresses();

                int cnt = 0;
                //Iterate all locals IP in this network interface
                while (ia.hasMoreElements()) {
                    InetAddress inetAddress = ia.nextElement();
                    InterfaceAddress address = inter.get(cnt++);
                    String myAddress = address.getAddress().getHostAddress();

                    //IPV4 verification and if it's not the localhost IP (127.0.0.1)
                    if (InetAddressValidator.getInstance().isValidInet4Address(myAddress) && !myAddress.equals("127.0.0.1")) {
                        SubnetUtils networkInfo = new SubnetUtils(myAddress + "/" + address.getNetworkPrefixLength());

                        //Only if this is a localSite IP, we can look for other ips
                        if (inetAddress.isSiteLocalAddress()) {
                            
                            String currentAddress = networkInfo.getInfo().getLowAddress();
                            String adressSplited[] = currentAddress.split("\\.");
                            String broadcastAddress = networkInfo.getInfo().getBroadcastAddress();

                            //We try all IP addresses in the same network as myAddress
                            while (!currentAddress.equals(broadcastAddress)) {

                                if (!currentAddress.equals(myAddress)) {
                                    threadPool.submit(new IPChecker(this, currentAddress));
                                }

                                int b4 = Integer.parseInt(adressSplited[3]) + 1;
                                int b3 = Integer.parseInt(adressSplited[2]);
                                int b2 = Integer.parseInt(adressSplited[1]);
                                int b1 = Integer.parseInt(adressSplited[0]);

                                if (b4 == 256) {
                                    b4 = 0;
                                    b3++;
                                    if (b3 == 256) {
                                        b3 = 0;
                                        b2++;
                                        if (b2 == 256) {
                                            b2 = 0;
                                            b1++;
                                        }
                                    }
                                }
                                currentAddress = "" + b1 + "." + b2 + "." + b3 + "." + b4;
                                adressSplited = currentAddress.split("\\.");
                            }
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
