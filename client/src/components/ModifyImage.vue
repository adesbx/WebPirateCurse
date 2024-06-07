<template>
  <div v-if="player">
    <fieldset v-if="player.role == 'PIRATE'">
      <legend>Choisir une image :</legend>
      <div>
        <input type="radio" name="pirate" value="pirate-1" />
        <img for="huey" src="../assets/img/pirate-1.png" />
      </div>

      <div>
        <input type="radio" name="pirate" value="pirate-2" />
        <img for="huey" src="../assets/img/pirate-2.png" />
      </div>

      <div>
        <input type="radio" name="pirate" value="pirate-3" />
        <img for="huey" src="../assets/img/pirate-3.png" />
      </div>

      <div>
        <input type="radio" name="pirate" value="pirate-4" />
        <img for="huey" src="../assets/img/pirate-4.png" />
      </div>
    </fieldset>

    <fieldset v-if="player.role == 'VILLAGEOIS'">
      <div>
        <input type="radio" name="villageois" value="villageois-1" />
        <img for="huey" src="../assets/img/villageois-1.png" />
      </div>

      <div>
        <input type="radio" name="villageois" value="villageois-2" />
        <img for="huey" src="../assets/img/villageois-2.png" />
      </div>

      <div>
        <input type="radio" name="villageois" value="villageois-3" />
        <img for="huey" src="../assets/img/villageois-3.png" />
      </div>

      <div>
        <input type="radio" name="villageois" value="villageois-4" />
        <img for="huey" src="../assets/img/villageois-4.png" />
      </div>
    </fieldset>
    <br />
    <button @click="changeImage()">Modifié l'image</button>
  </div>
</template>

<script setup>
import { useResourcesStore } from '@/stores/resources'
import { useUserStore } from '@/stores/user'
import { computed, ref, watch } from 'vue'

const storeResources = useResourcesStore()
const storeUser = useUserStore()

let player = ref(null)

const emit = defineEmits(['msgImage'])

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

async function changeImage() {
  storeUser.img = document.querySelector('input[type="radio"]:checked').value;
  let login = storeUser.login
  let body = {
    species: null,
    password: null,
    image: document.querySelector('input[type="radio"]:checked').value
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
        emit('msgImage', 'Image modifié')
      } else {
        emit('msg', 'Erreur lors de la modification')
      }
    })
    .catch((err) => {
      emit('msg', 'Erreur requete')
    })
}

watch(
  () => storeResources.resources,
  () => {
    majPlayer()
  }
)

majPlayer()
</script>

<style scoped>
input,
input[type='submit'],
select {
  color: grey;
  border: 1px solid;
}

fieldset {
  display: flex;
}

fieldset div {
  display: flex;
  flex-direction: column;
  margin: 10px;
}
</style>
