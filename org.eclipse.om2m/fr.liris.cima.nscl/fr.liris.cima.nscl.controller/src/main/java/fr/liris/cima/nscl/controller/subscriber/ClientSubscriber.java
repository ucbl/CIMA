package fr.liris.cima.nscl.controller.subscriber;

public class ClientSubscriber {

	private String url;
	
	public ClientSubscriber(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(!(other instanceof ClientSubscriber)) return false;
		
		ClientSubscriber subscriber = (ClientSubscriber)other;
		if(this.url != null) {
			if(subscriber.url == null) return false;
		}
		
		return this.url.equals(subscriber.url);
	}
	
	public int hashCode() {
		int prime = 31;
		int result = 1;
	
		result = result*prime + (url == null ? 0 : url.hashCode());
		
		return result;
	}
	
	public String toString() {
		return "Subscriber("+ url + ")";
	}
}
