package http;

public class AddRemoteRequest {
	public String url;
	public String key;
	
	public String getUrl( ) { return url; }
	public void setName(String url) { this.url = url; }
	
	public String getKey( ) { return key; }
	public void setKey(String key) { this.key = key; }
	
	public AddRemoteRequest() {
	}
	
	public AddRemoteRequest (String url, String key) {
		this.url = url;
		this.key = key;
	}
	
	public String toString() {
		return "AddRemote(" + url  + ")";
	}
}
