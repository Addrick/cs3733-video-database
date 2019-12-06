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
public class DeleteVideoSegmentHandler implements RequestHandler<DeleteVideoSegmentRequest, DeleteVideoSegmentResponse> {

	public LambdaLogger logger = null;
	
	@Override
	public DeleteVideoSegmentResponse handleRequest(DeleteVideoSegmentRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteVideoSegmentResponse response = null;
		logger.log(req.toString());

		VideoSegmentsDAO dao = new VideoSegmentsDAO();

		// See how awkward it is to call delete with an object, when you only
		// have one part of its information?
		VideoSegment video = new VideoSegment(req.id_video, "", "", "", false);
		try {
			if (dao.deleteVideoSegment(video)) {
				response = new DeleteVideoSegmentResponse(req.id_video, 200);
			} else {
				response = new DeleteVideoSegmentResponse(req.id_video, 409, "Unable to delete video.");
			}
		} catch (Exception e) {
			response = new DeleteVideoSegmentResponse(req.id_video, 400, "Unable to delete video: " + req.id_video + "(" + e.getMessage() + ")");
		}

		return response;
	}
}