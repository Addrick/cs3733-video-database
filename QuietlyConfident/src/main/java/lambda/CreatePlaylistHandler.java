package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.PlaylistsDAO;
import http.*;
import model.Playlist;

/**
 * Eliminated need to work with JSON
 */
public class CreatePlaylistHandler implements RequestHandler<CreatePlaylistRequest,CreatePlaylistResponse> {

	public LambdaLogger logger;

	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "playlists";
	public static final String TEST_BUCKET = "testplaylists";

	/** Load from RDS, if it exists
	 *  
	 * @throws Exception 
	 */
	boolean createPlaylist(String id_playlist) throws Exception { 
		if (logger != null) { logger.log("in createPlaylist"); }
		PlaylistsDAO dao = new PlaylistsDAO();
		
		// check if present
		Playlist exist = dao.getPlaylist(id_playlist);
		Playlist playlist = new Playlist (id_playlist, 1); // get rid of 1 later when we change database object
		if (exist == null) {
			return dao.addPlaylist(playlist);
		} else {
			return false;
		}
	}
	
	@Override
	public CreatePlaylistResponse handleRequest(CreatePlaylistRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all playlists");

		CreatePlaylistResponse response;
		try {
			if (createPlaylist(req.id_playlist))
			{
				response = new CreatePlaylistResponse(req.id_playlist);
			}
			else
			{
				response = new CreatePlaylistResponse(req.id_playlist, 409);
			}
		} catch (Exception e) {
			response = new CreatePlaylistResponse("Unable to create playlist: " + req.id_playlist + "(" + e.getMessage() + ")", 400);
		}
		
		return response;
	}
}
