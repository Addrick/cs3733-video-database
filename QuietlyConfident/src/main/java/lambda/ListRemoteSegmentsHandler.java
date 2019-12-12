package lambda;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.*;
import http.RemoteSegmentsResponse;
import model.VideoSegment;

/**
 * Only expose RDS for the "remotable" constants whose value lies in this range.
 */
public class ListRemoteSegmentsHandler implements RequestHandler<Object,RemoteSegmentsResponse> {

	public LambdaLogger logger;

	@Override
	public RemoteSegmentsResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all remote segments");

		VideoSegment one = new VideoSegment("https://cs3733wpi.s3.amazonaws.com/segments/output1.ogg", "worker", "one to beam up", "https://cs3733wpi.s3.amazonaws.com/segments/output1.ogg", false);
		VideoSegment two = new VideoSegment("https://cs3733wpi.s3.amazonaws.com/segments/output2.ogg", "bones", "he's dead, Jim.", "https://cs3733wpi.s3.amazonaws.com/segments/output2.ogg", false);
		VideoSegment three = new VideoSegment("https://cs3733wpi.s3.amazonaws.com/segments/output3.ogg", "worker", "Kirk, Out.", "https://cs3733wpi.s3.amazonaws.com/segments/output3.ogg", false);
		
		List<VideoSegment> list = new ArrayList<VideoSegment>();
		list.add(one);
		list.add(two);
		list.add(three);
		
		RemoteSegmentsResponse response = new RemoteSegmentsResponse(list, 200);
		return response;
	}
}
