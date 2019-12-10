package http;

public class UnregisterRemoteRequest {
	public String url;
	
	public String getUrl( ) { return url; }
	public void setName(String url) { this.url = url; }
	
	public UnregisterRemoteRequest() {
	}
	
	public UnregisterRemoteRequest (String url) {
		this.url = url;
	}
	
	public String toString() {
		return "UnregisterRemote(" + url + ")";
	}
}
