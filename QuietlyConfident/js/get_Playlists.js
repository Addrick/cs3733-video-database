/**
 * Refresh constant list from server
 *
 *    GET list_pl
 *    RESPONSE  list of [name, value] constants 
 */
function refreshPlaylistList() {
	var xhr = new XMLHttpRequest();
	xhr.open("GET", list_pl, true);
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
		output = output + "<div id=\"const" + id_playlist + "\"><b>" + "Playlist ID: " + id_playlist + ":</b> = " + "Playlist Order: " + order_playlist + "(<a href='javaScript:requestDelete(\"" + id_playlist + "\")'><img src='deleteIcon.png'></img></a>) <br></div>";
	}

	// Update computation result
	constList.innerHTML = output;
}

