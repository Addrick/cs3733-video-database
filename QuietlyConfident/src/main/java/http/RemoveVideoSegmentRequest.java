package http;

public class RemoveVideoSegmentRequest {
	public String id_playlist;
	public String id_video;
	
	public String getPL( ) { return id_playlist; }
	public void setPL(String id_playlist) { this.id_playlist = id_playlist; }
	
	public String getVS( ) { return id_video; }
	public void setVS(String id_video) { this.id_video = id_video; }
	
	public RemoveVideoSegmentRequest() {}
	
	public RemoveVideoSegmentRequest (String pl, String vs) {
		this.id_playlist = pl;
		this.id_video = vs;
		System.out.println("Request to remove video " + vs + " from playlist " + pl);
	}
	
	public String toString() {
		return "removeVS(" + id_playlist  + ", " + id_video + ")";
	}
}
