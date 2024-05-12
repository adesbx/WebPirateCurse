<script setup>
import ModifyPassword from '../components/ModifyPassword.vue'
import ModifyImage from '../components/ModifyImage.vue'

import { ref } from 'vue'

const message = ref('')
const messageImage = ref('')

</script>

<template>
  <main>
    <h1>Bonjour, bienvenu sur votre profil !!</h1>
    <ModifyPassword @msg="message = $event" />
    <h2>{{ message }}</h2>
    <ModifyImage @msgImage="messageImage = $event" />
    <h2>{{ messageImage }}</h2>
  </main>
</template>
