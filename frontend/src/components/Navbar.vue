<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()

function logout() {
  alert('Logging out...')
  // Add actual logout logic here
}
</script>

<template>
  <header class="navbar">
    <div class="logo">Cryptocurrency Platform</div>
    <div class="auth-section">
      <template v-if="auth.isLoggedIn">
        <span class="welcome">Welcome, {{ auth.currentUser.name }}</span>
        <button class="logout-button" @click="logout">Logout</button>
      </template>
      <template v-else>
        <router-link class="login-link" to="/login">Not logged in? Login here</router-link>
      </template>
    </div>
  </header>
</template>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background-color: #42b983;
  color: white;
  font-size: 1rem;
}

.logo {
  font-weight: bold;
  font-size: 1.3rem;
}

.auth-section {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.welcome {
  font-weight: 500;
}

.login-link {
  color: white;
  text-decoration: none;
  font-weight: 500;
}

.login-link:hover {
  text-decoration: underline;
}

.logout-button {
  background-color: white;
  color: #42b983;
  border: none;
  padding: 0.5rem 1rem;
  font-weight: bold;
  border-radius: 999px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.logout-button:hover {
  background-color: #2c9c72;
  color: white;
}
</style>