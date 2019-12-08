package model;

public class RemoteLibrary {
	
	public String url;

	public RemoteLibrary (String url) {
		this.url = url;
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
