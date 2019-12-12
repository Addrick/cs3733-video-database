






function refreshRemoteVideoSegmentsList(url, apikey) {

	  var xhr = new XMLHttpRequest();
	  xhr.open("GET", url, true);
	  xhr.setRequestHeader("x-api-key", apikey);
	  xhr.send();
	      
	  console.log("sent for remote videos");
	   
		// This will process results and update HTML as appropriate. 
		xhr.onloadend = function () {
			if (xhr.readyState == XMLHttpRequest.DONE) {
//				console.log ("XHR:" + xhr.responseText);
				processRemoteVideoSegmentListResponse(xhr.responseText);
			} else {
				processRemoteVideoSegmentListResponse("N/A");
			}
		};
	}


/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processRemoteVideoSegmentListResponse(result) {
	console.log("res:" + result);
	// Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
	var js = JSON.parse(result);
	var remoteVideoList = document.getElementById('remoteVideoList');

	var output = "";
	console.log("text: " + js.segments);

	for (var i = 0; i < js.segments.length; i++) {
		var constantJson = js.segments[i];

		var text = constantJson["text"];
		var characters = constantJson["character"];
		var url = constantJson["url"];
		
		output = output + "<div id=\"vs_" + url + "\">"
						+ "<b>RemoteVideo ID: " + url + "</b> "
						+ "<button onclick=\"processAppendRemoteVideoSegment(document.getElementById('vs_" + url + "').id)\">Add to Playlist...</button> <br>"
						+ "text: " + text + "<br>"
						+ "characters: " + characters + "<br>"
						+ "<TD><iframe allowfullscreen src = " + url + "></iframe> </TD><br><br></div>";
	}
	// Update computation result
	console.log("Output: " + output);

	remoteVideoList.innerHTML = output;
}
