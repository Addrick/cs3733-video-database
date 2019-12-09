function processCreateResponse(result)
{
	// Can grab any DIV or SPAN HTML element and can then manipulate its
	// contents dynamically via javascript
	console.log("result:" + result);
	refreshRemote refreshRemoteList();
}

function handleAddRemoteLibraryClick()
{
	var remote_url = prompt("Enter Remote Library URL:", "New Remote Library");
	if (remote_url == null || remote_url == "") {}
	else if (remote_url == "playlists" || remote_url == "remote libraries" || remote_url == "videos")
		{alert("Forbidden playlist name. Please select a different name");}
	else {
		var data = {};
		data["remote_url"] = remote_url;
		alert("Added remote site: " + remote_url);
	
		var js = JSON.stringify(data);
		console.log("JS:" + js);
		var xhr = new XMLHttpRequest();
		xhr.open("POST", add_remote_url, true);
	
		// send the collected data as JSON
		xhr.send(js);
	
	
		// This will process results and update HTML as appropriate. 
		xhr.onloadend = function () {
			console.log(xhr);
			console.log(xhr.request);
			if (xhr.readyState == XMLHttpRequest.DONE) {
				if (xhr.status == 200) {
					console.log ("XHR:" + xhr.responseText);
					alert("XHR:" + xhr.responseText);
					processCreateResponse(xhr.responseText);
				} else {
					processCreateResponse("N/A");
				}
			};
		}
	}
}