package http;

import java.util.ArrayList;
import java.util.List;

import model.VideoSegment;

public class RemoteSegmentsResponse {
	public final List<VideoSegment> VideoSegments;
	public final int statusCode;
	public final String error;
	
	public RemoteSegmentsResponse (List<VideoSegment> list, int code) {
		this.VideoSegments = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public RemoteSegmentsResponse (int code, String errorMessage) {
		this.VideoSegments = new ArrayList<VideoSegment>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (VideoSegments == null) { return "EmptySegments"; }
		return "Segments(" + VideoSegments.size() + ")";
	}
}
