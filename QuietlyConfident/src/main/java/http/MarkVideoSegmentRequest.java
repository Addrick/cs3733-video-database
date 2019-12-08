package http;

public class MarkVideoSegmentRequest {
	public String id_video;
	
	public void setName(String id_video) {this.id_video = id_video; }
	public String getName() {return id_video; }
	
	public MarkVideoSegmentRequest (String n) {
		this.id_video = n;
	}

	public MarkVideoSegmentRequest() {
		
	}
	
	public String toString() {
		return "Mark(" + id_video + ")";
	}
}
