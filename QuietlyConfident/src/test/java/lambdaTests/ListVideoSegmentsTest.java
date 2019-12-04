package lambdaTests;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import http.*;
import lambda.ListVideoSegmentsHandler;
import model.VideoSegment;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListVideoSegmentsTest extends LambdaTest {
	
    @Test
    public void testGetList() throws IOException {
    	ListVideoSegmentsHandler handler = new ListVideoSegmentsHandler();

        AllVideoSegmentsResponse resp = handler.handleRequest(null, createContext("list"));
        
        boolean hasE = false;
        for (VideoSegment c : resp.list) {
        	if (c.id_video.equals("I Distill it Myself")) { hasE = true; break; }
        }
        Assert.assertTrue("some conditions probably apply for this test case to work.", hasE);
        Assert.assertEquals(200, resp.statusCode);
        System.out.println(resp.statusCode);
    }

}
