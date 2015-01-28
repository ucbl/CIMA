package fr.liris.cima.gscl.discovery;

import java.io.IOException;
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
	
	public void sendInfos(String data ) {
		PrintWriter out = null;
		try {
			Socket client = new Socket(CIMA_ADDRESS, Integer.parseInt(FORWARD_PORT));
			out = new PrintWriter(new PrintWriter(client.getOutputStream()));
			out.print(data);
			out.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
