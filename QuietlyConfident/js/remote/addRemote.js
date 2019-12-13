function processRegisterResponse(result)
{
	// Can grab any DIV or SPAN HTML element and can then manipulate its
	// contents dynamically via javascript
	console.log("result:" + result);
	refreshRemoteList();
}

function addRemote()
{
	var remote_url = prompt("Enter Remote Library URL:", "New Remote Library");
	if (remote_url == null || remote_url == "") {}

	
	else {
		var data = {};
		data["url"] = remote_url;
		alert("Added remote site: " + remote_url);
	
		var js = JSON.stringify(data);
		console.log("JS:" + js);
		var xhr = new XMLHttpRequest();
		xhr.open("POST", register_remote_url, true);
	
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
					processRegisterResponse(xhr.responseText);
				} else {
					processRegisterResponse("N/A");
				}
			};
		}
	}
}