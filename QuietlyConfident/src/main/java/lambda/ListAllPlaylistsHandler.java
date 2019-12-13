package lambda;

import java.util.List;

import com.amazonaws.services.lambda.runtime.*;

import db.PlaylistsDAO;
import http.AllPlaylistsResponse;
import model.*;

/**
 * Eliminated need to work with JSON
 * TODO: The systemPlaylists() class is likely unnecessary (and useless?) and can be removed
 */
public class ListAllPlaylistsHandler implements RequestHandler<Object,AllPlaylistsResponse> {

	public LambdaLogger logger;

	/** Load from RDS, if it exists
	 *  
	 * @throws Exception 
	 */
	List<Playlist> getPlaylists() throws Exception {
		logger.log("in getPlaylists");
		PlaylistsDAO dao = new PlaylistsDAO();
		
		return dao.getAllPlaylists();
	}

	@Override
	public AllPlaylistsResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all playlists");

		AllPlaylistsResponse response;
		try {			System.out.println("here");

			// get all playlists
			List<Playlist> list = getPlaylists();
			response = new AllPlaylistsResponse(list, 200);
		} catch (Exception e) {
			response = new AllPlaylistsResponse(409, e.getMessage());
		}
		
		return response;
	}
}
