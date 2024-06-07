<script setup>
import { useResourcesStore } from '@/stores/resources'
import { useUserStore } from '@/stores/user'
import { computed, ref, watch } from 'vue'

const storeResources = useResourcesStore()
const storeUser = useUserStore()

let player = ref(null)

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

watch(
  () => storeResources.resources,
  () => {
    majPlayer()
  }
)

majPlayer()
</script>

<template style="">
  <h2>Information Perso</h2>
  <div v-if="player">
    <p><strong>Name: </strong>{{ player.id }}</p>
    <p><strong>Role: </strong>{{ player.role }}</p>
    <p><strong>Position:</strong> {{ player.position }}</p>
    <p><strong>Nombre de potions: </strong>{{ player.potions }}</p>
    <p><strong>Ttl: </strong>{{ player.ttl }}</p>
    <p><strong>Terminated: </strong>{{ player.terminated }}</p>
    <p><strong>Turned:</strong>{{ player.turned }}</p>
  </div>
</template>