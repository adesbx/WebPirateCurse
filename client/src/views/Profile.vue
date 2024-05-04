<script setup>
  async function changePassword() {
      const body = {
        password: document.getElementById("password").value
      };
      const headers = new Headers();
      headers.append("Content-Type", "application/json");
      const requestConfig = {
          method: "PUT",
          headers: headers,
          body: JSON.stringify(body),
          mode: "cors"
      };
      await fetch(`https://192.168.75.36:8443/users/${login}`, requestConfig)
          .then((response) => {
            console.log(response)
          })
      .catch((err) => {
          console.log(err)
          // emit('messageError', 'Erreur lors de la modification')
      })
    }
</script>

<template>
  <main>
   <h1>Bonjour, bienvenu sur votre profil !!</h1>
   <label for="password">Entrez un nouveau mot de passe : </label>
   <input type="text" id="password">
   <button @click="changePassword()">Send</button>
  </main>
</template>
