// Refreshes list of video segments in the page view.
// This function passes in a value to signify the user. 0 for participant, 1 for administrator
function refreshVideoSegmentsList() {
	if(document.title == "Admin: Star Trek Video DB") {var user = 1;}
	else {var user = 0;}
	var xhr = new XMLHttpRequest();
	xhr.open("GET", list_vid_url, true);
	xhr.send();

	console.log("sent2");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
//			console.log ("XHR:" + xhr.responseText);
			processVideoSegmentListResponse(xhr.responseText, user);
		} else {
			processVideoSegmentListResponse("N/A", user);
		}
	};
}

function handleSearch() {
	if(document.title == "Administrator Landing Page") {var user = 1;}
	else {var user = 0;}
	var criteria = prompt("Filter local and remote video list by characters or transcript text (queries are case-sensitive):", "");
	if(criteria == null || criteria == "") {}
	else
	{
		filterRemoteList(criteria);
		var data = {};
		data["criteria"] = criteria;
		var js = JSON.stringify(data);
		console.log("JS:" + js);
		var xhr = new XMLHttpRequest();
		xhr.open("POST", search_videos_url, true);
		xhr.send(js);
	
		// This will process results and update HTML as appropriate. 
		xhr.onloadend = function () {
			if (xhr.readyState == XMLHttpRequest.DONE) {
				console.log ("XHR:" + xhr.responseText);
				processVideoSegmentListResponse(xhr.responseText, user);
			} else {
				processVideoSegmentListResponse("N/A", user);
			}
		};
	}
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processVideoSegmentListResponse(result, user) {
	console.log("res:" + result);
	// Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
	var js = JSON.parse(result);
	var videoList = document.getElementById('videoList');

	var output = "<table style='table-layout: fixed'>";
	for (var i = 0; i < js.list.length; i++) {
		var constantJson = js.list[i];

		var id_video = constantJson["id_video"];
		var text = constantJson["text"];
		var characters = constantJson["characters"];
		var url = constantJson["url"];
		var system = constantJson["system"];
		var visible = constantJson["visible"];
		var markButton = " ";
		var systemIcon = "<br>";
		
		if (i%3 == 0){output = output + "<tr>"}
		output = output + "<td valign='bottom'; style='word-wrap:break-word; height:300px; width:320px'>"
		if(user)
		{
			if(system){
				systemIcon = "<span title=\"This video is stored in the local bucket\">&#128193</span>" + systemIcon;
				if(visible){
					markButton = "<select onchange=\"markVS(document.getElementById('vs_" + id_video + "').id)\">" +
								 "<option>&#127758 Public</option>" +
								 "<option>&#128274 Private</option>" +
								 "</select>" + markButton;}
				else{
					markButton = "<select onchange=\"unmarkVS(document.getElementById('vs_" + id_video + "').id)\">" +
								 "<option>&#128274 Private</option>" +
								 "<option>&#127758 Public</option>" +
								 "</select>" + markButton;}}
			else{systemIcon = "<span title=\"This video is stored in a remote library\">&#9729</span>" + systemIcon;}
		}
		output = output + "<div id=\"vs_" + id_video + "\">"
						+ "<b>Video ID: " + id_video + "</b><br> "
						+ "<button onclick=\"requestDeleteVideoSegment(document.getElementById('vs_" + id_video + "').id)\"><img src='deleteIcon.png'></img></button> "
						+ "<button onclick=\"processAppendVideoSegment('" + url + "')\">Add to Playlist...</button> "
						+ markButton
						+ systemIcon
						+ "<b>Text:</b> " + text + "<br>"
						+ "<b>Characters:</b> " + characters + "<br>"
						+ "<video width='320' height='240' controls src = " + url + "></video> <br><br></div>";
		if (i%3 == 2){output = output + "</tr>"}

	}
	// Update computation result
	videoList.innerHTML = output + "</Table>";
}
