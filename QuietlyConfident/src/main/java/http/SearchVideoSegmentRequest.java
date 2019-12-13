package http;

public class SearchVideoSegmentRequest {
	public String criteria;
	public void setCriteria(String c) {this.criteria = c;}
	public String getCriteria() {return this.criteria;}
	public SearchVideoSegmentRequest() {}
	
	public SearchVideoSegmentRequest (String criteria) {
		this.criteria = criteria;
		System.out.println("Searching video with criteria: " + criteria);
	}
	
	public String toString() {
		return "searchVS(" + criteria + ")";
	}
}
