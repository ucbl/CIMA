package fr.liris.cima.gscl.discovery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class allow to send information to C server.
 * @author madiallo
 *
 */
public class CIMAInternalCommunication {

	
	public CIMAInternalCommunication() {
		
	}
	
	public void sendInfos(String data ) {
		PrintWriter out = null;
		try {
			Socket client = new Socket("192.168.0.3", 1977);
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
