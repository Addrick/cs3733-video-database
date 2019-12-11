function processUploadResponse(result) {
	// Can grab any DIV or SPAN HTML element and can then manipulate its
	// contents dynamically via javascript
	console.log("result:" + result);

	refreshVideoSegmentsList();

}

function showUpload()
{
	input = "<form name=\"createForm\" method=\"post\">" +
			"<input name=\"id_video\" placeholder=\"Title\"/> " +
			"<input name=\"characters\" placeholder=\"Characters\"/> " +
			"<input name=\"text\" placeholder=\"text\"/> " +
			"<input name=\"base64Encoding\" hidden=\"\" value=\"\"/> Select a video in file: " +
			"<input type=\"file\" id=\"video_file\" name=\"video_file\"> " +
			"<input type=\"button\" id=\"createButton\" value=\"Upload Video\" disabled " +
			"onClick=\"handleUploadClick(this)\"></form><br>";
	document.getElementById('videoUpload').innerHTML = input;
	document.getElementById('video_file').addEventListener('change', handleFileSelect, false);
}

function handleUploadClick(e) {
	var form = document.createForm;
	
	var data = {};
	data["id_video"] 			= form.id_video.value;
	data["characters"] 			= form.characters.value;
	data["text"] 			= form.text.value;

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

function getBase64(file)
{
	var reader = new FileReader();
	reader.readAsDataURL(file);

	reader.onload = function ()
	{
		document.createForm.base64Encoding.value = reader.result;
		document.createForm.createButton.disabled = false;
	};
}

// When file is selected, stash base64 value in the encoding field.  
function handleFileSelect(evt)
{
    var files = evt.target.files; 
    if (files[0].size > 10000000000) {  // make as large or small as you need
    	document.createForm.base64Encoding.value = "";
    	alert("File size too large to use:" + files[0].size + " bytes");
    } else {
    	getBase64(files[0]); // request the load (async)
    }
}