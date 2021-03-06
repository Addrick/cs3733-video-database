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
var search_videos_url 	= base_url + "search_video_segments"; 	// GET
var remove_video_url	= base_url + "remove_video_segment";	// POST because of CORS
var append_video_url	= base_url + "append_video_segment";	// POST
var play_playlist_url	= base_url + "play_playlist";			// POST
var mark_video_url		= base_url + "mark_video";				// POST
var unmark_video_url	= base_url + "unmark_video";			// POST
var list_remote_url   	= base_url + "list_remote";    			// GET
var register_remote_url	= base_url + "register_remote";    		// POST
var unregister_remote_url=base_url + "unregister_remote";    	// POST
var list_public_url		= base_url + "list_public";				// POST


//generated from aws. BAD IDEA to encode here, but just getting something done.
var remote_url = "https://rct351a2w3.execute-api.us-east-1.amazonaws.com/secure/";

var search_remote_url = remote_url + "remote";


// test directly 
// curl -X POST -H "x-api-key: theKey" -H "Content-Type: application/json" -d '{"key":"val"}' https://[api-id].execute-api.[region].amazonaws.com
