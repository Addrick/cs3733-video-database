package http;

/** Sends back the id_playlist of the constant deleted -- easier to handle on client-side. */
public class DeletePlaylistResponse {
	public final String id_playlist;
	public final int statusCode;
	public final String error;
	
	public DeletePlaylistResponse (String id_playlist, int statusCode) {
		this.id_playlist = id_playlist;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	// 200 means success
	public DeletePlaylistResponse (String id_playlist, int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.id_playlist = id_playlist;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "DeleteResponse(" + id_playlist + ")";
		} else {
			return "ErrorResult(" + id_playlist + ", statusCode=" + statusCode + ", err=" + error + ")";
		}
	}
}
