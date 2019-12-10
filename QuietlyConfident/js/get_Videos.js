/**
 * Refresh constant list from server
 *
 *    GET list_url
 *    RESPONSE  list of [name, value] constants 
 */
function refreshVideoSegmentsList() {
	var xhr = new XMLHttpRequest();
	xhr.open("GET", list_vid_url, true);
	xhr.send();

	console.log("sent");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log ("XHR:" + xhr.responseText);
			processVideoSegmentListResponse(xhr.responseText);
		} else {
			processVideoSegmentListResponse("N/A");
		}
	};
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processVideoSegmentListResponse(result) {
	console.log("res:" + result);
	// Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
	var js = JSON.parse(result);
	var constList = document.getElementById('videoList');

	var output = "";
	for (var i = 0; i < js.list.length; i++) {
		var constantJson = js.list[i];
		console.log(constantJson);

		var id_video = constantJson["id_video"];
		var characters = constantJson["characters"];
		var transcript = constantJson["transcript"];
		var url_video = constantJson["url_video"];
		output = output + "<div id=\"vs_" + id_video + "\">"
						+ "<b>Video ID: " + id_video + "</b> "
						+ "<button onclick=\"requestDeleteVideoSegment(document.getElementById('vs_" + id_video + "').id)\"><img src='deleteIcon.png'></img></button>"
						+ "<button onclick=\"processAppendVideoSegment(document.getElementById('vs_" + id_video + "').id)\">Add to Playlist...</button><br>"
						+ "Transcript: " + transcript + "<br>"
						+ "<TD><iframe allowfullscreen src = " + url_video + "></iframe> </TD><br><br></div>";
	}

	// Update computation result
	constList.innerHTML = output;
}
