<script setup>
import { useResourcesStore } from '@/stores/resources'
import { useUserStore } from '@/stores/user'
import 'leaflet/dist/leaflet.css'
import { onBeforeMount, watch } from 'vue'
// initialisation de la map
let lat = 45.782,
  lng = 4.8656,
  zoom = 19
let mymap = {}
let groupMarker = []

const storeResources = useResourcesStore()
const storeUser = useUserStore()

function calcDist(lat1, lon1, lat2, lon2) {
  const R = 6371e3;
  var dLat = toRad(lat2 - lat1);
  var dLon = toRad(lon2 - lon1);
  var lat1 = toRad(lat1);
  var lat2 = toRad(lat2);

  var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  var d = R * c;
  d = Math.round(d * 10) / 10;

  return d;
}

function toRad(Value) {
  return Value * Math.PI / 180;
}

async function getAllRessources() {
  const headers = new Headers()
  headers.append('Authentication', localStorage.getItem('token'))
  headers.append('Accept', 'application/json')
  const requestConfig = {
    method: 'GET',
    headers: headers
  }
  try {
    const result = await fetch(`https://192.168.75.36/game/api/resources`, requestConfig)
      .then((response) => {
        return response
      })
      .catch((err) => {
        console.error('In get ressources: ' + err)
      })

    storeResources.resources = await result.json()
    if (!storeResources.resources.find(user => user.id === storeUser.login)) {
      storeUser.isDead = true
    }
    else {
      storeUser.role = storeResources.resources.find(user => user.id === storeUser.login).role
    }
    // storeUser.position = storeResources.resources.find(user => user.id === storeUser.login).position;
    // console.log(storeUser.position)
    // console.log(storeResources.resources)

  } catch (err) {
    console.error('In get ressources: ' + err)
  }
}

// Procédure de mise à jour de la map
function updateMap() {
  // Affichage à la nouvelle position
  mymap.setView([lat, lng], zoom)

  // La fonction de validation du formulaire renvoie false pour bloquer le rechargement de la page.
  return false
}
onBeforeMount(async () => {
  // HERE is where to load Leaflet components!
  const L = await import('leaflet')

  // Procédure d'initialisation
  mymap = L.map('map', {
    center: [lat, lng],
    zoom: zoom
  })
  //updateMap();

  // Création d'un "tile layer" (permet l'affichage sur la carte)
  L.tileLayer(
    'https://api.mapbox.com/v4/mapbox.satellite/{z}/{x}/{y}@2x.jpg90?access_token=pk.eyJ1IjoieGFkZXMxMDExNCIsImEiOiJjbGZoZTFvbTYwM29sM3ByMGo3Z3Mya3dhIn0.df9VnZ0zo7sdcqGNbfrAzQ',
    {
      maxZoom: 22,
      minZoom: 1,
      attribution:
        'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
        '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
      id: 'mapbox/streets-v11',
      tileSize: 512,
      zoomOffset: -1,
      accessToken:
        'pk.eyJ1IjoieGFkZXMxMDExNCIsImEiOiJjbGZoZTFvbTYwM29sM3ByMGo3Z3Mya3dhIn0.df9VnZ0zo7sdcqGNbfrAzQ'
    }
  ).addTo(mymap)

  // Ajout d'un marker
  L.marker([45.78207, 4.86559])
    .addTo(mymap)
    .bindPopup('Entrée du bâtiment<br><strong>Nautibus</strong>.')
    .openPopup()

  // Clic sur la carte
  mymap.on('click', (e) => {
    lat = e.latlng.lat
    lng = e.latlng.lng
    updateMap()
  })

  while (groupMarker.length > 0) {
    let layer = groupMarker.pop()
    mymap.removeLayer(layer)
  }
  getAllRessources();
  getZRR();
})

function majPositionPlayer() {
  function markerExistFct(id) {
    let markerExist = null;
    groupMarker.forEach(marker => {
      if (marker.getPopup().getContent().includes(id)) {
        markerExist = marker;
      }
    });
    return markerExist;
  }

  storeResources.resources.forEach((ressource) => {
    const markerExist = markerExistFct(ressource.id);
    if (!markerExist) {

    }
    let icon = null
    if (ressource.role == 'PIRATE') {
      let img = "pirate-1"
      if (ressource.id == storeUser.login && storeUser.img != null) {
        img = storeUser.img;
      }
      if (ressource.ttl > 0) {
        icon = L.icon({
          iconUrl: `src/assets/img/${img}-ttl.png`,
          iconSize: [30, 30],
          iconAnchor: [15, 15],
          popupAnchor: [0, -15]
        })
      } else {
        icon = L.icon({
          iconUrl: `src/assets/img/${img}.png`,
          iconSize: [30, 30],
          iconAnchor: [15, 15],
          popupAnchor: [0, -15]
        })
      }
      if (ressource.id == storeUser.login) {
        if (markerExist) {
          markerExist.setLatLng([ressource.position[0], ressource.position[1]]);
          markerExist.getPopup().setContent(`${ressource.id}<br>${ressource.role}`);
        } else {
          groupMarker.push(
            L.marker([storeUser.position[0], storeUser.position[1]], { icon: icon })
              .addTo(mymap)
              .bindPopup(`${ressource.id}<br>${ressource.role}`)
          )
        }
      } else {
        if (markerExist) {
          markerExist.setLatLng([ressource.position[0], ressource.position[1]]);
          markerExist.getPopup().setContent(`${ressource.id}<br>${ressource.role}<br>${calcDist(storeUser.position[0], storeUser.position[1], ressource.position[0], ressource.position[1])}m`);
        } else {
          groupMarker.push(
            L.marker([ressource.position[0], ressource.position[1]], { icon: icon })
              .addTo(mymap)
              .bindPopup(`${ressource.id}<br>${ressource.role}<br>${calcDist(storeUser.position[0], storeUser.position[1], ressource.position[0], ressource.position[1])}m`)
          )
        }
      }
    } else if (ressource.role == 'VILLAGEOIS') {
      let img = "villageois-1"
      if (ressource.id == storeUser.login && storeUser.img != null) {
        console.log(storeUser.img)
        img = storeUser.img;
      }
      if (ressource.ttl > 0) {
        icon = L.icon({
          iconUrl: `src/assets/img/${img}-ttl.png`,
          iconSize: [30, 30],
          iconAnchor: [15, 15],
          popupAnchor: [0, -15]
        })
      } else {
        icon = L.icon({
          iconUrl: `src/assets/img/${img}.png`,
          iconSize: [30, 30],
          iconAnchor: [15, 15],
          popupAnchor: [0, -15]
        })
      }

      if (ressource.id == storeUser.login) {
        if (markerExist) {
          markerExist.setLatLng([ressource.position[0], ressource.position[1]]);
          markerExist.getPopup().setContent(`${ressource.id}<br>${ressource.role}`);
        } else {
          groupMarker.push(
            L.marker([storeUser.position[0], storeUser.position[1]], { icon: icon })
              .addTo(mymap)
              .bindPopup(`${ressource.id}<br>${ressource.role}`)
          )
        }
      } else {
        if (markerExist) {
          markerExist.setLatLng([ressource.position[0], ressource.position[1]]);
          markerExist.getPopup().setContent(`${ressource.id}<br>${ressource.role}<br>${calcDist(storeUser.position[0], storeUser.position[1], ressource.position[0], ressource.position[1])}m`);
        } else {
          groupMarker.push(
            L.marker([ressource.position[0], ressource.position[1]], { icon: icon })
              .addTo(mymap)
              .bindPopup(`${ressource.id}<br>${ressource.role}<br>${calcDist(storeUser.position[0], storeUser.position[1], ressource.position[0], ressource.position[1])}m`)
          )
        }
      }

    } else if (ressource.role == 'FLASK') {
      icon = L.icon({
        iconUrl: `src/assets/img/potion.png`,
        iconSize: [30, 60],
        iconAnchor: [15, 15],
        popupAnchor: [0, -15]
      })

      if (markerExist) {
        markerExist.setLatLng([ressource.position[0], ressource.position[1]]);
        markerExist.getPopup().setContent(`${ressource.id}<br>${ressource.role}<br>${calcDist(storeUser.position[0], storeUser.position[1], ressource.position[0], ressource.position[1])}m`);
      } else {
        groupMarker.push(
          L.marker([ressource.position[0], ressource.position[1]], { icon: icon })
            .addTo(mymap)
            .bindPopup(`${ressource.id}<br>${ressource.role}<br>${calcDist(storeUser.position[0], storeUser.position[1], ressource.position[0], ressource.position[1])}m`)
        )
      }
    }
  })
}

//TEST METHOD
function moovPlayer() {
  ressource.forEach((ressource) => {
    if (ressource.id == login) {
      let posFloat = parseFloat(ressource.position[0]) + 0.001
      ressource.position[0] = posFloat.toString()
    }
  })
}

async function getZRR() {
  const headers = new Headers()
  headers.append('Authentication', localStorage.getItem('token'))
  headers.append('Accept', 'application/json')
  const requestConfig = {
    method: 'GET',
    headers: headers
  }
  try {
    const result = await fetch(`https://192.168.75.36/game/api/zrr`, requestConfig)
      .then((response) => {
        return response
      })
      .catch((err) => {
        console.error('In get ressources: ' + err)
      })
    const res = await result.json()
    storeResources.positionNE = [res.positionNE.lat, res.positionNE.lng]
    storeResources.positionSO = [res.positionSO.lat, res.positionSO.lng]
    // console.log(storeResources.positionNE);
    // console.log(storeResources.positionSO);
  } catch (err) {
    console.error('In get ressources: ' + err)
  }

  mymap.eachLayer(function (layer) {
    if (layer instanceof L.Rectangle) {
      mymap.removeLayer(layer)
    }
  })

  mymap.invalidateSize()
  var corner1 = L.latLng(storeResources.positionNE[0], storeResources.positionNE[1])
  var corner2 = L.latLng(storeResources.positionSO[0], storeResources.positionSO[1])
  let bounds = L.latLngBounds(corner1, corner2)
  L.rectangle(bounds, { color: '#ff7800', weight: 1 }).addTo(mymap)
  // mymap.fitBounds(bounds);
}

setInterval(getAllRessources, 2000)
setInterval(getZRR, 10000)
setInterval(majPositionPlayer, 1000)

watch(
  () => storeUser.role,
  (newRole, oldRole) => {
    if (oldRole !== '' && newRole !== '' && newRole !== oldRole && oldRole !== undefined) {
      Notification.requestPermission().then((result) => {
        if (result === "granted") {
          const notifTitle = "Tu es convertis";
          const notifBody = `tu as changer de Rôle !!`;
          const options = {
            body: notifBody
          };
          new Notification(notifTitle, options);
        }
      });
    }
  }
)

watch(
  () => storeUser.isDead,
  () => {
    Notification.requestPermission().then((result) => {
      if (result === "granted") {
        const notifTitle = "Tu es mort";
        const notifBody = `Tu peux plus jouer !`;
        const options = {
          body: notifBody
        };
        new Notification(notifTitle, options);
      }
    });
  }
)

// Fonction de test
// setInterval(getPositionUser, 1000)
// setInterval(moovPlayer, 10000);
//fonction bien décommenter lors du deploy ou des test
// setInterval(await sendNewPosition, 5000);
</script>

<template>
  <section>
    <h2>Carte</h2>
    <p v-if="storeUser.isDead">Vous êtes mort</p>
    <p class="content">
      -------------------------------------------------------------------------------------------------
    </p>
    <div id="map" class="map" ref="map"></div>
  </section>
  <p v-if="storeUser.direction">Direction: {{ storeUser.direction }}</p>
</template>

<style scoped>
.map {
  height: 400px;
  width: 100%;
  border: 1px solid;
}
</style>
