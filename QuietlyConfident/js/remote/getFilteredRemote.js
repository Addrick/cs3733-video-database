
function filterRemoteList(filter) {
	var xhr = new XMLHttpRequest();
	xhr.open("GET", list_remote_url, true);
	xhr.send();

	console.log("sent");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
//			console.log ("XHR:" + xhr.responseText);
			processSearchRemoteListResponse(xhr.responseText, filter);
		} else {
			processSearchRemoteListResponse("N/A");
		}
	};
}
function processSearchRemoteListResponse(result, filter) {
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
			filterRemoteVideoSegmentsList(url, api, filter);
		}
	}
	remoteVideoList.innerHTML = remoteVideoList.innerHTML + "<br>";

	// Update computation result
//	constList.innerHTML = output;
}
function filterRemoteVideoSegmentsList(url, apikey, filter) {

	var xhr = new XMLHttpRequest();
	xhr.open("GET", url, true);
	xhr.setRequestHeader("x-api-key", apikey);
	xhr.send();

	console.log("sent for remote videos");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
//			console.log ("XHR:" + xhr.responseText);
			processSearchRemoteVideoSegmentListResponse(xhr.responseText, filter);
		} else {
			processSearchRemoteVideoSegmentListResponse("N/A", filter);
		}
	};
}
function processSearchRemoteVideoSegmentListResponse(result, filter) {
	console.log("res:" + result);
	var js = JSON.parse(result);
	var remoteVideoList = document.getElementById('remoteVideoList');

	console.log("text: " + js.segments);
	var output = "<table border='1'; style='table-layout: fixed; width:960px'>";

	for (var i = 0; i < js.segments.length; i++) {
		var constantJson = js.segments[i];

		var text = constantJson["text"];
		var characters = constantJson["character"];
		var url = constantJson["url"];

		if (text.includes(filter) || characters.includes(filter)){

			if (i%3 == 0){output = output + "<tr>"}
			output = output + "<td valign='bottom'; style='word-wrap:break-word; height:300px; width:320px'>"

			output = output + "<div id=\"vs_" + url + "\">"
			+ "<b>Remote Video URL:</b> " + url + "<br> "
			+ "<button onclick=\"processAppendVideoSegment(document.getElementById('vs_" + url + "').id)\">Add to Playlist...</button> <br>"
			+ "<b>Text:</b> " + text + "<br>"
			+ "<b>Characters:</b> " + characters + "<br>"
			+ "<video width='320' height='240' controls src = " + url + "></video> </TD></div>";

			if (i%3 == 2){output = output + "</tr>"}

		}}
	// Update list
	remoteVideoList.innerHTML = remoteVideoList.innerHTML + output;
}
