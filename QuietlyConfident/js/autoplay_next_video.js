/**
 * lifted from prof heineman's website
 * https://web.cs.wpi.edu/~heineman/cs3733/updated.html
 */

// This is quick and dirty to make this work. You will have your own 
// video segments as stored in a playlist, right?
var allScripts = ["takeThis.mpg.ogg", "Orderedsoft.mpg.ogg",
  "whereDone.mpg.ogg", "engineeringDone.mpg.ogg", "withMe.mpg.ogg",
  "georgeDone.mpg.ogg", "highDone.mpg.ogg", "noManLonger.mpg.ogg"
];

// Oh this is tricky. The outer 'makePlayFunction' returns a function
// that is used (at runtime) to play the given id. In this way we are
// able to create unique callback methods. This is a bit complex!
// The inner function has a parameter 'e' (which reflects its structure
// as an event handler.)
function makePlayFunction(id) {

  // we are returning a function to be the event handler that plays 'id'
  return function(e) {
    document.getElementById(id).play();
  };
}

function registerAll(e) {
  var num = document.playingForm.numberToShow.value;

  var contents = "";
  var i;
  for (i = 0; i < num; i++) {
     var id = "num" + i;

     var vidBlock = "<video id='" + id + "' width=320 height=240";
     if (i == 0) { vidBlock += " controls"; }
     vidBlock += "><source src=\"" + allScripts[i] + "\" type=\"video/ogg\"></video>";

     console.log(vidBlock);
     contents += vidBlock;
  }

  var output = document.getElementById("playingArea");
  output.innerHTML = contents;

  // now that videos are in place, we can locate them and register the 
  // necessary callback functions, which is a tricky use of "closures" in Javascript.
  for (i = 0; i < num-1; i++) {
    var priorVid = document.getElementById("num" + i);
    callBackFunction = makePlayFunction("num" + (i+1));
    priorVid.addEventListener("ended", callBackFunction);
  }
}