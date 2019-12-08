function processUploadResponse(result) {
	// Can grab any DIV or SPAN HTML element and can then manipulate its
	// contents dynamically via javascript
	console.log("result:" + result);

	refreshVideoSegmentsList();

}

function handleUploadClick(e) {
	var form = document.createForm;
	
	var data = {};
	data["id_video"] 			= form.id_video.value;
	data["characters"] 			= form.characters.value;
//	data["transcript"] 			= form.transcript.value;
	data["system"] 				= true;
	alert(data["characters"])

	// base64EncodedValue":"data:text/plain;base64,My4xND....."
	var segments = document.createForm.base64Encoding.value.split(',');
	data["base64EncodedValue"] = segments[1];  // skip first one 

	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", create_video_url, true);

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
				processUploadResponse(xhr.responseText);
			} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert (err);
			}
		} else {
			processUploadResponse("N/A");
		}
	};
}