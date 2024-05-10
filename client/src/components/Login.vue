<template>
      <label for="login">Login :&nbsp;</label>
      <input type="text" name="login" id="login" />
      <br />
      <label for="password">Password :&nbsp;</label>
      <input type="password" name="password" id="password" />
      <br />
      <button @click="login()">Send</button>
  
  </template>
  
  <script setup>
  import { useUserStore } from '@/stores/user';

  const storeUser = useUserStore();

  const emit = defineEmits(['loginEvent', 'messageError'])

  async function login() {
    const body = {
            login: document.getElementById("login").value,                             
            password: document.getElementById("password").value
        };
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        const requestConfig = {
            method: "POST",
            headers: headers,
            body: JSON.stringify(body),
            mode: "cors"
        };
        await fetch("https://192.168.75.36:8443/users/login", requestConfig)
            .then((response) => {
                if (response.headers.get("Authentication")) {
                    const token = response.headers.get("Authentication");
                    localStorage.setItem('token', token);
                    storeUser.connected = true;
                    emit('loginEvent')
                } else {
                    emit('messageError', 'Mauvais mot de passe')
                }
            })
        .catch((err) => {
            emit('messageError', 'Erreur lors de la connexion')
        })
  }
  </script>
  
  <style scoped>
  input,
  input[type="submit"],
  select {
    color: grey;
    border: 1px solid;
  }
  </style>