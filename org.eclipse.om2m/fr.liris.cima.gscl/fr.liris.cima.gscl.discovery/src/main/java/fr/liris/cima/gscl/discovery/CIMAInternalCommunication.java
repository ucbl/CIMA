package fr.liris.cima.gscl.discovery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class allow to send information to C server.
 * @author madiallo
 *
 */
public class CIMAInternalCommunication {

	public static final String CIMA_ADDRESS = System.getProperty("fr.liris.cima.gscl.adress");
	public static final String FORWARD_PORT = System.getProperty("fr.liris.cima.gscl.forwardPort");

	
	public CIMAInternalCommunication() {		
	}
	
	public String sendInfos(String data ) {
		PrintWriter out = null;
        BufferedReader in = null;

		try {
			// Open socket connection between gscl and port forwarding part
			Socket client = new Socket(CIMA_ADDRESS, Integer.parseInt(FORWARD_PORT));
			
			out = new PrintWriter(new PrintWriter(client.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // send connection  data to the c part

			System.out.println("EEEEEEEEEEE send connection  data to the c part");
			System.out.println(data);
			out.print(data);
			out.flush();
			
			// Read server c response 
//			String inputLine = in.readLine();
			String inputLine,rep="";
			while((inputLine = in.readLine()) != null){
				rep += inputLine;
			}
			System.out.println("REP :");
			System.out.println(rep);
	        in.close(); 
            out.close();
            
			return rep;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
