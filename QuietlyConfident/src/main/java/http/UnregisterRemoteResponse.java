package http;

/**
 * In most cases the response is the name of the constant that was being created.
 * 
 * if an error of some sort, then the response describes that error.
 *  
 */
public class UnregisterRemoteResponse {
	public final String response;
	public final int httpCode;
	public final String error;
	
	public UnregisterRemoteResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
		this.error = "";
	}
	
	// 200 means success
	public UnregisterRemoteResponse (String s, int code, String errorMessage) {
		this.response = s;
		this.httpCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}