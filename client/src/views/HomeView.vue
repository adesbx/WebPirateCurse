<script setup>
import HelloWorld from '../components/HelloWorld.vue'
import Login from '../components/Login.vue'
import MyView from '../components/MyView.vue'
import Actions from '../components/Actions.vue'
import InfoPerso from '../components/InfoPerso.vue'
import { ref, defineProps } from 'vue'
import { useUserStore } from '@/stores/user'
const storeUser = useUserStore()


const emit = defineEmits(['logoutEvent'])


const props = defineProps({
  logged: Boolean
})

const errorMessage = ref("");

function logout() {
  emit("logoutEvent")
}

//position de l'utilisateur mise a jour
function getPositionUser() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(modifyPosition);
  }
}

function modifyPosition(position) {
  storeUser.position = [position.coords.latitude, position.coords.longitude];
  sendNewPosition();
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
      if (response.status == 204) {
        console.log('position modifié')
      } else {
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
