package model;

public class VideoSegment {
	
	public String id_playlist;
	public int order_playlist;
	public boolean system;      // when TRUE this is actually stored in S3 bucket
	
	public VideoSegment (String name, String url, String chars, String transcript) {
		this.id_playlist = "";
		this.order_playlist = 0;
		
	}
	
	public VideoSegment (String id_playlist, int order_playlist, boolean system) {
		this.id_playlist = "";
		this.order_playlist = 0;
		this.id_playlist = id_playlist;
		this.order_playlist = (int)order_playlist; 
		this.system = system;
	}
	
	public boolean getSystem() { return system; }
	public void setSystem(boolean s) { system = s; }
	
	/**
	 * Equality of Constants determined by id_playlist alone.
	 */
	public boolean equals (Object o) {
		if (o == null) { System.out.println("got null"); return false; }
		
		if (o instanceof VideoSegment) {
			VideoSegment other = (VideoSegment) o;
			System.out.println(id_playlist);
			return id_playlist.equals(other.id_playlist);
		}
		
		return false;  // not a Constant
	}
}
