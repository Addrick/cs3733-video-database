/**
 * Refresh constant list from server
 *
 *    GET list_pl_url
 *    RESPONSE  list of [name, value] constants 
 */
function refreshRemoteList() {
	var xhr = new XMLHttpRequest();
	xhr.open("GET", list_remote_url, true);
	xhr.send();

	console.log("sent");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log ("XHR:" + xhr.responseText);
			processRemoteListResponse(xhr.responseText);
		} else {
			processRemoteListResponse("N/A");
		}
	};
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processRemoteListResponse(result) {
	console.log("res:" + result);
	// Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
	var js = JSON.parse(result);
	var constList = document.getElementById('remoteVideoSegmentsList');

	var output = "";
	for (var i = 0; i < js.list.length; i++) {
		var constantJson = js.list[i];
		console.log(constantJson);

		var id_playlist = constantJson["url"];
		output = output + "<div id=\"pl_" + id_playlist + "\"><b>" + "Playlist ID: " + id_playlist + ":</b> = " + " <button onclick=\"requestDeleteRemote(document.getElementById('pl_" + id_playlist + "').id)\"><img src='deleteIcon.png'></img></a> <br></div>";
	}

	// Update computation result
	constList.innerHTML = output;
}

