<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()

const email = ref('')
const password = ref('')

const router = useRouter()

async function handleLogin() {
  try {
    const response = await fetch('http://localhost:8080/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: email.value,
        password: password.value,
      }),
      credentials: 'include'
    })

    if (!response.ok) {
      const errorData = await response.json();
      console.error('Login unsuccessful:', errorData.error);
      alert(`Login unsuccessful: ${errorData.error}`);
      return;
    }

    const data = await response.json();
    console.log('Login successful:', data.message);

    await auth.checkSession()
    await router.push('/');
  } catch (err) {
    console.error('Network or unexpected error:', err);
    alert('Login unsuccessful: could not reach server. Please try again later.');
  }
}
</script>

<template>
  <div class="login-page">
    <form class="login-form" @submit.prevent="handleLogin">
      <h2>Login</h2>
      <input
          type="email"
          v-model="email"
          placeholder="Email"
          required
      />
      <input
          type="password"
          v-model="password"
          placeholder="Password"
          required
      />
      <button type="submit">Login</button>
      <p class="switch-auth">
        Don't have an account?
        <router-link to="/register">Register here</router-link>
      </p>
    </form>
  </div>
</template>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f7f7f7;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 2rem;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  width: 300px;
  text-align: center;
}

.login-form h2 {
  margin-bottom: 1rem;
}

.login-form input {
  padding: 0.5rem;
  font-size: 1rem;
}

.login-form button {
  padding: 0.5rem;
  font-size: 1rem;
  cursor: pointer;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
}

.login-form button:hover {
  background-color: #369f77;
}

.switch-auth a {
  color: #42b983;
  text-decoration: none;
}
</style>