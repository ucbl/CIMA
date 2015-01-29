package fr.liris.cima.comm.protocol;
/**
 * Interface to define use of protocol
 * @author Rémi Desmargez
 *
 */
public interface Protocol {
	/**
	 * To know the protocol name
	 * @return the protocol name
	 */
	public String getName();
	
	/**
	 * Getter
	 * @param paramName parameter's name
	 * @return value of the parameter
	 */
	public Object getParam(String paramName);
	
	/**
	 * Setter
	 * @param paramName parameter's name
	 * @param paramValue new parameter's value
	 */
	public void setParam(String paramName, Object paramValue);
	
	/**
	 * Send a message with the protocol
	 * @param message
	 */
	public void sendMessage(String message);
}
