<script setup>
import HelloWorld from '../components/HelloWorld.vue'
import Login from '../components/Login.vue'
import MyView from '../components/MyView.vue'
import { ref } from 'vue'

var logged = ref(false);
const errorMessage = ref("");

async function authenticate() {
  const headers = new Headers();
  headers.append("Content-Type", "application/json");
  headers.append("Accept", "application/json");
  const requestConfig = {
      method: "GET",
      headers: headers
  };
  const params = new URLSearchParams();
  params.append('jwt', localStorage.getItem('token'));
  params.append('origin', window.location.origin);
  await fetch("http://192.168.75.36:8080/users/authenticate?" + params , requestConfig)
      .then((response) => {
          if (response.headers.get("Authentication")) {
            // localStorage.setItem(response.data)
            console.log(response.data)
          } 
      })
  .catch((err) => {
      console.log(err)
  })
}
</script>

<template>
  <main>
    <p v-if="logged">  
      <HelloWorld msg="Vous êtes bien connecté" />  
      <MyView />
      <button @click="logged = !logged">Loggout</button>
    </p>
    <p v-else> 
      <HelloWorld msg="Veuillez vous connectez" /> 
      <h2>{{ errorMessage }}</h2>
      <Login @loginEvent ='logged = !logged' @messageError="errorMessage = $event"/> 
    </p>
  </main>
</template>
