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

import http.AddRemoteRequest;
import http.AddRemoteResponse;
import lambda.AddRemoteHandler;
import model.RemoteLibrary;
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
public class AddRemoteHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	AddRemoteHandler handler = new AddRemoteHandler();
    	AddRemoteRequest req = new Gson().fromJson(incoming, AddRemoteRequest.class);
       
        AddRemoteResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.httpCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	AddRemoteHandler handler = new AddRemoteHandler();
    	AddRemoteRequest req = new Gson().fromJson(incoming, AddRemoteRequest.class);

    	AddRemoteResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.httpCode);
    }

    @Test
    public void testAddRemoteSite() {
    	// create VideoSegment
        AddRemoteRequest csr = new AddRemoteRequest("to-delete-again");
        
        AddRemoteResponse resp = new AddRemoteHandler().handleRequest(csr, createContext("create"));
        Assert.assertEquals(200, resp.httpCode);
    }
    
}
