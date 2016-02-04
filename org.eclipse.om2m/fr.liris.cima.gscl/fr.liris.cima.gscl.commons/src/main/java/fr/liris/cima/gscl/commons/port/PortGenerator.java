package fr.liris.cima.gscl.commons.port;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;

/**
 * This class randomly generate a port number between 1050 and 15000
 * @author madiallo
 *
 */
public class PortGenerator {

	static int Min = 1050;
	static int Max = 15000;
	static int PORT = Min + (int)(Math.random() * ((Max - Min) + 1));
	
	static HashSet<Integer> ports = new HashSet<>();
	
	
	public static int generatePort() {
		int port = 0;
		boolean ok = false;
		do {
			 try {
				port = new ServerSocket(0).getLocalPort();
				ok = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		while(ports.contains(port) || ok == false);
		ports.add(port);
		return port;
	}
	
	public static void main(String args[]) {
		System.out.println(generatePort());
	}
}
