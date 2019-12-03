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
import http.AllPlaylistsResponse;
import model.Playlist;

/**
 * Eliminated need to work with JSON
 */
public class DeleteVideoSegmentHandler implements RequestHandler<Object,AllPlaylistsResponse> {

	public LambdaLogger logger;

	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "playlists";
	public static final String TEST_BUCKET = "testplaylists";

	/** Load from RDS, if it exists
	 *  
	 * @throws Exception 
	 */
	List<Playlist> getPlaylists() throws Exception {
		logger.log("in getPlaylists");
		PlaylistsDAO dao = new PlaylistsDAO();
		
		return dao.getAllPlaylists();
	}
	
	// I am leaving in this S3 code so it can be a LAST RESORT if the playlist is not in the database
	private AmazonS3 s3 = null;
	
	/**
	 * Retrieve all SYSTEM playlists. This code is surprisingly dangerous since there could
	 * be an incredible number of objects in the bucket. Ignoring this for now.
	 */
	List<Playlist> systemPlaylists() throws Exception {
		logger.log("in systemPlaylists");
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
			logger.log("attach to S3 succeed");
		}
		ArrayList<Playlist> sysPlaylists = new ArrayList<>();
	    
		String bucket = REAL_BUCKET;
		boolean useTestDB = System.getenv("TESTING") != null;
		if (useTestDB) {
			bucket = TEST_BUCKET;
		}
		
		// retrieve listing of all objects in the designated bucket
		ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
				  .withBucketName("3733quietlyconfident")    // top-level bucket
				  .withPrefix(bucket);            // sub-folder declarations here (i.e., a/b/c)
												  
		
		// request the s3 objects in the s3 bucket 'cs3733wpi/playlists' -- change based on your bucket name
		logger.log("process request");
		ListObjectsV2Result result = s3.listObjectsV2(listObjectsRequest);
		logger.log("process request succeeded");
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		
		for (S3ObjectSummary os: objects) {
	      String name = os.getKey();
		  logger.log("S3 found:" + name);

	      // If name ends with slash it is the 'playlists/' bucket itself so you skip
	      if (name.endsWith("/")) { continue; }
			
	      S3Object obj = s3.getObject("3733quietlyconfident", name);
	    	
	    	try (S3ObjectInputStream playlistStream = obj.getObjectContent()) {
				Scanner sc = new Scanner(playlistStream);
				String val = sc.nextLine();
				sc.close();
				
				// just grab name *after* the slash. Note this is a SYSTEM playlist
				int postSlash = name.indexOf('/');
				sysPlaylists.add(new Playlist(name.substring(postSlash+1), Double.valueOf(val), true));
			} catch (Exception e) {
				logger.log("Unable to parse contents of " + name);
			}
	    }
		
		return sysPlaylists;
	}
	
	@Override
	public AllPlaylistsResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all playlists");

		AllPlaylistsResponse response;
		try {			System.out.println("here");

			// get all user defined playlists AND system-defined playlists.
			// Note that user defined playlists override system-defined playlists.
			List<Playlist> list = getPlaylists();
			for (Playlist c : systemPlaylists()) {
				if (!list.contains(c)) {
					list.add(c);
				}
			}
			response = new AllPlaylistsResponse(list, 200);
		} catch (Exception e) {
			response = new AllPlaylistsResponse(400, e.getMessage());
		}
		
		return response;
	}
}
