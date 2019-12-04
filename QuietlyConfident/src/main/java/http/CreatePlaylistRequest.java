package http;

public class CreatePlaylistRequest {
	public String id_playlist;
	public boolean system;
	
	public String getName( ) { return id_playlist; }
	public void setName(String id_playlist) { this.id_playlist = id_playlist; }
	
	public boolean getSystem( ) { return system; }
	public void setSystem(boolean system) { this.system = system; }
	
	public CreatePlaylistRequest() {
	}
	
	public CreatePlaylistRequest (String id) {
		this.id_playlist = id;
		System.out.println("Creating playlist with id " + id);
	}
	
	public CreatePlaylistRequest (String id, boolean system) {
		this.id_playlist = id;
		this.system = system;
		System.out.println("Creating playlist with id " + id + " and system status " + system);
	}
	
	public String toString() {
		return "CreatePlaylist(" + id_playlist  + ")";
	}
}
