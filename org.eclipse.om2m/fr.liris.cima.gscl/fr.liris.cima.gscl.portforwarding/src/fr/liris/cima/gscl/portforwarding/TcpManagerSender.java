package fr.liris.cima.gscl.portforwarding;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Maxime on 22/10/2015.
 */
public class TcpManagerSender {

    private static int PORTTOSEND = 5001;


    public static void sendMessage(String m) throws IOException {



        ServerSocket serverSocket = new ServerSocket(PORTTOSEND);

        Socket connectionSocket = serverSocket.accept();
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

        outToClient.writeBytes(m);

        connectionSocket.close();
        serverSocket.close();
}

}
