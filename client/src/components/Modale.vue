<template>
  <div v-if="visible" class="notification">
    <p>{{ message }}</p>
    <button @click="closeNotification">Close</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  message: {
    type: String,
    required: true,
  },
  visible: {
    type: Boolean,
    required: true,
  }
})

const emit = defineEmits(['close'])

function closeNotification() {
  emit('close')
}

</script>

<style scoped>
.notification {
  display: flex;
  justify-content: center;
  align-items: center;

  background-color: #f0f0f0;
  color: black;
  padding: 20px 30px;
  border: 1px solid #ccc;
  border-radius: 5px;

  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  z-index: 1000;
}
</style>
