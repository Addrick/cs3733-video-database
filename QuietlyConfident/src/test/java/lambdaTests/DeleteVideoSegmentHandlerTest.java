package lambdaTests;

import java.io.*;

import org.junit.Assert;
import org.junit.Test;

import http.*;
import lambda.*;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteVideoSegmentHandlerTest extends LambdaTest {

    @Test
    public void testUploadAndDeleteVideoSegment() {
    	// create video
    	String id = "test_video";
    	String characters = "everybody and their brother";
    	String text = "I would've gotten away with it, if it weren't for those meddling kids!";
    	boolean isIt = true;
    	
        UploadVideoSegmentRequest uvsr = new UploadVideoSegmentRequest(id,characters,text,isIt);
        UploadVideoSegmentResponse u_resp = new UploadVideoSegmentHandler().handleRequest(uvsr, createContext("upload"));
        Assert.assertEquals(200, u_resp.statusCode);
        
        // now delete
        DeleteVideoSegmentRequest dvsr = new DeleteVideoSegmentRequest(id);
        DeleteVideoSegmentResponse d_resp = new DeleteVideoSegmentHandler().handleRequest(dvsr, createContext("delete"));
        Assert.assertEquals(200, d_resp.statusCode);
        
        // try again and this should fail
        d_resp = new DeleteVideoSegmentHandler().handleRequest(dvsr, createContext("delete"));
        Assert.assertEquals(422, d_resp.statusCode);
    }
    
   
}
