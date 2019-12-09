package http;

public class AddRemoteRequest {
	public String url;
	public boolean system;
	
	public String getUrl( ) { return url; }
	public void setName(String url) { this.url = url; }
	
	public boolean getSystem( ) { return system; }
	public void setSystem(boolean system) { this.system = system; }
	
	public AddRemoteRequest() {
	}
	
	public AddRemoteRequest (String url) {
		this.url = url;
		System.out.println("Adding remote library with URL = " + url);
	}
	
	public AddRemoteRequest (String url, boolean system) {
		this.url = url;
		this.system = system;
		System.out.println("Adding remote library with URL = " + url + " and system status " + system);
	}
	
	public String toString() {
		return "AddRemote(" + url  + ")";
	}
}
