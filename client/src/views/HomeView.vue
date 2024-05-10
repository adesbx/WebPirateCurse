<script setup>
import HelloWorld from '../components/HelloWorld.vue'
import Login from '../components/Login.vue'
import MyView from '../components/MyView.vue'
import Actions from '../components/Actions.vue'
import { ref, defineProps } from 'vue'

const emit = defineEmits(['logoutEvent'])


const props = defineProps({
  logged: Boolean
})

const errorMessage = ref("");

function logout() {
  emit("logoutEvent")
}

</script>

<template>
  <main>
    <p v-if="logged">  
      <HelloWorld msg="Vous êtes bien connecté" />  
      <div style="display: flex;">
        <MyView />
        <Suspense>
          <Actions />
        </Suspense>
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
