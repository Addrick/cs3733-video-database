function processDeletePlaylistResponse(result)
{
  console.log("deleted :" + result);
  refreshPlaylistList();
}

function requestDeletePlaylist(val)
{
	val = val.slice(3);
	if (confirm("Are you sure you want to delete the playlist " + val + "?")) {processDeletePlaylist(val);}
}

function processDeletePlaylist(val)
{
	  var data = {};
	  data["id_playlist"] = val;
	
	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", delete_pl_url, true);
	
	  // send the collected data as JSON
	  xhr.send(js);
	
	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
		  console.log(xhr);
		  console.log(xhr.request);
		  if (xhr.readyState == XMLHttpRequest.DONE) {
			  if (xhr.status == 200) {
				  console.log ("XHR:" + xhr.responseText);
				  processDeletePlaylistResponse(xhr.responseText);
			  } else {
				  console.log("actual:" + xhr.responseText)
				  var js = JSON.parse(xhr.responseText);
				  var err = js["error"];
				  alert (err);
			  }
		  } else {
			  processDeletePlaylistResponse("N/A");
		  }
  };
}