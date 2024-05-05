<script setup>
import ModifyPassword from '../components/ModifyPassword.vue'
import { ref } from 'vue'

const message = ref("");
</script>

<template>
  <main>
   <h1>Bonjour, bienvenu sur votre profil !!</h1>
   <ModifyPassword @msg="message = $event" />
   <h2>{{ message }}</h2>
  </main>
</template>
