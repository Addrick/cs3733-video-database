package lambda;

import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import db.VideoSegmentsDAO;
import http.*;
import model.VideoSegment;

/**
 * Eliminated need to work with JSON
 */
public class UnmarkVideoSegmentHandler implements RequestHandler<UnmarkVideoSegmentRequest, UnmarkVideoSegmentResponse> {

	public LambdaLogger logger = null;
	
	@Override
	public UnmarkVideoSegmentResponse handleRequest(UnmarkVideoSegmentRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to unmark");

		UnmarkVideoSegmentResponse response = null;
		logger.log(req.toString());

		VideoSegmentsDAO dao = new VideoSegmentsDAO();

		// See how awkward it is to call unmark with an object, when you only
		// have one part of its information?
		VideoSegment video = new VideoSegment(req.id_video, "", "", "", false);
		try {
			if (dao.unmarkVideoSegment(video)) {
				response = new UnmarkVideoSegmentResponse(req.id_video, 200);
			} else {
				response = new UnmarkVideoSegmentResponse(req.id_video, 409, "Unable to unmark video: " + req.id_video + " does not exist or is not stored locally.");
			}
		} catch (Exception e) {
			response = new UnmarkVideoSegmentResponse(req.id_video, 400, "Unable to unmark video: " + req.id_video + "(" + e.getMessage() + ")");
		}

		return response;
	}
}