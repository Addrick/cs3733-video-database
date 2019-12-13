package modelTests;


import org.junit.Assert;
import org.junit.Test;
import model.VideoSegment;

public class VideoSegmentTest {

	@Test
	public void testVS() {
		String id = "id";
		String characters = "characters";
		String text = "text";
		String url = "url";
		boolean system = true;
		VideoSegment vs = new VideoSegment(id,characters,text,url,system);
		Assert.assertEquals(id,vs.getID());
		Assert.assertEquals(characters,vs.getChars());
		Assert.assertEquals("https://3733quietlyconfident.s3.us-"
				+ "east-2.amazonaws.com/videosegments/" + id + ".ogg",vs.getURL());
		vs.setSystem(false);
		Assert.assertFalse(vs.getSystem());
		vs.setVisible(true);
		Assert.assertTrue(vs.getVisible());
		Assert.assertTrue(vs.equals(new VideoSegment("id","","","",true)));
		Assert.assertFalse(vs.equals(null));		
		Assert.assertFalse(vs.equals(1));
		VideoSegment vs1 = new VideoSegment(id,characters,text,url,false,false);
		VideoSegment vs2 = new VideoSegment(id,characters,text,url,false);
	}

}
