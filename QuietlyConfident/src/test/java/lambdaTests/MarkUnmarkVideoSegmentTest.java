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
public class MarkUnmarkVideoSegmentTest extends LambdaTest {
	@Test
	public void testMarkUnmark() throws IOException {
        MarkVideoSegmentHandler mark = new MarkVideoSegmentHandler();
        MarkVideoSegmentRequest markReq = new MarkVideoSegmentRequest("Forget it");
        MarkVideoSegmentRequest markBlank = new MarkVideoSegmentRequest();
        Assert.assertEquals("Mark(Forget it)", markReq.toString());
        MarkVideoSegmentResponse markResp = mark.handleRequest(markReq, createContext("mark"));
        Assert.assertEquals(200, markResp.statusCode);
        Assert.assertEquals("MarkResponse(Forget it)", markResp.toString());
        markReq.setName("pleaseDontBeTheNameOfAVideo");
        Assert.assertEquals("pleaseDontBeTheNameOfAVideo", markReq.getName());
        MarkVideoSegmentResponse markResp2 = mark.handleRequest(markReq, createContext("mark"));
        Assert.assertEquals(409, markResp2.statusCode);
        Assert.assertEquals("ErrorResult(pleaseDontBeTheNameOfAVideo, statusCode=409, err="+markResp2.error+")", markResp2.toString());
        
        UnmarkVideoSegmentHandler unmark = new UnmarkVideoSegmentHandler();
        UnmarkVideoSegmentRequest unmarkReq = new UnmarkVideoSegmentRequest("Forget it");
        UnmarkVideoSegmentRequest unmarkBlank = new UnmarkVideoSegmentRequest();
        Assert.assertEquals("Unmark(Forget it)", unmarkReq.toString());
        UnmarkVideoSegmentResponse unmarkResp = unmark.handleRequest(unmarkReq, createContext("unmark"));
        Assert.assertEquals(200, unmarkResp.statusCode);
        Assert.assertEquals("UnmarkResponse(Forget it)", unmarkResp.toString());
        unmarkReq.setName("pleaseDontBeTheNameOfAVideo");
        Assert.assertEquals("pleaseDontBeTheNameOfAVideo", unmarkReq.getName());
        UnmarkVideoSegmentResponse unmarkResp2 = unmark.handleRequest(unmarkReq, createContext("unmark"));
        Assert.assertEquals(409, unmarkResp2.statusCode);
        Assert.assertEquals("ErrorResult(pleaseDontBeTheNameOfAVideo, statusCode=409, err="+unmarkResp2.error+")", unmarkResp2.toString());
	}
}
