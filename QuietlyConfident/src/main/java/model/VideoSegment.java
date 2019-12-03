package model;

public class VideoSegment {
	
	public final String name;
	public final String url;
	public final String chars;
	public final String transcript;
	public boolean system;      // when TRUE this is actually stored in S3 bucket
	
	public VideoSegment (String name, String url, String chars, String transcript, boolean system) {
		this.name = name;
		this.url = url;
		this.chars = chars;
		this.transcript = transcript;
		this.system = system;
	}
	
	public boolean getSystem() { return system; }
	public void setSystem(boolean s) { system = s; }
	
	/**
	 * Equality of video segments determined by the name alone.
	 */
	public boolean equals (Object o) {
		if (o == null) { System.out.println("got null"); return false; }
		
		if (o instanceof VideoSegment) {
			VideoSegment other = (VideoSegment) o;
			System.out.println(name);
			return name.equals(other.name);
		}
		
		return false;  // not a Constant
	}
}
