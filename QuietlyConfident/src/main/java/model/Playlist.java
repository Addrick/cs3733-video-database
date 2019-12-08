package model;

import java.util.List;

import model.VideoSegment;

public class Playlist {
	
	public final String id_playlist;
	public final int order_playlist;
	public List<String> videos;
	public boolean system;      // when TRUE this is actually stored in S3 bucket
	
	public Playlist(String id_playlist, int order_playlist) {
		this.id_playlist = id_playlist;
		this.order_playlist = order_playlist;
		System.out.println(id_playlist);
	}
	
	public Playlist(String id_playlist, double order_playlist, boolean system) {
		this.id_playlist = id_playlist;
		this.order_playlist = (int)order_playlist; 
		this.system = system;
	}
	
	public Playlist(String id_playlist, int order_playlist, List<String> videos) {
		this.id_playlist = id_playlist;
		this.order_playlist = order_playlist;
		this.videos = videos;
	}
	
	public boolean getSystem() { return system; }
	public void setSystem(boolean s) { system = s; }
	
	/**
	 * Equality of Constants determined by id_playlist alone.
	 */
	public boolean equals (Object o) {
		if (o == null) { System.out.println("got null"); return false; }
		
		if (o instanceof Playlist) {
			Playlist other = (Playlist) o;
			System.out.println(id_playlist);
			return id_playlist.equals(other.id_playlist);
		}
		
		return false;  // not a Constant
	}
}
