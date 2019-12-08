package http;

public class UnmarkVideoSegmentRequest {
	public String id_video;
	
	public void setName(String id_video) {this.id_video = id_video; }
	public String getName() {return id_video; }
	
	public UnmarkVideoSegmentRequest (String n) {this.id_video = n;}

	public UnmarkVideoSegmentRequest() {}
	
	public String toString() {return "Unmark(" + id_video + ")";}
}
