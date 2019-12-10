function unmarkVSResponse(result)
{
  console.log("unmarked :" + result);
  refreshVideoSegmentsList();
}

function unmarkVS(val) {
	var data = {};
	data["id_video"] = val.slice(3);

	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", unmark_video_url, true);
	
	// send the collected data as JSON
	xhr.send(js);
		
	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
			  console.log(xhr);
			  console.log(xhr.request);
			  if (xhr.readyState == XMLHttpRequest.DONE) {
				  if (xhr.status == 200) {
					  console.log ("XHR:" + xhr.responseText);
					  unmarkVSResponse(xhr.responseText);
				  } else {
					  console.log("actual:" + xhr.responseText)
					  var js = JSON.parse(xhr.responseText);
					  var err = js["error"];
					  alert (err);
				  }
			  } else {
				  unmarkVSResponse("N/A");
			  }
		  };
	}

