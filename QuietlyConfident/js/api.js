// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://kv30lpjbbe.execute-api.us-east-2.amazonaws.com/somethingepic/"; 

var add_url    = base_url + "calculator";   // POST
var create_url = base_url + "upload_video";    // POST
var delete_url = base_url + "constant";     // POST because of CORS
var list_pl   = base_url + "list_playlists";    // GET
var list_vid   = base_url + "list_video_segments";    // GET
