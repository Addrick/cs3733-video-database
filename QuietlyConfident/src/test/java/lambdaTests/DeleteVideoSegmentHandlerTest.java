package lambdaTests;

import static org.junit.Assert.assertEquals;

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
        uvsr.base64EncodedValue = "Mi43MTgyODE4Mjg=";
        UploadVideoSegmentResponse u_resp = new UploadVideoSegmentHandler().handleRequest(uvsr, createContext("upload"));
        Assert.assertEquals(200, u_resp.statusCode);
        
        // now delete
        DeleteVideoSegmentRequest dvsr = new DeleteVideoSegmentRequest(id);
        DeleteVideoSegmentRequest vsr = new DeleteVideoSegmentRequest();
        dvsr.setName("new name");
        assertEquals("new name",dvsr.getName());
        dvsr.setName(id);
        DeleteVideoSegmentResponse d_resp = new DeleteVideoSegmentHandler().handleRequest(dvsr, createContext("delete"));
        Assert.assertEquals(200, d_resp.statusCode);
        
        // try again and this should fail
        d_resp = new DeleteVideoSegmentHandler().handleRequest(dvsr, createContext("delete"));
        Assert.assertEquals(409, d_resp.statusCode);
        
    }
    
   
}
