<script setup>
import { useResourcesStore } from '@/stores/resources'
import { useUserStore } from '@/stores/user'
import { watch } from 'vue'
import { onBeforeMount } from 'vue'

const storeResources = useResourcesStore()
const storeUser = useUserStore()

let player

function majPlayer() {
  storeResources.resources.forEach((resource) => {
    if (resource.id === storeUser.login) {
      player = resource
    }
  })
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
  await fetch(`http://localhost:3376/api/resources/${id}`, requestConfig)
    .then((response) => {
      if (response.status == 204) {
        console.log('pirate tué')
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
  await fetch(`http://localhost:3376/api/resources/${id}`, requestConfig)
    .then((response) => {
      if (response.status == 204) {
        console.log('pirate transformer')
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
  await fetch(`http://localhost:3376/api/resources/${id}`, requestConfig)
    .then((response) => {
      if (response.status == 204) {
        console.log('potion prise')
      } else {
        console.log('erreur')
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

onBeforeMount(async () => {
  // HERE is where to load Leaflet components!
  const L = await import('leaflet')
})

function isNearFromMe(position) {
  const posMe = L.latLng(player.position)
  const posOther = L.latLng(position)
  const dist = posMe.distanceTo(posOther)
  console.log(dist <= 100)
  return dist <= 100
}

function test() {
  console.log('player')
  console.log(player)
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
    <h2>actions</h2>
    <br />
    <div v-if="storeResources.resources">
      <div v-for="r in storeResources.resources">
        <span v-if="r.role !== player.role">
          <span v-if="player.ttl > 0">
            <span v-if="isNearFromMe(r.position)">
              {{ r.id }}, {{ r.role }}
              <button @click="aTuer(r.id)" v-if="r.role === 'PIRATE'">tuer</button>
              <button @click="aConvert(r.id)" v-else-if="r.role === 'VILLAGEOIS'">convertir</button>
              <button @click="aBoire(r.id)" v-else-if="r.role === 'FLASK'">boire</button>
              <br />
            </span>
          </span>
        </span>
      </div>
    </div>
  </section>
</template>
