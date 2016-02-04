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
 * Class to receive data on specified socket.
 */
public class CIMAReceiver implements Runnable{

	public static final String CIMA_ADDRESS = System.getProperty("fr.liris.cima.gscl.adress");
	public static final String FORWARD_PORT = System.getProperty("fr.liris.cima.gscl.forwardPort");

	PrintWriter out = null;
	BufferedReader in = null;

	private Socket client;
	private String data;
	
	private String response;

	public CIMAReceiver(Socket socket, String data) {
		this.client = socket;
		this.data = data;
	}


	@Override
	public void run() {
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
			response = new String(dataRecieved.getData());
			System.out.println("Data recieved : " + response); 
			System.out.println("From : " + dataRecieved.getAddress() + ":" + dataRecieved.getPort()); 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public String getResponse() {
		return response;
	}


	public void setResponse(String response) {
		this.response = response;
	}
}
