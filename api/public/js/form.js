// Initialisation
function initListeners(mymap) {
	console.log("TODO: add more event listeners...");

	document.getElementById("setZrrButton").addEventListener("click", () => {
		setZrr(L.latLng(document.getElementById("lat").value, document.getElementById("lon").value));
	});

	document.getElementById("sendZrrButton").addEventListener("click", () => {
		sendZrr();
	});

	document.getElementById("setTtlButton").addEventListener("click", () => {
		setTtl();
	});
}

// MàJ des inputs du formulaire
function updateLatValue(lat) {
	document.getElementById("lat").value = lat;
}

function updateLonValue(lng) {
	document.getElementById("lon").value = lng;
}

function updateZoomValue(zoom) {
	document.getElementById("zoom").value = zoom;
}

function setZrr(latLng) {
	if ( document.getElementById("lat1").value === "" 
		 && document.getElementById("lon1").value === "") {
		document.getElementById("lat1").value = latLng.lat;
		document.getElementById("lon1").value = latLng.lng;
	} else if (document.getElementById("lat2").value === "" 
			   && document.getElementById("lon2").value === ""
			   && document.getElementById("lat1") != latLng.lat
			   && document.getElementById("lon1").value != latLng.lng) {
		document.getElementById("lat2").value = latLng.lat;
		document.getElementById("lon2").value = latLng.lng;
	} else {
		document.getElementById("lat1").value = latLng.lat;
		document.getElementById("lon1").value = latLng.lng;
		document.getElementById("lat2").value = "";
		document.getElementById("lon2").value = ""; 
	} 
}

// Requêtes asynchrones
function sendZrr() {
	console.log("send fetch request...");
}

function setTtl() {
	console.log("TODO: send fetch request...");
}

export { updateLatValue, updateLonValue, updateZoomValue };
export default initListeners;