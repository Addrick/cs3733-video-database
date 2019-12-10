package model;

public class RemoteLibrary {
	
	public String url;
	public String key;

	public RemoteLibrary (String url, String key) {
		this.url = url;
		this.key = key;
	}
	
	/**
	 * no idea what this does
	 */
	public boolean equals (Object o) {
		if (o == null) { System.out.println("got null"); return false; }
		
		if (o instanceof RemoteLibrary) {
			RemoteLibrary other = (RemoteLibrary) o;
			System.out.println(url);
			return url.equals(other.url);
		}
		
		return false;  // not a Constant
	}
}
