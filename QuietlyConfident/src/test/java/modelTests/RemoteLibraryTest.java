package modelTests;


import org.junit.Assert;
import org.junit.Test;
import model.RemoteLibrary;

public class RemoteLibraryTest {

	@Test
	public void test() {
		String url = "url";
		String key = "key";
		RemoteLibrary rl = new RemoteLibrary(url, key);
		
		Assert.assertFalse(rl.equals(1));
		Assert.assertFalse(rl.equals(null));
		Assert.assertTrue(rl.equals(new RemoteLibrary("url","")));
	}

}
