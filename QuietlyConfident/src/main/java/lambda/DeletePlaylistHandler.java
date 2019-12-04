package lambda;

import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import db.PlaylistsDAO;
import http.*;
import model.Playlist;

/**
 * Eliminated need to work with JSON
 */
public class DeletePlaylistHandler implements RequestHandler<DeletePlaylistRequest, DeletePlaylistResponse> {

	public LambdaLogger logger = null;
	
	@Override
	public DeletePlaylistResponse handleRequest(DeletePlaylistRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeletePlaylistResponse response = null;
		logger.log(req.toString());

		PlaylistsDAO dao = new PlaylistsDAO();

		// See how awkward it is to call delete with an object, when you only
		// have one part of its information?
		Playlist playlist = new Playlist(req.id_playlist, 0);
		try {
			if (dao.deletePlaylist(playlist)) {
				response = new DeletePlaylistResponse(req.id_playlist, 200);
			} else {
				response = new DeletePlaylistResponse(req.id_playlist, 409, "Unable to delete playlist.");
			}
		} catch (Exception e) {
			response = new DeletePlaylistResponse(req.id_playlist, 400, "Unable to delete playlist: " + req.id_playlist + "(" + e.getMessage() + ")");
		}

		return response;
	}
}