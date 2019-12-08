package lambdaTests;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import http.*;
import lambda.ListAllRemoteHandler;
import model.RemoteLibrary;;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListRemoteTest extends LambdaTest {
	
    @Test
    public void testGetList() throws IOException {
    	ListAllRemoteHandler handler = new ListAllRemoteHandler();

        AllRemoteResponse resp = handler.handleRequest(null, createContext("list"));
        
        boolean hasE = false;
        for (RemoteLibrary c : resp.list) {
        	if (c.url.equals("test")) { hasE = true; break; }
        }
        Assert.assertTrue("e Needs to exist in the playlists table (from tutorial) for this test case to work.", hasE);
        Assert.assertEquals(200, resp.statusCode);
        System.out.println(resp.statusCode);
    }

}
