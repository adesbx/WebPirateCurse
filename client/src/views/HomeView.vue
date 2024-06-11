<script setup>
import HelloWorld from '../components/HelloWorld.vue'
import Login from '../components/Login.vue'
import GetLoc from '../components/GetLoc.vue'
import MyView from '../components/MyView.vue'
import Actions from '../components/Actions.vue'
import InfoPerso from '../components/InfoPerso.vue'
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'
const storeUser = useUserStore()
const emit = defineEmits(['logoutEvent'])

const errorMessage = ref("");

function logout() {
  emit("logoutEvent")
}

Notification.requestPermission()
</script>

<template>
  <main>
    <p v-if=storeUser.connected>
      <HelloWorld msg="Vous êtes bien connecté" />
      <GetLoc />
    <div v-if="storeUser.position" style="display: flex; flex-direction: column;">
      <Actions />
      <MyView />
      <InfoPerso />
    </div>
    <div v-if="!storeUser.position">
      <h1>Impossible de récupérer votre position</h1>
      <p>Il nous faut votre position pour jouer!!</p>
    </div>
    <button @click="logout()">Logout</button>
    </p>
    <p v-else>
      <HelloWorld msg="Veuillez vous connectez" />
    <h2>{{ errorMessage }}</h2>
    <Login @messageError="errorMessage = $event" />
    </p>
  </main>
</template>
