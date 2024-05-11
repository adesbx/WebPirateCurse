<script setup>
import { useResourcesStore } from '@/stores/resources'
import { useUserStore } from '@/stores/user'
import { watch } from 'vue'

const storeResources = useResourcesStore()
const storeUser = useUserStore()

let player

function majPlayer() {
  storeResources.resources.forEach((resource) => {
    if (resource.id === storeUser.login) {
      player = resource
    }
  })
}
majPlayer()
watch(
  () => storeResources.resources,
  () => {
    majPlayer()
  }
)
</script>

<template>
	<h2>Information Perso</h2><br />
	<br /> <p><strong>Name: </strong>{{ player.id }}</p>
	<br /> <p><strong>Role: </strong>{{ player.role }}</p>
	<br /> <p><strong>Position:</strong> {{ player.position }}</p>
	<br /> <p><strong>Nombre de potions: </strong>{{ player.potions }}</p>
	<br /> <p><strong>Ttl: </strong>{{ player.ttl }}</p>
	<br /> <p><strong>Terminated: </strong>{{ player.terminated }}</p>
	<br /> <p><strong>Turned:</strong>{{ player.turned }}</p>
</template>