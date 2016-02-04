package fr.liris.cima.nscl.commons.subscriber;


public class ClientSubscriber {

	private String url;
	private String port;
	
	public ClientSubscriber(String url,String port) {
		this.url = url;
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(!(other instanceof ClientSubscriber)) return false;
		
		ClientSubscriber subscriber = (ClientSubscriber)other;
		if(this.url != null) {
			if(subscriber.url == null) return false;
		}
		if(this.port != null) {
			if(subscriber.port == null) return false;
		}
		
		return this.url.equals(subscriber.url) && this.port.equals(subscriber.port);
	}
	
	public int hashCode() {
		int prime = 31;
		int result = 1;
	
		result = result*prime + ((url == null || port == null) ? 0 : url.hashCode());
		
		return result;
	}
	
	public String toString() {
		return "Subscriber("+ url + ":" + port + ")";
	}
}
