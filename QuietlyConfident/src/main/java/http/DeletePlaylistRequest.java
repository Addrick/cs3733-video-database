package http;

public class DeletePlaylistRequest {
	public String id_playlist;
	
	public void setName(String id_playlist) {this.id_playlist = id_playlist; }
	public String getName() {return id_playlist; }
	
	public DeletePlaylistRequest (String n) {
		this.id_playlist = n;
	}

	public DeletePlaylistRequest() {
		
	}
	
	public String toString() {
		return "Delete(" + id_playlist + ")";
	}
}
