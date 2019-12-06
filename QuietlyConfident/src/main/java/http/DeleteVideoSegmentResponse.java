package http;

/** Sends back the id_video of the constant deleted -- easier to handle on client-side. */
public class DeleteVideoSegmentResponse {
	public final String id_video;
	public final int statusCode;
	public final String error;
	
	public DeleteVideoSegmentResponse (String id_video, int statusCode) {
		this.id_video = id_video;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	// 200 means success
	public DeleteVideoSegmentResponse (String id_video, int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.id_video = id_video;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "DeleteResponse(" + id_video + ")";
		} else {
			return "ErrorResult(" + id_video + ", statusCode=" + statusCode + ", err=" + error + ")";
		}
	}
}
