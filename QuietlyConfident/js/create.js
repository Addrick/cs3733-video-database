function processCreateResponse(result)
{
	// Can grab any DIV or SPAN HTML element and can then manipulate its
	// contents dynamically via javascript
	console.log("result:" + result);
	refreshPlaylistList();
}

function handleCreateClick()
{
	var id_playlist = prompt("Enter Playlist Name:", "New Playlist");
	if (id_playlist == null || id_playlist == "") {}
	else if (id_playlist == "playlists" || id_playlist == "remote libraries" || id_playlist == "videos")
		{alert("Forbidden playlist name. Please select a different name");}
	else {
		var data = {};
		data["id_playlist"] = id_playlist;
		alert("Created playlist: " + id_playlist);
	
		var js = JSON.stringify(data);
		console.log("JS:" + js);
		var xhr = new XMLHttpRequest();
		xhr.open("POST", create_pl_url, true);
	
		// send the collected data as JSON
		xhr.send(js);
	
	
		// This will process results and update HTML as appropriate. 
		xhr.onloadend = function () {
			console.log(xhr);
			console.log(xhr.request);
			if (xhr.readyState == XMLHttpRequest.DONE) {
				if (xhr.status == 200) {
					console.log ("XHR:" + xhr.responseText);
					processCreateResponse(xhr.responseText);
				} else {
					processCreateResponse("N/A");
				}
			};
		}
	}
}