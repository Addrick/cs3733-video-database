package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import db.RemoteVideoLibraryDAO;
import http.AllRemoteResponse;
import model.RemoteLibrary;

/**
 * Eliminated need to work with JSON
 * TODO: The systemPlaylists() class is likely unnecessary (and useless?) and can be removed
 */
public class ListAllRemoteHandler implements RequestHandler<Object,AllRemoteResponse> {

	public LambdaLogger logger;

	List<RemoteLibrary> getRemoteLibraries() throws Exception {
		logger.log("in getRemote");
		RemoteVideoLibraryDAO dao = new RemoteVideoLibraryDAO();
		
		return dao.getAllRemoteLibraries();
	}

	@Override
	public AllRemoteResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all remote sites");

		AllRemoteResponse response;
		try {			System.out.println("here");

			// get all user defined playlists AND system-defined playlists.
			// Note that user defined playlists override system-defined playlists.
			List<RemoteLibrary> list = getRemoteLibraries();

			response = new AllRemoteResponse(list, 200);
			logger.log(list.toString());

		} catch (Exception e) {
			response = new AllRemoteResponse(409, e.getMessage());
			System.out.println(response);
		}
		
		return response;
	}
}
