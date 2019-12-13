package modelTests;


import java.awt.List;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import model.Playlist;

import org.junit.Test;

public class PlaylistTest {

	@Test
	public void test() {
		String id = "integers are the coolest";
		int order = 2;
		
		String double_id = "doubles blow";
		double double_order = 3;
		
		ArrayList<String> videos = new ArrayList();
		
		Playlist p = new Playlist(id, order);
		Playlist p2 = new Playlist(double_id, double_order, false);
		Playlist p3 = new Playlist(id, order,videos);
		
		p2.setSystem(true);
		Assert.assertTrue(p2.getSystem());
		
		Assert.assertTrue(p2.equals(new Playlist("doubles blow", 1)));
		Assert.assertFalse(p2.equals(null));
		Assert.assertFalse(p.equals(2));
		
	}

}
