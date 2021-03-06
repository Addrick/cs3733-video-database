package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import db.PlaylistsDAO;
import db.VideoSegmentsDAO;
import http.AllPlaylistsResponse;
import http.UploadVideoSegmentRequest;
import http.UploadVideoSegmentResponse;
import model.Playlist;
import model.VideoSegment;

import java.io.ByteArrayInputStream;

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
 * Create a new VideoSegment and store in S3 bucket.

 * @author 
 */
public class UploadVideoSegmentHandler implements RequestHandler<UploadVideoSegmentRequest,UploadVideoSegmentResponse> {

	LambdaLogger logger;

	// To access S3 storage
	private AmazonS3 s3 = null;

	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "videosegments/";
	public static final String TEST_BUCKET = "testvideosegments/";

	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */ 
	boolean UploadVideoSegment(String id_video, String characters, String text, String url, boolean system) throws Exception { 
		if (logger != null) { logger.log("in UploadVideoSegment"); }
		VideoSegmentsDAO dao = new VideoSegmentsDAO();
		logger.log("video item id field: " + id_video);
		logger.log("video item character field: " + characters);
		logger.log("video item script field: " + text);

		// check if present
		VideoSegment exist = dao.getVideoSegment(id_video);
		VideoSegment VideoSegment = new VideoSegment (id_video, characters, text, url, system);
		if (exist == null) {
			return dao.addVideoSegment(VideoSegment);
		} else {
			return false;
		}
	}

	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	boolean uploadSystemVideoSegment(String name, byte[]  contents) throws Exception {
		if (logger != null) { logger.log("in createSystemVideoSegment"); }

		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
			logger.log("attach to S3 succeed");
		}

		String bucket = REAL_BUCKET;
		boolean useTestDB = System.getenv("TESTING") != null;
		if (useTestDB) {
			bucket = TEST_BUCKET;
		}

		ByteArrayInputStream bais = new ByteArrayInputStream(contents);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(contents.length);

		// makes the object publicly visible
		PutObjectResult res = s3.putObject(new PutObjectRequest("3733quietlyconfident", bucket + name, bais, omd)
				.withCannedAcl(CannedAccessControlList.PublicRead));

		// if we ever get here, then whole thing was stored
		return true;
	}

	@Override 
	public UploadVideoSegmentResponse handleRequest(UploadVideoSegmentRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		UploadVideoSegmentResponse response;
		try {
			byte[] encoded = java.util.Base64.getDecoder().decode(req.base64EncodedValue);
			if (uploadSystemVideoSegment(req.id_video + ".ogg", encoded)) {
				if(UploadVideoSegment(req.id_video, req.characters, req.text, req.url, req.system)) {
					System.out.println("Video added to RDS successfully.");
				}
				else {
					System.out.println("Error adding video to RDS. (likely duplicate)");
				}
				response = new UploadVideoSegmentResponse(req.id_video);
			} else {
				response = new UploadVideoSegmentResponse(req.id_video, 422);
			}
		}

		catch (Exception e) {
			response = new UploadVideoSegmentResponse("Unable to create VideoSegment: " + req.id_video + "(" + e.getMessage() + ")", 400);
		}
		System.out.println(response);
		return response;
	}
}
