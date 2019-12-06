package http;

public class DeleteVideoSegmentRequest {
	public String id_video;
	
	public void setName(String id_video) {this.id_video = id_video; }
	public String getName() {return id_video; }
	
	public DeleteVideoSegmentRequest (String n) {
		this.id_video = n;
	}

	public DeleteVideoSegmentRequest() {
		
	}
	
	public String toString() {
		return "Delete(" + id_video + ")";
	}
}
