<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { useUserStore } from '@/stores/user'

const storeUser = useUserStore()
if ("AmbientLightSensor" in window) {
  const sensor = new AmbientLightSensor();
  const seuil = 50;

  sensor.addEventListener('reading', () => {

    if (sensor.illuminance < seuil) {
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
  sensor.addEventListener("error", (event) => {
    console.log(event.error.name, event.error.message);
  });

  sensor.start();
}
</script>

<template>
  <header>
    <!-- <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="125" height="125" /> -->

    <div class="wrapper">
      <!-- <HelloWorld msg="You did it!" /> -->

      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink v-if="storeUser.connected" to="/profil">Profil</RouterLink>
        <RouterLink to="/about">About</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView @logoutEvent="storeUser.connected = false" />
</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    display: flex;
    flex-direction: row;
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
