/**
 * Refresh constant list from server
 *
 *    GET list_pl_url
 *    RESPONSE  list of [name, value] constants 
 */
function refreshRemoteList() {
	var xhr = new XMLHttpRequest();
	xhr.open("GET", list_remote_url, true);
	xhr.send();

	console.log("sent");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
//			console.log ("XHR:" + xhr.responseText);
			processRemoteListResponse(xhr.responseText);
		} else {
			processRemoteListResponse("N/A");
		}
	};
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processRemoteListResponse(result) {
	console.log("res:" + result);
	
	// Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
	var js = JSON.parse(result);
	var remoteVideoList = document.getElementById('remoteVideoList');
	remoteVideoList.innerHTML = "";

	for (var i = 0; i < js.list.length; i++) {
		var constantJson = js.list[i];
//		console.log(constantJson);

		var urlapi = constantJson["url"];

		var q = urlapi.indexOf("?apikey=");
		if (q == -1) {
			alert("Malformed Remote Library URL or API key");
			remoteVideoList.innerHTML = "<b>Invalid Remote Site:</b> " + urlapi + "(<a href='javaScript:requestUnregisterRemote(\"" + urlapi + "\")'><img src='deleteIcon.png'></img></a>) <br></div>";
		} else {
			var url = urlapi.substring(0, q);
			var api = urlapi.substring(q+8);
			console.log(url);
			remoteVideoList.innerHTML = remoteVideoList.innerHTML + "<b>Remote Site:</b> " + url + "(<a href='javaScript:requestUnregisterRemote(\"" + urlapi + "\")'><img src='deleteIcon.png'></img></a>) <br></div>";
			refreshRemoteVideoSegmentsList(url, api);
		}
	}
	remoteVideoList.innerHTML = remoteVideoList.innerHTML + "<br>";

	// Update computation result
//	constList.innerHTML = output;
}

function refreshRemoteVideoSegmentsList(url, apikey) {

	var xhr = new XMLHttpRequest();
	xhr.open("GET", url, true);
	xhr.setRequestHeader("x-api-key", apikey);
	xhr.send();

	console.log("sent for remote videos");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
//			console.log ("XHR:" + xhr.responseText);
			processRemoteVideoSegmentListResponse(xhr.responseText);
		} else {
			processRemoteVideoSegmentListResponse("N/A");
		}
	};
}


/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processRemoteVideoSegmentListResponse(result) {
	console.log("res:" + result);
	// Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
	var js = JSON.parse(result);
	var remoteVideoList = document.getElementById('remoteVideoList');

	console.log("text: " + js.segments);
	var output = "<table border='1'; style='table-layout: fixed; width:960px'>";

	for (var i = 0; i < js.segments.length; i++) {
		var constantJson = js.segments[i];

		var text = constantJson["text"];
		var characters = constantJson["character"];
		var url = constantJson["url"];
		
		if (i%3 == 0){output = output + "<tr>"}
		output = output + "<td valign='bottom'; style='word-wrap:break-word; height:300px; width:320px'>"

		output = output + "<div id=\"vs_" + url + "\">"
		+ "<b>Remote Video URL:</b> " + url + "<br> "
		+ "<button onclick=\"processAppendVideoSegment(document.getElementById('vs_" + url + "').id)\">Add to Playlist...</button> <br>"
		+ "<b>Text:</b> " + text + "<br>"
		+ "<b>Characters:</b> " + characters + "<br>"
		+ "<video width='320' height='240' controls src = " + url + "></video> </TD></div>";

		if (i%3 == 2){output = output + "</tr>"}

	}
	// Update list
	remoteVideoList.innerHTML = remoteVideoList.innerHTML + output;
}

function processFilteredRemoteVideoSegmentListResponse(result, filter) {
	console.log("res:" + result);
	// Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
	var js = JSON.parse(result);
	var remoteVideoList = document.getElementById('remoteVideoList');

	console.log("text: " + js.segments);
	var output = "<table border='1'; style='table-layout: fixed; width:960px'>";

	for (var i = 0; i < js.segments.length; i++) {
		var constantJson = js.segments[i];

		var text = constantJson["text"];
		var characters = constantJson["character"];
		var url = constantJson["url"];
		
		if (i%3 == 0){output = output + "<tr>"}
		output = output + "<td valign='bottom'; style='word-wrap:break-word; height:300px; width:320px'>"

		output = output + "<div id=\"vs_" + url + "\">"
		+ "<b>Remote Video URL:</b> " + url + "<br> "
		+ "<button onclick=\"processAppendVideoSegment(document.getElementById('vs_" + url + "').id)\">Add to Playlist...</button> <br>"
		+ "<b>Text:</b> " + text + "<br>"
		+ "<b>Characters:</b> " + characters + "<br>"
		+ "<video width='320' height='240' controls src = " + url + "></video> </TD></div>";

		if (i%3 == 2){output = output + "</tr>"}

	}
	// Update list
	remoteVideoList.innerHTML = remoteVideoList.innerHTML + output;
}

function handleAddRemoteClick()
{
	var url = prompt("Enter Remote Library URL:", "New Remote Site");
	if (url == null || url == "") {}
	var q = url.indexOf("?apikey=");
	if (q == -1) {
		alert("Malformed Remote Library URL or API key");}
	else {
		var data = {};
		data["url"] = url;
		alert("Adding remote site: " + url);

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
					processUnregisterResponse(xhr.responseText);
					remoteVideoList.innerHTML = "";
					refreshRemoteList();
				} else {
					processCreateResponse("N/A");
				}
			};
		}
	}
}
