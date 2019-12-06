package model;

public class VideoSegment {
	
	public String id_video;
	public String characters;
	public String transcript;
	public String url_video;
	public boolean system;      // when TRUE this is actually stored in S3 bucket
	
//	public VideoSegment (String name, String url, String chars, String transcript) {
//		this.id_playlist = "";
//		this.order_playlist = 0;
//		
//	}
	
	public VideoSegment (String id_video, String characters, String transcript, String url_video, boolean system) {
		this.id_video = id_video;
		this.characters = characters;
		this.transcript = transcript;
		this.url_video = url_video; 
		this.system = system;
	}
	
	public boolean getSystem() { return system; }
	public String getID() { return id_video; }
	public String getChars() { return characters; }
	public String getURL() { return url_video; }
	public void setSystem(boolean s) { system = s; }
	
	/**
	 * no idea what this does
	 */
	public boolean equals (Object o) {
		if (o == null) { System.out.println("got null"); return false; }
		
		if (o instanceof VideoSegment) {
			VideoSegment other = (VideoSegment) o;
			System.out.println(id_video);
			return id_video.equals(other.id_video);
		}
		
		return false;  // not a Constant
	}
}
