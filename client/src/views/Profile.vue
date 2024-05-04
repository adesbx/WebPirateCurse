<script setup>
  async function changePassword() {
    
    let headers = new Headers();
    headers.append("Content-Type", "application/json");
    headers.append("Accept", "application/json");
    let requestConfig = {
        method: "GET",
        headers: headers
    };
    let params = new URLSearchParams();
    params.append('jwt', localStorage.getItem('token'));
    params.append('origin', window.location.origin);
    await fetch("https://192.168.75.36:8443/users/authenticate?" + params , requestConfig)
        .then((response) => {
            if (response.headers.get("Authentication")) {
              // localStorage.setItem(response.data)
              console.log(response.data)
            } 
        })
    .catch((err) => {
        console.log(err)
    })


      let body = {
        password: document.getElementById("password").value
      };
      headers = new Headers();
      headers.append("Content-Type", "application/json");
      requestConfig = {
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
