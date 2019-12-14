package lambda;

import java.util.List;

import com.amazonaws.services.lambda.runtime.*;

import db.VideoSegmentsDAO;
import http.*;
import model.VideoSegment;

public class SearchVideoSegmentHandler implements RequestHandler<SearchVideoSegmentRequest,SearchVideoSegmentResponse> {

	public LambdaLogger logger;

	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "videos";
	public static final String TEST_BUCKET = "testvideos";

	/** Load from RDS, if it exists
	 *  
	 * @throws Exception 
	 */
	List<VideoSegment> searchVideoSegment(String criteria) throws Exception {
		logger.log("in getVideoSegments");
		VideoSegmentsDAO dao = new VideoSegmentsDAO();
		
		return dao.searchVideoSegment(criteria);
	}

	@Override
	public SearchVideoSegmentResponse handleRequest(SearchVideoSegmentRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all videos");

		SearchVideoSegmentResponse response;
		try {			System.out.println("here");

			// get all user defined videos
			List<VideoSegment> list = searchVideoSegment(req.criteria);
			
			response = new SearchVideoSegmentResponse(list, 200);
		} catch (Exception e) {
			response = new SearchVideoSegmentResponse(400, e.getMessage());
		}
		
		return response;
	}
}
