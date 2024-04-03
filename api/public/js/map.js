import apiBase from "./api-base.js";

// initialisation de la map
const lat = 45.782, lng = 4.8656, zoom = 19;

let mymap = L.map('map', {
    center: [lat, lng],
    zoom: zoom
});

let groupMarker = [];


// Initialisation de la map
function initMap() {
	// Création d'un "tile layer" (permet l'affichage sur la carte)
	L.tileLayer('https://api.mapbox.com/v4/mapbox.satellite/{z}/{x}/{y}@2x.jpg90?access_token=pk.eyJ1IjoieGFkZXMxMDExNCIsImEiOiJjbGZoZTFvbTYwM29sM3ByMGo3Z3Mya3dhIn0.df9VnZ0zo7sdcqGNbfrAzQ', {
		maxZoom: 21,
		minZoom: 1,
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
			'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		id: 'mapbox/streets-v11',
		tileSize: 512,
		zoomOffset: -1,
		accessToken: 'pk.eyJ1IjoibTFpZjEzIiwiYSI6ImNqczBubmhyajFnMnY0YWx4c2FwMmRtbm4ifQ.O6W7HeTW3UvOVgjCiPrdsA'
	}).addTo(mymap);

	// Ajout d'un marker
	L.marker([45.78207, 4.86559]).addTo(mymap).bindPopup('Entrée du bâtiment<br>Nautibus.').openPopup();

	getAllRessources();

	// Clic sur la carte
	mymap.on('click', e => {
		updateMap([e.latlng.lat, e.latlng.lng], mymap.getZoom());
		document.getElementById("lat").value = e.latlng.lat;
		document.getElementById("lon").value = e.latlng.lng;
		document.getElementById("zoom").value = mymap.getZoom();
	});

	//Modif du formulairee
	document.getElementById("setLatLonZoom").addEventListener("change", function () {
        let tempLat = document.getElementById("lat").value;	
		let tempLon = document.getElementById("lon").value;
		let zoom = document.getElementById("zoom").value;

		updateMap([tempLat, tempLon], zoom);
    });

	return mymap;
}

// Mise à jour de la map
function updateMap(latlng, zoom) {
	// Affichage à la nouvelle position
	mymap.setView(latlng, zoom);

	// La fonction de validation du formulaire renvoie false pour bloquer le rechargement de la page.
	return false;
}

//getBounds
function getBounds() {
	return mymap.getBounds();
}

//affichage ZRR
function ZRRDraw(bounds) {
	// create an orange rectangle
	L.rectangle(bounds, {color: "#ff7800", weight: 1}).addTo(mymap);

	// zoom the map to the rectangle bounds
	mymap.fitBounds(bounds);
}

async function getAllRessources() {
	const headers = new Headers();
	headers.append("Authentication", localStorage.getItem('token'));
	headers.append("Accept", "application/json");
	const requestConfig = {
		method: "GET",
		headers: headers,
	};

	const result = await fetch(`${apiBase}/api/resources`, requestConfig)
		.then((response) => {
			return response;
		})
	.catch((err) => {
		console.error("In get ressources: " + err);
	})

	const ressources = await result.json();
	let previousMapCenter = mymap.getCenter();
	let previousMapZoom = mymap.getZoom();

	while (groupMarker.length > 0) {
		let layer = groupMarker.pop();
		mymap.removeLayer(layer);	
	}
	ressources.forEach(ressource => {
		// console.log(ressource.id + " : " + ressource.position[0] + " " + ressource.position[1]);
		groupMarker.push(L.marker([ressource.position[0], ressource.position[1]]).addTo(mymap).bindPopup(ressource.id));
	});
	mymap.setView(previousMapCenter, previousMapZoom);
}

setInterval(getAllRessources, 5000);
export { updateMap, ZRRDraw, getBounds };
export default initMap;