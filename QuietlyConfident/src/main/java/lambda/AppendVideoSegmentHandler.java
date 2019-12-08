package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.PlaylistsDAO;
import http.*;
import model.*;

public class AppendVideoSegmentHandler implements RequestHandler<AppendVideoSegmentRequest,AppendVideoSegmentResponse> {

	public LambdaLogger logger;

	/** Load from RDS, if it exists
	 *  
	 * @throws Exception 
	 */
	boolean appendVS(String id_playlist, String id_video) throws Exception { 
		if (logger != null) { logger.log("in appendVS"); }
		PlaylistsDAO dao = new PlaylistsDAO();
		
		// check if present
		Playlist existPL = dao.getPlaylist(id_playlist);
		VideoSegment video = new VideoSegment (id_video, "", "", "", false);
		if (existPL == null) {
			System.out.println("Playlist does not exist");
			return false;
		} else {
			System.out.println("Playlist exists. Attempting to append VS...");
			return dao.appendToPlaylist(existPL, video);
		}
	}
	
	@Override
	public AppendVideoSegmentResponse handleRequest(AppendVideoSegmentRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to append video segment");

		AppendVideoSegmentResponse response;
		try {
			if (appendVS(req.id_playlist, req.id_video))
			{
				response = new AppendVideoSegmentResponse("playlist: " + req.id_playlist + "  video: " + req.id_video);
			}
			else
			{
				response = new AppendVideoSegmentResponse("playlist: " + req.id_playlist + "  video: " + req.id_video, 409);
			}
		} catch (Exception e) {
			response = new AppendVideoSegmentResponse("Unable to append video: " + req.id_video + " (" + e.getMessage() + ")", 400);
		}
		
		return response;
	}
}
