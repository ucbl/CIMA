package fr.liris.cima.comm.http;

import org.eclipse.om2m.commons.rest.RequestIndication;

import fr.liris.cima.nscl.commons.constants.Constants;
import fr.liris.cima.comm.protocol.AbstractProtocol;
import fr.liris.cima.comm.protocol.Protocol;

/**
 * Http protocol implementation
 * @author remi
 *
 */
public class HTTP extends AbstractProtocol {
	/**
	 * base URL
	 */
	private String base;

	/**
	 * Http method can be GET, POST, PUT, DELETE...
	 */
	private String method;

	/**
	 * Http port to contact
	 */
	private int port;

	/**
	 * Http uri to contact
	 */
	private String uri;

	/**
	 * Http body content
	 */
	private String body;

	/**
	 * Http transport protocol
	 */
	private String transport = "tcp";

	@Override
	public String getName() {
		return "http";
	}

	/**
	 * Send message using cima http rest client
	 * @param message the message to send
	 */
	@Override
	public void sendMessage(String message) {
		CIMARestHttpClient cimaRestHttpClient = new CIMARestHttpClient();
		RequestIndication requestIndication = new RequestIndication(method, uri, Constants.REQENTITY, ((body == null) ? "" : body));
		requestIndication.setBase(base);

		cimaRestHttpClient.sendRequest(requestIndication);
	}

	/**
	 * Test method
	 * @param args a Simple args for main method
	 */
	public static void main(String [] args){
		Protocol httpProtocol = new HTTP();
		httpProtocol.setParam("method", "GET");
		httpProtocol.setParam("base", "http://localhost");
		httpProtocol.setParam("port", new Integer(8080));
		httpProtocol.setParam("uri", "/capabilities/temperature");
		httpProtocol.setParam("body", null);

		System.out.println("Created protocol : " + HTTP.class.getSimpleName().toLowerCase());
		System.out.println("\tname : " + httpProtocol.getName());
		System.out.println("\tmethod : " + httpProtocol.getParam("method"));
		System.out.println("\tport : " + httpProtocol.getParam("port"));
		System.out.println("\turi : " + httpProtocol.getParam("uri"));
		System.out.println("\tbody : " + httpProtocol.getParam("body"));
	}

}
