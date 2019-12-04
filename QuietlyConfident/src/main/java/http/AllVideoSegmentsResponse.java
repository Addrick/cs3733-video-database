package http;

import java.util.ArrayList;
import java.util.List;

import model.Playlist;
import model.VideoSegment;

public class AllVideoSegmentsResponse {
	public final List<VideoSegment> list;
	public final int statusCode;
	public final String error;
	
	public AllVideoSegmentsResponse (List<VideoSegment> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public AllVideoSegmentsResponse (int code, String errorMessage) {
		this.list = new ArrayList<VideoSegment>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "videos table empty"; }
		return "AllVideoSegments(" + list.size() + ")";
	}
}
