// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://kv30lpjbbe.execute-api.us-east-2.amazonaws.com/somethingepic/"; 

var add_url    			= base_url + "calculator";   			// POST
var create_video_url 	= base_url + "upload_video";    		// POST
var create_pl_url 		= base_url + "create_playlist"; 		// POST
var delete_video_url 	= base_url + "delete_video";     		// POST because of CORS
var delete_pl_url 		= base_url + "delete_playlist";     	// POST because of CORS
var list_pl_url   	    = base_url + "list_playlists";    		// GET
var list_vid_url   	    = base_url + "list_video_segments"; 	// GET
var search_videos_url 	= base_url + "search_video_segments" 	// GET
var rem_video_url		= base_url + "remove_video_segment"		// POST because of CORS
var append_video_url	= base_url + "append_video_segment"		// POST
var list_remote_url   	= base_url + "list_remote";    		// GET
var add_remote_url   	= base_url + "add_remote";    		// POST


//generated from aws. BAD IDEA to encode here, but just getting something done.
var remote_url = "https://rct351a2w3.execute-api.us-east-1.amazonaws.com/secure/";

var search_remote_url = remote_url + "remote";

var apikey = "I14G0D8EJn4Q44b8dFhtb6CcdIraLflm9dpcyXAX";

// test directly 
// curl -X POST -H "x-api-key: theKey" -H "Content-Type: application/json" -d '{"key":"val"}' https://[api-id].execute-api.[region].amazonaws.com

// NEWLY ADDED to show how one might write remote video segment API
// used same API key from above. 
var sample_video_segments_url = "https://g75v8iurq5.execute-api.us-east-1.amazonaws.com/RemoteSite/publicsegments";

