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
import db.VideoSegmentsDAO;
import http.*;
import lambda.*;
import model.*;
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
public class SearchTest extends LambdaTest {
	@Test
	public void testSearch() throws IOException {
        SearchVideoSegmentHandler search = new SearchVideoSegmentHandler();
        SearchVideoSegmentRequest searchReq = new SearchVideoSegmentRequest("celebrate the syndicate");
        SearchVideoSegmentRequest searchBlank = new SearchVideoSegmentRequest();
        Assert.assertEquals("searchVS(celebrate the syndicate)", searchReq.toString());
        SearchVideoSegmentResponse searchResp = search.handleRequest(searchReq, createContext("search"));
        Assert.assertEquals(200, searchResp.statusCode);
        Assert.assertEquals("SearchVideoSegments(1)", searchResp.toString());
        searchReq.setCriteria("thisisnevergoingtobeinthedatabase");
        Assert.assertEquals("thisisnevergoingtobeinthedatabase", searchReq.getCriteria());
        SearchVideoSegmentResponse searchResp2 = search.handleRequest(searchReq, createContext("search"));
        SearchVideoSegmentResponse searchResp3 = new SearchVideoSegmentResponse(400, "test");
        searchReq.setCriteria("a");
        SearchVideoSegmentResponse searchResp4 = search.handleRequest(searchReq, createContext("search"));  
    }
}
