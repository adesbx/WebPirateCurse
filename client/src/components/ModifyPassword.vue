<template>
   <label for="password">Entrez un nouveau mot de passe : </label>
   <input type="text" id="password">
   <br />
   <button @click="changePassword()">Send</button>
  </template>
  
  <script setup>

  const emit = defineEmits(['msg'])

  async function changePassword() {
    
    // let headersTest = new Headers();
    // headersTest.append("Content-Type", "application/json");
    // headersTest.append("Accept", "application/json");
    // let requestConfigTest = {
    //     method: "GET",
    //     headers: headersTest
    // };
    // let params = new URLSearchParams();
    // params.append('jwt', localStorage.getItem('token'));
    // params.append('origin', window.location.origin);
    // await fetch("https://192.168.75.36:8443/users/authenticate?" + params , requestConfigTest)
    //     .then((response) => {
    //         if (response.status == 200) {
    //           // localStorage.setItem(response.data)
    //           console.log(response)
    //         } 
    //     })
    // .catch((err) => {
    //     console.log(err)
    // })

    let login  = 'John';
    let body = {
      species: null,
      password: document.getElementById("password").value
    };
    let headers = new Headers();
    headers.append("Content-Type", "application/json");
    let requestConfig = {
        method: "PUT",
        headers: headers,
        body: JSON.stringify(body),
        mode: "cors"
    };
    await fetch(`https://192.168.75.36:8443/users/users/${login}`, requestConfig)
        .then((response) => {
            if(response.status == 200) {
                emit('msg', "Mot de passe modifié")
            } else {
                emit('msg', "Erreur lors de la modification")
            }
        })
    .catch((err) => {
        emit('msg', "Erreur requete")
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