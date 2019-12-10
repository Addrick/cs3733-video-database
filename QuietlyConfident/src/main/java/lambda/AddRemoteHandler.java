package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.RemoteVideoLibraryDAO;
import http.*;
import model.RemoteLibrary;

/**
 * Eliminated need to work with JSON
 */
public class AddRemoteHandler implements RequestHandler<AddRemoteRequest,AddRemoteResponse> {

	public LambdaLogger logger;

	/** Load from RDS, if it existsa
	 *  
	 * @throws Exception 
	 */
	boolean createRemoteLibrary(String url, String key) throws Exception { 
		if (logger != null) { logger.log("in createRemoteLibrary"); }
		RemoteVideoLibraryDAO dao = new RemoteVideoLibraryDAO();
		
		// check if present
		RemoteLibrary exist = dao.getRemoteLibrary(url);
		RemoteLibrary library = new RemoteLibrary(url, key); 
		if (exist == null) {

			System.out.println("RemoteLibrary didn't already exist");
			return dao.addRemoteLibrary(library);
		} else {

			System.out.println("RemoteLibrary already exists");
			return false;
		}
	}
	
	@Override
	public AddRemoteResponse handleRequest(AddRemoteRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all remote sites");

		AddRemoteResponse response;
		try {
			if (createRemoteLibrary(req.url, req.key))
			{
				response = new AddRemoteResponse(req.url);
			}
			else
			{
				response = new AddRemoteResponse(req.url, 409);
			}
		} catch (Exception e) {
			response = new AddRemoteResponse("Unable to create playlist: " + req.url + "(" + e.getMessage() + ")", 400);
		}
		
		return response;
	}
}
