package http;

import java.util.ArrayList;
import java.util.List;

import model.Playlist;
import model.RemoteLibrary;

public class AllRemoteResponse {
	public final List<RemoteLibrary> list;
	public final int statusCode;
	public final String error;
	
	public AllRemoteResponse (List<RemoteLibrary> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public AllRemoteResponse (int code, String errorMessage) {
		this.list = new ArrayList<RemoteLibrary>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyRemoteThing"; }
		return "AllRemote(" + list.size() + ")";
	}
}
