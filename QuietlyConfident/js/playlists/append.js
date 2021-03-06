function processAppendVideoSegmentResponse(result) {
	// Can grab any DIV or SPAN HTML element and can then manipulate its
	// contents dynamically via javascript
	console.log("appended :" + result);

	refreshPlaylistList();
}

function processAppendVideoSegment(val) {
	var id_playlist = prompt("Enter name of playlist to add to:", "");
	if (id_playlist == null || id_playlist == "") {}
	else {
		var data = {};
		data["id_playlist"] = id_playlist;

		if(val.slice(0,3) == "vs_"){
			data["id_video"] = val.slice(3);
		}
		else{
			data["id_video"] = val;
		}
		var js = JSON.stringify(data);
		console.log("JS:" + js);
		var xhr = new XMLHttpRequest();
		xhr.open("POST", append_video_url, true);

		// send the collected data as JSON
		xhr.send(js);

		// This will process results and update HTML as appropriate. 
		xhr.onloadend = function () {
			console.log(xhr);
			console.log(xhr.request);
			if (xhr.readyState == XMLHttpRequest.DONE) {
				if (xhr.status == 200) {
					console.log ("XHR:" + xhr.responseText);
					processAppendVideoSegmentResponse(xhr.responseText);
				} else {
					console.log("actual:" + xhr.responseText)
					var js = JSON.parse(xhr.responseText);
					var err = js["error"];
					alert (err);
				}
			} else {
				processAppendVideoSegmentResponse("N/A");
			}
		};
	}
}

function processRemoveVideoSegment(pl, vs) {
		var data = {};
		data["id_playlist"] = pl;
		data["id_video"] = vs;
		
		var js = JSON.stringify(data);
		console.log("JS:" + js);
		var xhr = new XMLHttpRequest();
		xhr.open("POST", remove_video_url, true);

		// send the collected data as JSON
		xhr.send(js);

		// This will process results and update HTML as appropriate. 
		xhr.onloadend = function () {
			console.log(xhr);
			console.log(xhr.request);
			if (xhr.readyState == XMLHttpRequest.DONE) {
				if (xhr.status == 200) {
					console.log ("XHR:" + xhr.responseText);
					processAppendVideoSegmentResponse(xhr.responseText);
				} else {
					console.log("actual:" + xhr.responseText)
					var js = JSON.parse(xhr.responseText);
					var err = js["error"];
					alert (err);
				}
			} else {
				processAppendVideoSegmentResponse("N/A");
			}
		};
	}
