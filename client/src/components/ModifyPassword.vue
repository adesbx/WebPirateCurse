<template>
  <label for="password">Entrez un nouveau mot de passe : </label>
  <input type="text" id="password" />
  <br />
  <button @click="changePassword()">Modifié</button>
</template>

<script setup>
const emit = defineEmits(['msg'])

import { useUserStore } from '@/stores/user'

const storeUser = useUserStore()

async function changePassword() {
  let login = storeUser.login
  let body = {
    species: null,
    password: document.getElementById('password').value
  }
  let headers = new Headers()
  headers.append('Content-Type', 'application/json')
  let requestConfig = {
    method: 'PUT',
    headers: headers,
    body: JSON.stringify(body),
    mode: 'cors'
  }
  await fetch(`https://192.168.75.36:8443/users/users/${login}`, requestConfig)
    .then((response) => {
      if (response.status == 200) {
        emit('msg', 'Mot de passe modifié')
      } else {
        emit('msg', 'Erreur lors de la modification')
      }
    })
    .catch((err) => {
      emit('msg', 'Erreur requete')
    })
}
</script>

<style scoped>
input,
input[type='submit'],
select {
  color: grey;
  border: 1px solid;
}
</style>
