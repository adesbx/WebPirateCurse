<script setup>
import HelloWorld from '../components/HelloWorld.vue'
import Login from '../components/Login.vue'
import MyView from '../components/MyView.vue'
import Actions from '../components/Actions.vue'
import InfoPerso from '../components/InfoPerso.vue'
import { ref, defineProps } from 'vue'
import { useUserStore } from '@/stores/user'
import { useResourcesStore } from '@/stores/resources'
const storeResources = useResourcesStore()
const storeUser = useUserStore()

const emit = defineEmits(['logoutEvent'])

const props = defineProps({
  logged: Boolean
})

const errorMessage = ref("");

function logout() {
  emit("logoutEvent")
}

function calcDirection(lat1, lon1, lat2, lon2) {
  lat1 = toRad(lat1);
  lon1 = toRad(lon1);
  lat2 = toRad(lat2);
  lon2 = toRad(lon2);

  let dLon = lon2 - lon1;
  let x = Math.sin(dLon) * Math.cos(lat2);
  let y = Math.cos(lat1) * Math.sin(lat2) - (Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon));
  
  let initialBearing = Math.atan2(x, y);

  initialBearing = toDeg(initialBearing);
  let compassBearing = (initialBearing + 360) % 360;
  
  return compassBearing;
}

function getCardinalDirection(bearing) {
  const directions = [
    "Nord", "Nord-Est", "Est", "Sud-Est", 
    "Sud", "Sud-Ouest", "Ouest", "Nord-Ouest"
  ];
  const idx = Math.round(bearing / 45) % 8;
  storeUser.direction = directions[idx];
}

function toRad(Value) {
  return Value * Math.PI / 180;
}

function toDeg(Value) {
  return Value * ( 180 / Math.PI);
}

//position de l'utilisateur mise a jour
function getPositionUser() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(modifyPosition);
  }
}

function modifyPosition(position) {
  storeUser.position = [position.coords.latitude, position.coords.longitude];
  const pos = storeResources.resources.find(user => user.id === storeUser.login).position;
  if(pos[0] != storeUser.position[0] && pos[1] && storeUser.position[1]) {
    const bearing = calcDirection(pos[0], pos[1], storeUser.position[0], storeUser.position[1]);
    getCardinalDirection(bearing);
  } else {
    storeUser.direction = "Vous ne bougez pas"
  }

  if(!storeUser.isDead) {
    sendNewPosition();
  }  
}

function errorLocal() {
  storeUser.position = null;
}

async function sendNewPosition() {
  const headers = new Headers()
  headers.append('Authentication', localStorage.getItem('token'))
  headers.append('Content-Type', 'application/json')
  const requestConfig = {
    method: 'PUT',
    headers: headers,
    body: JSON.stringify(storeUser.position),
    mode: 'cors'
  }
  await fetch(`https://192.168.75.36/game/api/resources/${storeUser.login}/position`, requestConfig)
    .then((response) => {
      if (response.status != 204) {
        console.log('erreur')
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

navigator.geolocation.watchPosition(getPositionUser, errorLocal);

setInterval(console.log(storeUser.position), 1000)

</script>

<template>
  <main>
    <p v-if="logged">  
      <HelloWorld msg="Vous êtes bien connecté" />  
      <div v-if="storeUser.position" style="display: flex; flex-direction: column;">
        <Actions />
        <MyView />
        <InfoPerso />
      </div>
      <div v-if="!storeUser.position">
        <h1>Impossible de récupérer votre position</h1>
        <p>Il nous faut votre position pour jouer!!</p>
      </div>
      <button @click="logout()">Loggout</button>
    </p>
    <p v-else> 
      <HelloWorld msg="Veuillez vous connectez" /> 
      <h2>{{ errorMessage }}</h2>
      <Login @messageError="errorMessage = $event"/> 
    </p>
  </main>
</template>
