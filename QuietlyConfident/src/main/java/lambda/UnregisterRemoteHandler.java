package lambda;

import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import db.*;
import http.*;
import model.*;

/**
 * Eliminated need to work with JSON
 */
public class UnregisterRemoteHandler implements RequestHandler<UnregisterRemoteRequest, UnregisterRemoteResponse> {

	public LambdaLogger logger = null;
	
	@Override
	public UnregisterRemoteResponse handleRequest(UnregisterRemoteRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		UnregisterRemoteResponse response = null;
		logger.log(req.toString());

		RemoteVideoLibraryDAO dao = new RemoteVideoLibraryDAO();

		// See how awkward it is to call delete with an object, when you only
		// have one part of its information?
		RemoteLibrary lib = new RemoteLibrary(req.url, "");
		try {
			if (dao.unregisterRemoteLibrary(lib)) {
				response = new UnregisterRemoteResponse(req.url, 200);
			} else {
				response = new UnregisterRemoteResponse(req.url, 409, "Unable to unregister remote.");
			}
		} catch (Exception e) {
			response = new UnregisterRemoteResponse(req.url, 400, "Unable to unregister remote: " + req.url + "(" + e.getMessage() + ")");
		}

		return response;
	}
}