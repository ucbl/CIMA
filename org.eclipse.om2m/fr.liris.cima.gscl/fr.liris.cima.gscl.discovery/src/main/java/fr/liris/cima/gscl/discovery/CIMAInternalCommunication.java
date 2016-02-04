package fr.liris.cima.gscl.discovery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class allow to send information to C server
 * by opening socket connection between gscl and port forwarding part,
 * sending connection data and reading server response.
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
			out.print(data);
			out.flush();

			// Read server c response 
			String inputLine = in.readLine();

			in.close(); 
			out.close();
			return inputLine;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String handleRequest(String data) {
		try {
			Socket client = new Socket(CIMA_ADDRESS, Integer.parseInt(FORWARD_PORT));
			CIMAReceiver cimaSender = new CIMAReceiver(client, data);
			return cimaSender.getResponse();
			
//			CIMACommunicationHandler cimaSender = new CIMACommunicationHandler(client, data);
//			return cimaSender.getResponse();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String sendInfosUDP(String data ) {
		try {

			InetAddress serveur = InetAddress.getByName(CIMA_ADDRESS); 
			
			int length = data.length(); 
			byte buffer[] = data.getBytes(); 
			DatagramPacket dataSent = new DatagramPacket(buffer,length,serveur,Integer.parseInt(FORWARD_PORT)); 
			DatagramSocket socket = new DatagramSocket(); 
			System.out.println("EEEEEEEEEEE send connection  data to the c part"+serveur.getHostName());
			socket.send(dataSent); 
			System.out.println("DONE send connection  data to the c part");

			DatagramPacket dataRecieved = new DatagramPacket(new byte[length],length); 
			socket.receive(dataRecieved); 
			System.out.println("DONE1 send connection  data to the c part");

			// Read server c response 
			String inputLine = new String(dataRecieved.getData());
			System.out.println("Data recieved : " + inputLine); 
			System.out.println("From : " + dataRecieved.getAddress() + ":" + dataRecieved.getPort()); 
			return inputLine;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public String sendInfos(String data ) {
//		PrintWriter out = null;
//		BufferedReader in = null;
//
//		try {
//			// Open socket connection between gscl and port forwarding part
//			Socket client = new Socket(CIMA_ADDRESS, Integer.parseInt(FORWARD_PORT));
//
//			out = new PrintWriter(new PrintWriter(client.getOutputStream()));
//			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//
//			// send connection  data to the c part
//			out.print(data);
//			out.flush();
//
//			// Read server c response 
//			String inputLine = in.readLine();
//
//			in.close(); 
//			out.close();
//
//			return inputLine;
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}