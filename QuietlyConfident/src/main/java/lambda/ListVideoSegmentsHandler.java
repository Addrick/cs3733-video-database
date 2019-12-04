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

import db.VideoSegmentsDAO;
import db.VideoSegmentsDAO;
import http.AllVideoSegmentsResponse;
import http.AllVideoSegmentsResponse;
import model.VideoSegment;
import model.VideoSegment;

/**
 * Eliminated need to work with JSON
 */
public class ListVideoSegmentsHandler implements RequestHandler<Object,AllVideoSegmentsResponse> {

	public LambdaLogger logger;

	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "videos";
	public static final String TEST_BUCKET = "testvideos";

	/** Load from RDS, if it exists
	 *  
	 * @throws Exception 
	 */
	List<VideoSegment> getVideoSegments() throws Exception {
		logger.log("in getVideoSegments");
		VideoSegmentsDAO dao = new VideoSegmentsDAO();
		
		return dao.getAllVideoSegments();
	}
	
	// I am leaving in this S3 code so it can be a LAST RESORT if the VideoSegment is not in the database
	private AmazonS3 s3 = null;
	
	/**
	 * Retrieve all SYSTEM VideoSegments. This code is surprisingly dangerous since there could
	 * be an incredible number of objects in the bucket. Ignoring this for now.
	 */
	List<VideoSegment> systemVideoSegments() throws Exception {
		logger.log("in systemVideoSegments"); // this is where things get fucky
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
			logger.log("attach to S3 succeed");
		}
		ArrayList<VideoSegment> sysVideoSegments = new ArrayList<>();
	    
		String bucket = REAL_BUCKET;
		boolean useTestDB = System.getenv("TESTING") != null;
		if (useTestDB) {
			bucket = TEST_BUCKET;
		}
		
		// retrieve listing of all objects in the designated bucket
		ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
				  .withBucketName("3733quietlyconfident")    // top-level bucket
				  .withPrefix(bucket);            // sub-folder declarations here (i.e., a/b/c)
												  
		
		// request the s3 objects in the s3 bucket 'cs3733wpi/videos' -- change based on your bucket name
		logger.log("process request");
		ListObjectsV2Result result = s3.listObjectsV2(listObjectsRequest);
		logger.log("process request succeeded");
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		
		for (S3ObjectSummary os: objects) {
	      String name = os.getKey();
		  logger.log("S3 found:" + name);

	      // If name ends with slash it is the 'videos/' bucket itself so you skip
	      if (name.endsWith("/")) { continue; }
			
	      S3Object obj = s3.getObject("3733quietlyconfident", name);
	    	
	    	try (S3ObjectInputStream videoSegmentStream = obj.getObjectContent()) {
				Scanner sc = new Scanner(videoSegmentStream);
				String id_video = sc.nextLine(); // idk wtf "name.substring(postSlash+1)" so I'm not gonna fuck with it yet
				String characters = sc.nextLine();
				String transcript = sc.nextLine();
				String url_video = sc.nextLine();
				sc.close();
				
				// just grab name *after* the slash. Note this is a SYSTEM video
				int postSlash = name.indexOf('/');
				sysVideoSegments.add(new VideoSegment(name.substring(postSlash+1), characters, transcript, url_video, true));
			} catch (Exception e) {
				logger.log("Unable to parse contents of " + name);
			}
	    }
		
		return sysVideoSegments;
	}
	
	@Override
	public AllVideoSegmentsResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all videos");

		AllVideoSegmentsResponse response;
		try {			System.out.println("here");

			// get all user defined videos AND system-defined videos.
			// Note that user defined videos override system-defined videos.
			List<VideoSegment> list = getVideoSegments();
			for (VideoSegment c : systemVideoSegments()) {
				if (!list.contains(c)) {
					list.add(c);
				}
			}
			response = new AllVideoSegmentsResponse(list, 200);
		} catch (Exception e) {
			response = new AllVideoSegmentsResponse(400, e.getMessage());
		}
		
		return response;
	}
}
