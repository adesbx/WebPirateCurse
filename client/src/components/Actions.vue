<script setup>
import { useResourcesStore } from '@/stores/resources'
import { useUserStore } from '@/stores/user'
import { computed, ref, watch, onBeforeMount } from 'vue'
import Modale from './Modale.vue'

const storeResources = useResourcesStore()
const storeUser = useUserStore()

let player = ref(null)
let notificationVisible = ref(false)
let notificationMessage = ref('')

function majPlayer() {
  const ressources = computed(() => {
      if (!storeResources) {
        return null;
      }
      return storeResources.resources;
    });
  if (ressources.value) {
    ressources.value.forEach((ressource) => {
      if (ressource.id === storeUser.login) {
        player.value = ressource
      }
    })
  }
}

async function aTuer(id) {
  let body = {
    operationType: 'terminate pirate'
  }
  let headers = new Headers()
  headers.append('Authentication', localStorage.getItem('token'))
  headers.append('Content-Type', 'application/json')
  let requestConfig = {
    method: 'POST',
    headers: headers,
    body: JSON.stringify(body),
    mode: 'cors'
  }
  await fetch(`https://192.168.75.36/game/api/resources/${id}`, requestConfig)
    .then((response) => {
      if (response.status == 204) {
        navigator.vibrate(200);
        // console.log('pirate tué')
        showNotification('Pirate tué')
      } else {
        console.log('erreur')
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

async function aConvert(id) {
  let body = {
    operationType: 'turn villager into pirate'
  }
  let headers = new Headers()
  headers.append('Authentication', localStorage.getItem('token'))
  headers.append('Content-Type', 'application/json')
  let requestConfig = {
    method: 'POST',
    headers: headers,
    body: JSON.stringify(body),
    mode: 'cors'
  }
  await fetch(`https://192.168.75.36/game/api/resources/${id}`, requestConfig)
    .then((response) => {
      if (response.status == 204) {
        navigator.vibrate(200);
        // console.log('pirate transformer')
        showNotification('Pirate transformer')
      } else {
        console.log('erreur')
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

async function aBoire(id) {
  let body = {
    operationType: 'grab potion flask'
  }
  let headers = new Headers()
  headers.append('Authentication', localStorage.getItem('token'))
  headers.append('Content-Type', 'application/json')
  let requestConfig = {
    method: 'POST',
    headers: headers,
    body: JSON.stringify(body),
    mode: 'cors'
  }
  await fetch(`https://192.168.75.36/game/api/resources/${id}`, requestConfig)
    .then((response) => {
      if (response.status == 204) {
        // console.log('potion prise')
        showNotification('Potion flask récupéré')
      } else {
        console.log('erreur')
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

function showNotification(message) {
  notificationMessage.value = message
  notificationVisible.value = true
}

onBeforeMount(async () => {
  // HERE is where to load Leaflet components!
  const L = await import('leaflet')
})

function isNearFromMe(position, playerPos) {
  const posMe = L.latLng(playerPos)
  const posOther = L.latLng(position)
  const dist = posMe.distanceTo(posOther)
  // console.log(dist <= 100)
  return dist <= 2
}

watch(
  () => storeResources.resources,
  () => {
    majPlayer()
  }
)
</script>

<template>
  <section>
    <h2>Actions</h2>
    <br />
    <div v-if="storeResources.resources && player !== null && storeUser.login !== null">
      <div v-for="r in storeResources.resources">
        <span v-if="r.role !== player.role">
          <span v-if="isNearFromMe(r.position, player.position)">
              {{ r.id }}, {{ r.role }}
              <button @click="aBoire(r.id)" v-if="r.role === 'FLASK'">boire</button>
              <span v-if="player.potion > 0 || player.ttl > 0">
                <button @click="aTuer(r.id)" v-if="r.role === 'PIRATE'">tuer</button>
                <button @click="aConvert(r.id)" v-else-if="r.role === 'VILLAGEOIS'">convertir</button>
              </span>
              <br />
          </span>
        </span>
      </div>
    </div>
    <Modale 
      :message="notificationMessage" 
      :visible="notificationVisible" 
      @close="notificationVisible = false" 
    />
  </section>
</template>
