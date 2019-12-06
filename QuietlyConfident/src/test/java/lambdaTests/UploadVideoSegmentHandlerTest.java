package lambdaTests;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.gson.Gson;

import db.PlaylistsDAO;
import db.VideoSegmentsDAO;
import http.AllPlaylistsResponse;
import http.UploadVideoSegmentRequest;
import http.UploadVideoSegmentResponse;
import lambda.UploadVideoSegmentHandler;
import model.Playlist;
import model.VideoSegment;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UploadVideoSegmentHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	UploadVideoSegmentHandler handler = new UploadVideoSegmentHandler();
    	UploadVideoSegmentRequest req = new Gson().fromJson(incoming, UploadVideoSegmentRequest.class);
       
        UploadVideoSegmentResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	UploadVideoSegmentHandler handler = new UploadVideoSegmentHandler();
    	UploadVideoSegmentRequest req = new Gson().fromJson(incoming, UploadVideoSegmentRequest.class);

    	UploadVideoSegmentResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }
    
//    @Test
//    public void testShouldBeDuplicate() {
//    	int rndNum = (int)(20000*(Math.random()));
//    	String var = "throwAway" + rndNum;
//    	
//    	UploadVideoSegmentRequest ccr = new UploadVideoSegmentRequest(var, "Mi43MTgyODE4Mjg=");
//    	String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
//        
//        try {
//        	testSuccessInput(SAMPLE_INPUT_STRING);
//        	testFailInput(SAMPLE_INPUT_STRING, 422);
//        	
//        	 DeleteVideoSegmentRequest dcr = new DeleteVideoSegmentRequest(var);
//             DeleteVideoSegmentResponse d_resp = new DeleteVideoSegmentHandler().handleRequest(dcr, createContext("delete"));
//             
//        } catch (IOException ioe) {
//        	Assert.fail("Invalid:" + ioe.getMessage());
//        }
//        
//    }
   
    // NOTE: this proliferates large number of VideoSegments! Be mindful
    @Test
    public void testShouldBeOk() {
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAway" + rndNum;
    	
    	UploadVideoSegmentRequest ccr = new UploadVideoSegmentRequest(var, "Mi43MTgyODE4Mjg=", var, var, false);
    	//String SAMPLE_INPUT_STRING = "{\"name\": \"" + var + "\", \"value\": \"Mi43MTgyODE4Mjg=\"}";
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
//    
//    @Test
//    public void testFailInput() {
//    	int rndNum = (int)(990*(Math.random()));
//    	String var = "throwAway" + rndNum;
//    	UploadVideoSegmentRequest ccr = new UploadVideoSegmentRequest(var, "this is not base64 encoded", var, var, false);
//    	String SAMPLE_INPUT_STRING =  new Gson().toJson(ccr);  
//        
//        try {
//        	testFailInput(SAMPLE_INPUT_STRING, 400);
//        } catch (IOException ioe) {
//        	Assert.fail("Invalid:" + ioe.getMessage());
//        }
//    }
//    
//    @Test
//    public void testGarbageInput() {
//    	String SAMPLE_INPUT_STRING = "{\"sdsd\": \"e3\", \"hgfgdfgdfg\": \"this is not a number\"}";
//        
//        try {
//        	testFailInput(SAMPLE_INPUT_STRING, 400);
//        } catch (IOException ioe) {
//        	Assert.fail("Invalid:" + ioe.getMessage());
//        }
//    }
//    
    // overwrites into it
    @Test
    public void testCreateSystemVideoSegment() {
    	// create VideoSegment
        UploadVideoSegmentRequest csr = new UploadVideoSegmentRequest("to-delete-again", "Mi43MTgyODE4Mjg=", "idk", "idk", true);
        
        UploadVideoSegmentResponse resp = new UploadVideoSegmentHandler().handleRequest(csr, createContext("create"));
        Assert.assertEquals(200, resp.statusCode);
    }
    
}
