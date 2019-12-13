/**
 * Refresh constant list from server
 *
 *    GET list_pl_url
 *    RESPONSE  list of [name, value] constants 
 */
function refreshPlaylistList() {
	var xhr = new XMLHttpRequest();
	xhr.open("GET", list_pl_url, true);
	xhr.send();

	console.log("sent");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log ("XHR:" + xhr.responseText);
			processPlaylistListResponse(xhr.responseText);
		} else {
			processPlaylistListResponse("N/A");
		}
	};
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processPlaylistListResponse(result) {
	console.log("res:" + result);
	// Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
	var js = JSON.parse(result);
	var constList = document.getElementById('playlistList');

	var output = "";
	for (var i = 0; i < js.list.length; i++) {
		var constantJson = js.list[i];
		console.log(constantJson);

		var id_playlist = constantJson["id_playlist"];
		var order_playlist = constantJson["order_playlist"];
		var s = "";
		if(order_playlist != 1)
			s = "s";
		var videos = constantJson["videos"];
		var vidlist = "<ol>";
		for(var j = 0; j < videos.length; j++) {
			vidlist = vidlist + "<li>" + "<TD><iframe allowfullscreen src = " + videos[j] + "></iframe> </TD><br><br></div>" + "</li>"
			+ "<button onclick=\"processRemoveVideoSegment('" + videos[j] + "')\">Remove from Playlist</button> ";

		}
		vidlist = vidlist + "</ol>";
		output = output + "<div id=\"pl_" + id_playlist + "\"><b>" + "Playlist ID: " + id_playlist + "</b> (" + order_playlist + " video" + s + ") " +
				"<button onclick=\"requestDeletePlaylist(document.getElementById('pl_" + id_playlist + "').id)\"><img src='deleteIcon.png'></img></button>" +
		vidlist + "</div>";
	}

	// Update computation result
	constList.innerHTML = output;
}

