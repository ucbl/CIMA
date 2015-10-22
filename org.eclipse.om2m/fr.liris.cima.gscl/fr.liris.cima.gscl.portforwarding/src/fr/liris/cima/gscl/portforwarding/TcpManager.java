package fr.liris.cima.gscl.portforwarding;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dasilvafrederic on 22/10/15.
 */
public class TcpManager  implements  Runnable{

    //input --> what we want to send
    private Queue<String> input = new LinkedBlockingQueue<String>();
    //output --> what it just arriving
    private Queue<String> output = new LinkedBlockingQueue<String>();
    private int NBPORT = 5000;
    private Socket sock;

    @Override
    public void run(){

        try {
            ServerSocket s = new ServerSocket(NBPORT);
            while(true){
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("DANS LE WHILE");
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                sock = s.accept();
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("ACCEPT");
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                //Open the output stream
                OutputStream out = sock.getOutputStream();

                System.out.println("New client, address " + sock.getInetAddress() + " on " + sock.getPort() + ".");

                /*if(!this.input.isEmpty()){
                    out.write(this.input.poll().getBytes());
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    System.out.println("REMMMMMMMMMMPLI");
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                    //if the protocol is perfect
                    this.input=null;

                }*/
                InputStream toto =sock.getInputStream();
                String res = new BufferedReader(new InputStreamReader(toto)).readLine();
                if (res!=null) {
                    this.output.add(res);


                }
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("ITTTTTERATIOOOOOON");
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                //Get the response

                out.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void sendMessage(String e) {
        this.input.add(e);
    }

    public String  getResponse() {
        return this.output.poll();
    }

}
