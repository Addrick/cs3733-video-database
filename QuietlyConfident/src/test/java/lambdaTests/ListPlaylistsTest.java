package lambdaTests;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import http.*;
import lambda.ListAllPlaylistsHandler;
import model.Playlist;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListPlaylistsTest extends LambdaTest {
	
    @Test
    public void testGetList() throws IOException {
    	ListAllPlaylistsHandler handler = new ListAllPlaylistsHandler();

        AllPlaylistsResponse resp = handler.handleRequest(null, createContext("list"));
        
        boolean hasE = false;
        for (Playlist c : resp.list) {
        	if (c.id_playlist.equals("test_pl")) { hasE = true; break; }
        }
        Assert.assertTrue("e Needs to exist in the playlists table (from tutorial) for this test case to work.", hasE);
        Assert.assertEquals(200, resp.statusCode);
        System.out.println(resp.statusCode);
    }

}
