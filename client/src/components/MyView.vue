<template>
    <section>
      <h2>Carte</h2>
      <p class="content">
        <strong>TODO :</strong> mettre à jour les positions des différents objets
        sur la carte.
      </p>
      <div id="map" class="map" ref="map"></div>
    </section>
  </template>

  <script>
  import "leaflet/dist/leaflet.css";
  //import { LMap, LTileLayer } from "@vue-leaflet/vue-leaflet";

  // initialisation de la map
  let lat = 45.782,
    lng = 4.8656,
    zoom = 19;
  let mymap = {};
  let groupMarker = [];

  let login = "John";

  let mockZRR = {
    "positionNE": {
        "lat": 45.799485997405384,
        "lng": 4.90891456604004
    },
    "positionSO": {
        "lat": 45.77554537118828,
        "lng": 4.762830734252931
    }
  }
  
  let ressource = [
      {
          "id": "toto",
          "position": [
              "45.78196433811854",
              "4.865881204605103"
          ],
          "role": "PIRATE",
          "ttl": 0,
          "potions": 0,
          "terminated": 0,
          "turned": 0
      },
      {
          "id": "John",
          "position": [
              "45.78216635696405",
              "4.864507913589478"
          ],
          "role": "VILLAGEOIS",
          "ttl": 20,
          "potions": 0,
          "terminated": 0,
          "turned": 0
      },
      {
          "id": "titi",
          "position": [
              "45.78282104245197",
              "4.864250421524049"
          ],
          "role": "PIRATE",
          "ttl": 0,
          "potions": 0,
          "terminated": 10,
          "turned": 0
      },
      {
          "id": "potion1",
          "position": [
              "45.78220619944917",
              "4.865867793560029"
          ],
          "role": "FLASK",
          "ttl": 50,
          "potions": 0,
          "terminated": 0,
          "turned": 0
      },
      {
          "id": "potion2",
          "position": [
              "45.78163137968618",
              "4.865838289260865"
          ],
          "role": "FLASK",
          "ttl": 60,
          "potions": 0,
          "terminated": 0,
          "turned": 0
      },
      {
          "id": "potion3",
          "position": [
              "45.781825917472084",
              "4.868005514144898"
          ],
          "role": "FLASK",
          "ttl": 60,
          "potions": 0,
          "terminated": 0,
          "turned": 0
      },
      {
          "id": "potion4",
          "position": [
              45.78276866789585,
              4.868021607398988
          ],
          "role": "FLASK",
          "ttl": 60,
          "potions": 0,
          "terminated": 0,
          "turned": 0
      },
      {
          "id": "potion5",
          "position": [
              45.78346075627386,
              4.866701960563661
          ],
          "role": "FLASK",
          "ttl": "20",
          "potions": 0,
          "terminated": 0,
          "turned": 0
      },
      {
          "id": "ADMIN",
          "position": [
              "45.78159022737526",
              "4.86721158027649"
          ],
          "role": "ADMIN",
          "ttl": 0,
          "potions": 0,
          "terminated": 0,
          "turned": 0
      },
      {
          "id": "potion6",
          "position": [
              45.78114877340201,
              4.866026043891908
          ],
          "role": "FLASK",
          "ttl": 60,
          "potions": 0,
          "terminated": 0,
          "turned": 0
      },
      {
          "id": "potionTest",
          "position": [
              "45.78220619944917",
              "4.865867793560029"
          ],
          "role": "FLASK",
          "ttl": 50,
          "potions": 0,
          "terminated": 0,
          "turned": 0
      },
      {
          "id": "potionTest",
          "position": [
              "45.78220619944917",
              "4.865867793560029"
          ],
          "role": "FLASK",
          "ttl": 50,
          "potions": 0,
          "terminated": 0,
          "turned": 0
      },
      {
          "id": "potionTest",
          "position": [
              "45.78220619944917",
              "4.865867793560029"
          ],
          "role": "FLASK",
          "ttl": 50,
          "potions": 0,
          "terminated": 0,
          "turned": 0
      }
  ];


  async function getAllRessources() {
    const headers = new Headers();
    headers.append("Authentication", localStorage.getItem('token'));
    headers.append("Accept", "application/json");
    headers.append("Origin", "http://localhost:5173/");
    const requestConfig = {
      method: "GET",
      headers: headers,
    };
    try {
      const result = await fetch(`https://192.168.75.36/game/api/resources`, requestConfig)
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
        groupMarker.push(
          L.marker([ressource.position[0], ressource.position[1]])
          .addTo(mymap)
          .bindPopup(`${ressource.id}<br>${ressource.role}`)
          .on('mouseover', function(e) {
            this.openPopup();
          })
        );
      });
      mymap.setView(previousMapCenter, previousMapZoom);
    } catch (err) {
          console.error("In get ressources: " + err);
      }
  }

  export default {
    name: "MyMap",
    methods: {
      // Procédure de mise à jour de la map
      updateMap: function () {
        // Affichage à la nouvelle position
        mymap.setView([lat, lng], zoom);
  
        // La fonction de validation du formulaire renvoie false pour bloquer le rechargement de la page.
        return false;
      },
    },
    async beforeMount() {
      // HERE is where to load Leaflet components!
      const L = await import("leaflet");

      // Procédure d'initialisation
      mymap = L.map("map", {
        center: [lat, lng],
        zoom: zoom,
      });
      //updateMap();
  
      // Création d'un "tile layer" (permet l'affichage sur la carte)
      L.tileLayer(
        "https://api.mapbox.com/v4/mapbox.satellite/{z}/{x}/{y}@2x.jpg90?access_token=pk.eyJ1IjoieGFkZXMxMDExNCIsImEiOiJjbGZoZTFvbTYwM29sM3ByMGo3Z3Mya3dhIn0.df9VnZ0zo7sdcqGNbfrAzQ",
        {
          maxZoom: 22,
          minZoom: 1,
          attribution:
            'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
            '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
            'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
          id: "mapbox/streets-v11",
          tileSize: 512,
          zoomOffset: -1,
          accessToken:
            "pk.eyJ1IjoieGFkZXMxMDExNCIsImEiOiJjbGZoZTFvbTYwM29sM3ByMGo3Z3Mya3dhIn0.df9VnZ0zo7sdcqGNbfrAzQ",
        }
      ).addTo(mymap);
      //TODO FIX THIS
      // getAllRessources();

      // Ajout d'un marker
      L.marker([45.78207, 4.86559])
        .addTo(mymap)
        .bindPopup("Entrée du bâtiment<br><strong>Nautibus</strong>.")
        .openPopup();
  
      // Clic sur la carte
      mymap.on("click", (e) => {
        lat = e.latlng.lat;
        lng = e.latlng.lng;
        this.updateMap();
      });
      
      // const headers = new Headers();
      //   headers.append("Authentication", localStorage.getItem('token'));
      //   headers.append("Accept", "application/json");
      //   headers.append("Origin", "http://localhost:5173/");
      //   const requestConfig = {
      //       method: "GET",
      //       headers: headers,
      //   };
      // var bounds = await fetch("https://192.168.75.36/game/api/zrr", requestConfig)
      //       .then((response) => {
      //         return response.json()
      //       })
      //   .catch((err) => {
      //       console.log(err)
      //   })
      // const zrr = L.latLngBounds(L.latLng(bounds.positionSO.lat, bounds.positionSO.lng),
      //                      L.latLng(bounds.positionNE.lat, bounds.positionNE.lng));
      // L.rectangle(zrr, {color: "#ff7800", weight: 1}).addTo(mymap);

      
      var corner1 = L.latLng(mockZRR.positionNE.lat,
          mockZRR.positionNE.lng)
		  var corner2 = L.latLng(mockZRR.positionSO.lat,
          mockZRR.positionSO.lng)
      let bounds = L.latLngBounds(corner1, corner2);
      L.rectangle(bounds, {color: "#ff7800", weight: 1}).addTo(mymap);
      mymap.fitBounds(bounds);

      while (groupMarker.length > 0) {
        let layer = groupMarker.pop();
        mymap.removeLayer(layer);	
		  }
      const pirateIcons = ["pirate-1", "pirate-2", "pirate-3", "pirate-4"];
      const villagerIcon = ["villageois-1", "villageois-2", "villageois-3", "villageois-4"];
      ressource.forEach(ressource => {
        // console.log(ressource.id + " : " + ressource.position[0] + " " + ressource.position[1]);
        if(ressource.role == "PIRATE") {
          //IMAGE RANDOM DE PIRATE
          let icon;
          if(ressource.ttl > 0) {
      
            icon = L.icon({
              iconUrl: `src/assets/img/pirate-ttl.png`,
              iconSize: [30, 30],
              iconAnchor: [15, 15],
              popupAnchor: [0, -15]
            });

          } else {
            const randomIndex = Math.floor(Math.random() * pirateIcons.length);
            const randomPirateIcon = pirateIcons[randomIndex];

            icon = L.icon({
              iconUrl: `src/assets/img/${randomPirateIcon}.png`,
              iconSize: [30, 30],
              iconAnchor: [15, 15],
              popupAnchor: [0, -15]
            });
          }

          groupMarker.push(L.marker([ressource.position[0], ressource.position[1]], {icon: icon}).addTo(mymap).bindPopup(`${ressource.id}<br>${ressource.role}`));
        }
        else if( ressource.role == "VILLAGEOIS") {
          //IMAGE RANDOM DE VILLAGEOIS
          let icon;
          if(ressource.ttl > 0) {
              
            icon = L.icon({
              iconUrl: `src/assets/img/villageois-ttl.png`,
              iconSize: [30, 30],
              iconAnchor: [15, 15],
              popupAnchor: [0, -15]
            });

          } else {
            const randomIndex = Math.floor(Math.random() * villagerIcon.length);
            const randomVillageoisIcon = villagerIcon[randomIndex];

            icon = L.icon({
              iconUrl: `src/assets/img/${randomVillageoisIcon}.png`,
              iconSize: [30, 30],
              iconAnchor: [15, 15],
              popupAnchor: [0, -15]
            });
          }

          groupMarker.push(L.marker([ressource.position[0], ressource.position[1]], {icon: icon}).addTo(mymap).bindPopup(`${ressource.id}<br>${ressource.role}`));
        } else if(ressource.role == "FLASK") {

          const icon = L.icon({
            iconUrl: `src/assets/img/potion.png`,
            iconSize: [30, 60],
            iconAnchor: [15, 15],
            popupAnchor: [0, -15]
          });

          groupMarker.push(L.marker([ressource.position[0], ressource.position[1]], {icon: icon}).addTo(mymap).bindPopup(`${ressource.id}<br>${ressource.role}`));
        }
      });

    },
  };

  //TODO cette fonction devra également envoyer la nouvelle position au serveur
  function moovPlayer() {
    ressource.forEach(ressource => {
      if(ressource.id == login) {
        let posFloat = parseFloat(ressource.position[0]) + 0.001
        ressource.position[0] = posFloat.toString()
      }
    })
  }

  function majPositionPlayer() {
    for (let i = 0; i < groupMarker.length; i++) {
      const marker = groupMarker[i];
      const popupContent = marker.getPopup().getContent();
      if (popupContent.includes(login)) {
          const oldIcon = marker.getIcon();
          mymap.removeLayer(marker);

          const userResource = ressource.find(resource => resource.id === login);

          const newUserMarker = L.marker([userResource.position[0], userResource.position[1]])
            .addTo(mymap)
            .bindPopup(`${userResource.id}<br>${userResource.role}`);
          
          newUserMarker.setIcon(oldIcon);
          groupMarker[i] = newUserMarker;

          break;
      }
    }
  }

  async function sendNewPosition() {
    let userPosition = ressource.find(resource => resource.id === login).position
    console.log(userPosition)
    const headers = new Headers();
    headers.append("Authentication", localStorage.getItem('token'));
    headers.append("Content-Type", "application/json");
    const requestConfig = {
        method: "PUT",
        headers: headers,
        body: JSON.stringify(userPosition),
        mode: "cors"
    };
    await fetch(`https://192.168.75.36/game/api/resources/${login}/position`, requestConfig)
        .then((response) => {
            if (response.status == 204) {
              console.log("position modifié")
            } else {
              console.log("erreur")
            }
        })
    .catch((err) => {
        console.log(err)
    })
  }

  // setInterval(getAllRessources, 5000);

  setInterval(moovPlayer, 1000);
  setInterval(majPositionPlayer, 1000)

  //fonction bien décommenter lors du deploy ou des test
  // setInterval(await sendNewPosition, 5000);
  </script>
  
  <style scoped>
  .map {
    height: 400px;
    width: 100%;
    border: 1px solid;
  }
  </style>
  