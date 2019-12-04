package lambdaTests;

import java.io.*;

import org.junit.Assert;
import org.junit.Test;

import http.*;
import lambda.CreatePlaylistHandler;
import lambda.DeletePlaylistHandler;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeletePlaylistHandlerTest extends LambdaTest {

    @Test
    public void testCreateAndDeletePlaylist() {
    	// create playlist
        int rnd = (int) (Math.random() * 1000000);
        CreatePlaylistRequest ccr = new CreatePlaylistRequest("x" + rnd);
        
        CreatePlaylistResponse resp = new CreatePlaylistHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);
        
        // now delete
        DeletePlaylistRequest dcr = new DeletePlaylistRequest("x" + rnd);
        DeletePlaylistResponse d_resp = new DeletePlaylistHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals("x" + rnd, d_resp.id_playlist);
        
        // try again and this should fail
        d_resp = new DeletePlaylistHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(422, d_resp.statusCode);
    }
    
   
}
