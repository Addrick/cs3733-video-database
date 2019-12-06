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
	boolean removeVS(String id_playlist, String id_video) throws Exception { 
		if (logger != null) { logger.log("in removeVS"); }
		PlaylistsDAO dao = new PlaylistsDAO();
		
		// check if present
		Playlist existPL = dao.getPlaylist(id_playlist);
		Playlist playlist = new Playlist (id_playlist, 1); // get rid of 1 later when we change database object
		VideoSegment video = new VideoSegment (id_video, "", "", "", false);
		if (existPL == null) {
			System.out.println("Playlist does not exist");
			return false;
		} else {
			System.out.println("Playlist exists. Attempting to remove VS...");
			return dao.removeFromPlaylist(playlist, video);
		}
	}
	
	@Override
	public RemoveVideoSegmentResponse handleRequest(RemoveVideoSegmentRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to remove video segment");

		RemoveVideoSegmentResponse response;
		try {
			if (removeVS(req.id_playlist, req.id_video))
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
