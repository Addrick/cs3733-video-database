function processUnregisterResponse(result)
{
	console.log("result:" + result);
	refreshRemoteList();
}

function requestUnregisterRemote(val)
{
	val = val.slice(4);
	if (confirm("Are you sure you want to unregister the site " + val + "?")) {processUnregisterRemote(val);}
}

function processUnregisterRemote(val)
{
	var data = {};
	data["url"] = val;
	
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", unregister_remote_url, true);
	
	// send the collected data as JSON
	xhr.send(js);
	
	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
			  console.log ("XHR:" + xhr.responseText);
			  processUnregisterResponse(xhr.responseText);
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processUnregisterResponse("N/A");
	  }
	};
}