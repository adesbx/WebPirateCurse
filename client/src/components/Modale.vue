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

const sensor = new AmbientLightSensor({ frequency: 60 });
const seuil = 50;

sensor.addEventListener('reading', () => {

  if (sensor.illuminance < seuil ) {
    document.documentElement.style.setProperty('--color-background', 'var(--vt-c-black)');
    document.documentElement.style.setProperty('--color-background-soft', 'var(--vt-c-black-soft)');
    document.documentElement.style.setProperty('--color-background-mute', 'var(--vt-c-black-mute)');

    document.documentElement.style.setProperty('--color-border', 'var(--vt-c-divider-dark-2)');
    document.documentElement.style.setProperty('--color-border-hover', 'var(--vt-c-divider-dark-1)');

    document.documentElement.style.setProperty('--color-heading', 'var(--vt-c-text-dark-1)');
    document.documentElement.style.setProperty('--color-text', 'var(--vt-c-text-dark-2)');
  } else {
    document.documentElement.style.setProperty('--color-background', 'var(--vt-c-white)');
    document.documentElement.style.setProperty('--color-background-soft', 'var(--vt-c-white-soft)');
    document.documentElement.style.setProperty('--color-background-mute', 'var(--vt-c-white-mute)');

    document.documentElement.style.setProperty('--color-border', 'var(--vt-c-divider-light-2)');
    document.documentElement.style.setProperty('--color-border-hover', 'var(--vt-c-divider-light-1)');

    document.documentElement.style.setProperty('--color-heading', 'var(--vt-c-text-light-1)');
    document.documentElement.style.setProperty('--color-text', 'var(--vt-c-text-light-1)');
  }
});

sensor.start();
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
