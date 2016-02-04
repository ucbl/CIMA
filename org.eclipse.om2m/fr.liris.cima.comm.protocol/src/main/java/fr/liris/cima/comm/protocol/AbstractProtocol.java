package fr.liris.cima.comm.protocol;

import java.lang.reflect.Field;

/**
 * {@code AbstractProtocol} implements generic methods of {@code Protocol} interface.
 * @author remi
 *
 */
public abstract class AbstractProtocol implements Protocol {

	/**
	 *	Get the value of a given parameter name
	 * @param paramName the parameter name
	 * @return the value of the parameter
	 */
	@Override
	public Object getParam(String paramName) {
		paramName = paramName.toLowerCase();
		try {
			// Get the private field
	        final Field field = this.getClass().getDeclaredField(paramName);
	        // Allow modification on the field
	        field.setAccessible(true);
	        // Return the parameter value
			return field.get(this);
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Set protocol parameters
	 * @param paramName the parameter name
	 * @param paramValue the parameter value
	 */
	@Override
	public void setParam(String paramName, Object paramValue) {
		paramName = paramName.toLowerCase();
		try {
			// Get the private field
	        final Field field = this.getClass().getDeclaredField(paramName);
	        // Allow modification on the field
	        field.setAccessible(true);
	        // Return the parameter value
			field.set(this, paramValue);
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 *	Get the protocol name
	 * @return the protocol name
	 */
	@Override
	public abstract String getName();

	/**
	 * Send message using this protocol
	 * @param message the message to send
	 */
	@Override
	public abstract void sendMessage(String message);

}
