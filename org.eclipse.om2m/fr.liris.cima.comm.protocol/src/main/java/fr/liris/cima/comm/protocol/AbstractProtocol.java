package fr.liris.cima.comm.protocol;

import java.lang.reflect.Field;

public abstract class AbstractProtocol implements Protocol {

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

	@Override
	public abstract String getName();

	@Override
	public abstract void sendMessage(String message);

}
