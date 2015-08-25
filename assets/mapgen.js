var mapVerticalMargin 	= 100;
var pin;
var pinXOffset = 8;
var pinYOffset = 32;
var mapContainer;
var pinCssText;

function r(labelXCoor, labelYCoor, xCoor, yCoor, squeezeText) {
	this.labelXCoor		= labelXCoor;
	this.labelYCoor		= labelYCoor;
	this.xCoor			= xCoor;
	this.yCoor			= yCoor;
	this.squeezeText	= squeezeText;
}



function l(labelXCoor, labelYCoor, xCoor, yCoor, landmarkLabel, landmarkName) {
	this.labelXCoor		= labelXCoor;
	this.labelYCoor		= labelYCoor;
	this.xCoor			= xCoor;
	this.yCoor			= yCoor;
	this.landmarkLabel	= landmarkLabel;
	this.landmarkName	= landmarkName;
}

function scrollToRoom(roomId) {
	var room = rooms[roomId];
	window.scroll(room.xCoor-window.innerWidth/2, room.yCoor+mapVerticalMargin-window.innerHeight/2-10);
}

function scrollToLandmark(landmarkId) {
	var landmark = landmarks[landmarkId];
	window.scroll(landmark.xCoor-window.innerWidth/2, landmark.yCoor+mapVerticalMargin-window.innerHeight/2-10);
}


function hidePin() {
	pin.style.display = "none";
}

function positionPinAtRoom(roomId) {
	var room = rooms[roomId];
	if (room) {
		var cssText = "display:inline;left:"
							+ (room.xCoor-pinXOffset).toString()
							+ "px;top:"
							+ (room.yCoor-pinYOffset).toString()
							+ "px;";
		pin.style.cssText = cssText;
		pinCssText = cssText;
	}
}


function prepareMap() {
	mapContainer			= document.getElementById('container');
	var m 						= mapVerticalMargin.toString();
	mapContainer.style.margin	= m + 'px 0 ' + m + 'px 0';

	var elementGroup = document.createDocumentFragment();
		
	for (var roomId in rooms) {
		var roomLabel 			= document.createElement('div');
		var room				= rooms[roomId];
		if (room.squeezeText==0) {
			roomLabel.className = 'room_label';
		} else {
			roomLabel.className	= 'room_label_s';
		}			
		roomLabel.style.left 	= room.labelXCoor.toString() + 'px';
		roomLabel.style.top		= room.labelYCoor.toString() + 'px';
		roomLabel.innerHTML		= roomId;
		roomLabel.onclick		= function() { onIdClick(this.innerHTML); };
		elementGroup.appendChild(roomLabel);
	}
	
	for (var landmarkId in landmarks) {
		var landmarkLabel			= document.createElement('div');
		var landmark				= landmarks[landmarkId];
		landmarkLabel.style.left	= landmark.labelXCoor.toString() + 'px';
		landmarkLabel.style.top		= landmark.labelYCoor.toString() + 'px';
		landmarkLabel.innerHTML		= landmark.landmarkLabel;
		landmarkLabel.className 	= 'room_label';
		landmarkLabel.id			= landmarkId;
		landmarkLabel.onclick		= function() { onLandmarkClick(this.id); };
		elementGroup.appendChild(landmarkLabel);
	}
	
	pin 		= document.createElement('img');
	pin.src 	= 'images/indicator.svg';
	pin.height 	= 21;
	pin.width 	= 15;
	pin.id		= "pin";
	elementGroup.appendChild(pin);
	
	mapContainer.appendChild(elementGroup);
	
	if (rooms[defaultRoomId]) {
		positionPinAtRoom(defaultRoomId);
		scrollToRoom(defaultRoomId);
	} else {
		hidePin();
		scrollToLandmark("LIFT");
	}

}

function onIdClick(roomId) {
	positionPinAtRoom(roomId);
	window.jsInterface.displayRoomDescription(floorLevel,roomId);
}

function onLandmarkClick(landmarkId) {
	var landmark = landmarks[landmarkId];
	if (landmark!=null) {
		var cssText = "display:inline;left:"
							+ (landmark.xCoor-pinXOffset).toString()
							+ "px;top:"
							+ (landmark.yCoor-pinYOffset).toString()
							+ "px;";
		pin.style.cssText = cssText;
		pinCssText = cssText;
		window.jsInterface.displayLandmarkDescription(floorLevel,landmark.landmarkName);
	}
}

function forcePinRedraw() {
	pin.style.cssText = pinCssText;
}