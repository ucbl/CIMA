package fr.liris.cima.gscl.discovery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CIMACommunicationHandler implements Runnable{

	public static final String CIMA_ADDRESS = System.getProperty("fr.liris.cima.gscl.adress");
	public static final String FORWARD_PORT = System.getProperty("fr.liris.cima.gscl.forwardPort");

	PrintWriter out = null;
	BufferedReader in = null;

	private Socket client;
	private String data;
	private String response;

	public CIMACommunicationHandler(Socket socket, String data) {
		this.client = socket;
		this.data = data;
	}

	@Override
	public void run() {
		try {
			out = new PrintWriter(new PrintWriter(client.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			// send connection  data to the c part
			out.print(data);
			out.flush();

			// Read server c response 
			this.response = in.readLine();
			in.close(); 
			out.close();
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
