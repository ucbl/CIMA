package fr.liris.cima.gscl.portforwarding;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by dasilvafrederic on 22/10/15.
 */
public class TcpManagerListener extends Thread {

    private int NBPORT = 50000;
    private Socket sock;

    private PortForwardManager portForwardManager;


    public TcpManagerListener(PortForwardManager portForwardManager){
        super();
        this.portForwardManager = portForwardManager;
    }

    @Override
    public void run() {

        try {

            Socket socket = new Socket ("127.0.0.1", NBPORT);
            PrintWriter out = new PrintWriter (socket.getOutputStream(), true);

            System.out.println("Hello from JAVA TCP Client");

            out.close ();
            socket.close ();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
