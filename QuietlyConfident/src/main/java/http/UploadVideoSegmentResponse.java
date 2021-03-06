package http;

/**
 * In most cases the response is the name of the constant that was being created.
 * 
 * if an error of some sort, then the response describes that error.
 *  
 */
public class UploadVideoSegmentResponse {
	public final String response;
	public final int statusCode;
	
	public UploadVideoSegmentResponse (String s, int statusCode) {
		this.response = s;
		this.statusCode = statusCode;
	}
	
	// 200 means success
	public UploadVideoSegmentResponse (String s) {
		this.response = s;
		this.statusCode = 200;
	}
	
	public String toString() {
		return "Response(" + statusCode + ", " + response + ")";
	}
}
