<!-- src/App.vue -->
<template>
  <div id="app">
    <Navbar />
    <router-view />
  </div>
</template>

<script>
import Navbar from "./components/Navbar.vue";
import { store } from "./store.js";

export default {
  name: "App",
  components: {
    Navbar,
  },
  setup() {
    return { store };
  },
};
</script>

<style>
/* Your existing styles */
</style>
