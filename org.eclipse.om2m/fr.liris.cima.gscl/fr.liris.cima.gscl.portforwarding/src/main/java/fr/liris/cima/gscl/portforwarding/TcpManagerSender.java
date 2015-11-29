package fr.liris.cima.gscl.portforwarding;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Maxime on 22/10/2015.
 */
public class TcpManagerSender {

    private static int PORT = 50000;




    public static void sendMessage(String m){



        InputStream inputGet;
        OutputStream outSend;
        PrintWriter out;
        Socket socket;

        try
        {
            socket = new Socket((String) null, PORT);


            outSend = socket.getOutputStream();
            out = new PrintWriter(outSend, true);
            System.out.println("ENVOI DE : " + m);
            out.print(m);
            System.out.println("ENVOYEE");
            out.flush();


            socket.shutdownOutput(); //HAHAHA
            

            socket.setSoTimeout(3000);

            
            inputGet = socket.getInputStream();

            try{
            System.out.println("111");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputGet));

            String reponse = bufferedReader.readLine();

            System.out.printf("222");

            System.out.println("MESSAGE RECU ==> " + reponse);

            System.out.println("333");

            }
            catch(java.net.SocketTimeoutException e){
            	System.out.println("ERROR : timeout during port forwarding answer.");
            }
            inputGet.close();
            socket.close();
        }
        catch (UnknownHostException e) {
            System.out.println("uNKNOWNHOST EXCEPTION EEE");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOEXEPTION EEE");
            e.printStackTrace();
        }
}

}
