package model;

public class VideoSegment {
	
	public String id_video;
	public String characters;
	public String text;
	public String url;
	public boolean system;      // local when true, remote when false
	public boolean visible;		// public when true, private when false
	
	public VideoSegment (String id_video, String characters, String text, String url, boolean system) {
		this.id_video = id_video;
		this.characters = characters;
		this.text = text;
		if(system) {this.url = "https://3733quietlyconfident.s3.us-east-2.amazonaws.com/videosegments/" + id_video + ".ogg";} 
		else {this.url = url;}
		this.system = system;
		this.visible = true;
	}
	
	public VideoSegment (String id, String chars, String script, String url, boolean sys, boolean vis) {
		this.id_video = id;
		this.characters = chars;
		this.text = script;
		this.url = url;
		this.system = sys;
		this.visible = vis;
	}
	
	public boolean getSystem() { return system; }
	public String getID() { return id_video; }
	public String getChars() { return characters; }
	public String getURL() { return url; }
	public void setSystem(boolean s) {system = s;}
	public void setVisible(boolean v) {visible = v;}
	public boolean getVisible() {return visible;}
	
	public boolean equals (Object o) {
		if (o == null) {return false;}
		
		if (o instanceof VideoSegment) {
			VideoSegment other = (VideoSegment) o;
			System.out.println(id_video);
			return id_video.equals(other.id_video);
		}
		
		return false;  // not a Constant
	}
}
