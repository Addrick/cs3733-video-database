package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.PlaylistsDAO;
import http.*;
import model.*;

public class RemoveVideoSegmentHandler implements RequestHandler<RemoveVideoSegmentRequest,RemoveVideoSegmentResponse> {

	public LambdaLogger logger;

	/** Load from RDS, if it exists
	 *  
	 * @throws Exception 
	 */
	boolean removeVS(String id_playlist, String id_video, int num) throws Exception { 
		if (logger != null) { logger.log("in removeVS"); }
		PlaylistsDAO dao = new PlaylistsDAO();
		
		// check if present
		Playlist existPL = dao.getPlaylist(id_playlist);
		VideoSegment video = new VideoSegment (id_video, "", "", "", false);
		if (existPL == null) {
			System.out.println("Playlist does not exist");
			return false;
		} else {
			System.out.println("Playlist exists. Attempting to remove VS...");
			return dao.removeFromPlaylist(existPL, video, num);
		}
	}
	
	@Override
	public RemoveVideoSegmentResponse handleRequest(RemoveVideoSegmentRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to remove video segment");

		RemoveVideoSegmentResponse response;
		try {
			if (removeVS(req.id_playlist, req.id_video, req.num))
			{
				response = new RemoveVideoSegmentResponse("playlist: " + req.id_playlist + "  video: " + req.id_video);
			}
			else
			{
				response = new RemoveVideoSegmentResponse("playlist: " + req.id_playlist + "  video: " + req.id_video, 409);
			}
		} catch (Exception e) {
			response = new RemoveVideoSegmentResponse("Unable to remove video: " + req.id_video + " (" + e.getMessage() + ")", 400);
		}
		
		return response;
	}
}
