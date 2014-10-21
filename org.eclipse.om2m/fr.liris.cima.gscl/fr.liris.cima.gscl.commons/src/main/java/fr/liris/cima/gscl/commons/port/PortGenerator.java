package fr.liris.cima.gscl.commons.port;

import java.util.HashSet;
/**
 * This class generate a port 
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
		do {
			 port = Min + (int)(Math.random() * ((Max - Min) + 1));
		}
		while(ports.contains(port));
		ports.add(port);
		return port;
	}
	
	public static void main(String args[]) {
		System.out.println(generatePort());
	}
}
