package http;

public class UploadVideoSegmentRequest {
	public String id_video;
	public String characters;
	public String text;
	public String url;
	public String base64EncodedValue;
	public boolean system;
	
	public String getID( ) { return id_video; }
	public void setID(String id_video) { this.id_video = id_video; }	
	
	public String getCharacters( ) { return characters; }
	public void setCharacters(String characters) { this.characters = characters; }	
	
	public String getText( ) { return text; }
	public void setText(String text) { this.text = text; }	
	
	public String getUrl( ) { return url; }
	public void setUrl(String url) { this.url = url; }
	
	public String getBase64EncodedValue() { return base64EncodedValue; }
	public void setBase64EncodedValue(String base64EncodedValue) { this.base64EncodedValue = base64EncodedValue; }
	
	public boolean getSystem( ) { return system; }
	public void setSystem(boolean system) { this.system = system; }
		
	public UploadVideoSegmentRequest() {
	}
	
	public UploadVideoSegmentRequest (String id_video, String characters, String text, boolean system) {
		this.id_video = id_video;
		this.characters = characters;
		this.text = text;
		this.url = "https://3733quietlyconfident.s3.us-east-2.amazonaws.com/videosegments/" + id_video + ".ogg"; 
		this.system = system;
		

	}
	
	public String toStringS3() {
		return "upload_video(" + id_video + "," + base64EncodedValue + ")";
	}	
	public String toStringSQL() {
		return "upload_video(" + id_video + "," + characters + "," + text + "," + url + ")";
	}
}
