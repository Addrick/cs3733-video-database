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
		var vidlist = "<Table>";
		var removevid = "";
		for(var j = 0; j < videos.length; j++) {
			if (j%3 == 0){vidlist = vidlist + "<tr>"}
			vidlist = vidlist + "<TD>" + "<video width='320' height='240' controls src = " + videos[j] + ">type='video/ogg'</video><br>"
			+ "<button style='width:320px' onclick=\"processRemoveVideoSegment('" + id_playlist + "', '" + videos[j] + "')\">Remove from Playlist</button> " + "</TD>";
			if (j%3 == 2){vidlist = vidlist + "</tr>"}

		}
		output = output + "<div id=\"pl_" + id_playlist + "\">" + "<b>Playlist ID:</b> " + id_playlist + " (" + order_playlist + " video" + s + ") " +
				"<button onclick=\"requestDeletePlaylist(document.getElementById('pl_" + id_playlist + "').id)\"><img src='deleteIcon.png'></img></button><br>" +
		vidlist + removevid + "</Table></div>";
	}

	// Update computation result
	constList.innerHTML = output;
}

//<li>+ "</li>"
//<ol> 		vidlist = vidlist + "</ol>";
