package http;

public class RemoveVideoSegmentRequest {
	public String id_playlist;
	public String id_video;
	public int num; // represents the position in the playlist
	
	public String getPL( ) { return id_playlist; }
	public void setPL(String id_playlist) { this.id_playlist = id_playlist; }
	
	public String getVS( ) { return id_video; }
	public void setVS(String id_video) { this.id_video = id_video; }
	
	public RemoveVideoSegmentRequest() {}
	
	public RemoveVideoSegmentRequest (String pl, String vs, int n) {
		this.id_playlist = pl;
		this.id_video = vs;
		this.num = n;
		System.out.println("Request to remove video " + vs + " from playlist " + pl);
	}
	
	public String toString() {
		return "removeVS(" + id_playlist  + ", " + id_video + ", " + num + ")";
	}
}
