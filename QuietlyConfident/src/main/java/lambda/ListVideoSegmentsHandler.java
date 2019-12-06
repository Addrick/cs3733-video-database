package lambda;

import java.util.List;

import com.amazonaws.services.lambda.runtime.*;

import db.VideoSegmentsDAO;
import http.AllVideoSegmentsResponse;
import model.VideoSegment;

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

	@Override
	public AllVideoSegmentsResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all videos");

		AllVideoSegmentsResponse response;
		try {			System.out.println("here");

			// get all user defined videos
			List<VideoSegment> list = getVideoSegments();

			response = new AllVideoSegmentsResponse(list, 200);
		} catch (Exception e) {
			response = new AllVideoSegmentsResponse(400, e.getMessage());
		}
		
		return response;
	}
}
