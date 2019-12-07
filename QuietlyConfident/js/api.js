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
var search_videos 		= base_url + "search_video_segments" 	// GET
var rem_video			= base_url + "remove_video_segment"		// POST because of CORS
var append_video		= base_url + "append_video_segment"		// POST


