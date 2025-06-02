<script setup>
import { onMounted, computed } from 'vue';
import { useRoute, RouterView } from 'vue-router'
import { useAuthStore } from './stores/auth'
import Navbar from "@/components/Navbar.vue";

const route = useRoute()
const auth = useAuthStore()

onMounted( () => {
  auth.checkSession()
});

const showNavbar = computed(() => {
  return !['/login', '/register'].includes(route.path)
})
</script>

<template>
  <Navbar v-if="showNavbar" />
  <RouterView />
</template>

<style scoped>
</style>
