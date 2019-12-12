package lambdaTests;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import http.RemoteSegmentsResponse;
import lambda.ListRemoteSegmentsHandler;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListRemoteSegmentsTest extends LambdaTest {

    @Test
    public void testListSegments() {
    	 
        RemoteSegmentsResponse resp = new ListRemoteSegmentsHandler().handleRequest(null, createContext("listsegs"));
        Assert.assertEquals(3, resp.VideoSegments.size());
        
        System.out.println(new Gson().toJson(resp));
    }
    
   
}
