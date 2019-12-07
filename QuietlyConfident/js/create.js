function processCreateResponse(result) {
	// Can grab any DIV or SPAN HTML element and can then manipulate its
	// contents dynamically via javascript
	console.log("result:" + result);

	refreshPlaylistList();

}

function handleCreateClick(e) {
	var form = document.createForm;

	var data = {};
	data["id_playlist"] = createPlaylistForm.id_playlist.value;

	alert(data);

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
				alert("XHR:" + xhr.responseText);
				processCreateResponse(xhr.responseText);
			} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert (err);
			}
		} else {
			processCreateResponse("N/A");
		}
	};
}
