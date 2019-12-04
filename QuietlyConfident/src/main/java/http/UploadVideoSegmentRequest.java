package http;

public class UploadVideoSegmentRequest {
	public String id_video;
	public String characters;
	public String transcript;
	public String url_video;
	public boolean system;
	
	public String getId_video( ) { return id_video; }
	public void setName(String id_video) { this.id_video = id_video; }	
	
	public String getCharacters( ) { return characters; }
	public void setCharacters(String characters) { this.id_video = characters; }	
	
	public String getTranscript( ) { return transcript; }
	public void setTranscript(String transcript) { this.transcript = transcript; }	
	
	public String getUrl_video( ) { return url_video; }
	public void setUrl_video(String url_video) { this.id_video = url_video; }
	
	public boolean getSystem( ) { return system; }
	public void setSystem(boolean system) { this.system = system; }
		
	public UploadVideoSegmentRequest() {
	}
	
	public UploadVideoSegmentRequest (String id_video, String characters, String transcript, String url_video, boolean system) {
		this.id_video = id_video;
		this.characters = characters;
		this.transcript = transcript;
		this.url_video = url_video; 
		this.system = system;
	}
	
	public String toString() {
		return "UploadVideoSegment(" + id_video + "," + characters + "," + transcript + "," + url_video + ")";
	}
}
