import { ZRRDraw, getBounds } from "./map.js";

// Initialisation
function initListeners(mymap) {
	console.log("TODO: add more event listeners...");

	document.getElementById("setZrrButton").addEventListener("click", () => {
		setZrr(getBounds());
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

function setZrr(bounds) {
	console.log(bounds);
	document.getElementById("lat1").value = bounds.getSouthWest().lat;
	document.getElementById("lon1").value = bounds.getSouthWest().lng;
	document.getElementById("lat2").value = bounds.getNorthEast().lat;
	document.getElementById("lon2").value = bounds.getNorthEast().lng;
}

// Requêtes asynchrones
async function sendZrr() {
	console.log("send fetch request...");
	if (document.getElementById("lat1").value !== "" 
	&& document.getElementById("lon1").value !== ""
	&& document.getElementById("lat2").value !== "" 
	&& document.getElementById("lon2").value !== "") {
		var corner1 = L.latLng(document.getElementById("lat1").value,
							   document.getElementById("lon1").value),
		corner2 = L.latLng(document.getElementById("lat2").value,
						   document.getElementById("lon2").value),
		bounds = L.latLngBounds(corner1, corner2);
		const body = {
			"latLng1": corner1,
			"latLng2": corner2
		};
		const headers = new Headers();
		headers.append("Content-Type", "application/json");
		headers.append("authentication", localStorage.getItem('token'));
		const requestConfig = {
			method: "POST",
			headers: headers,
			body: JSON.stringify(body),
			mode: "cors"
		};
		const response = await fetch("http://localhost:3376/admin/initZRR", requestConfig)
			.then((response) => {
				if (response.ok) {
					return true;
				} else {
					return false;
				}
			})
		.catch((err) => {
			console.error("In post initZRR: " + err);
		})
		if (response) {
			ZRRDraw(bounds);
		}
	}
}

function setTtl() {
	console.log("TODO: send fetch request...");
}

export { updateLatValue, updateLonValue, updateZoomValue };
export default initListeners;