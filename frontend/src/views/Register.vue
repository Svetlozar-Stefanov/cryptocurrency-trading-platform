<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const name = ref('')
const email = ref('')
const password = ref('')

const router = useRouter()

async function handleRegister() {
  try {
    const response = await fetch('http://localhost:8080/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        name: name.value,
        email: email.value,
        password: password.value,
      }),
      credentials: 'include'
    })

    if (!response.ok) {
      const errorData = await response.json();
      console.error('Registration failed:', errorData.error);
      alert(`Error: ${errorData.error}`);
      return;
    }

    const data = await response.json();
    console.log('Registration successful:', data.message);

    await router.push('/login');
  } catch (err) {
    console.error('Network or unexpected error:', err);
    alert('Registration failed: could not reach server. Please try again later.');
  }
}
</script>

<template>
  <div class="register-page">
    <form class="register-form" @submit.prevent="handleRegister">
      <h2>Register</h2>
      <input type="text" v-model="name" placeholder="Username" required />
      <input type="email" v-model="email" placeholder="Email" required />
      <input type="password" v-model="password" placeholder="Password" required />
      <button type="submit">Register</button>
      <p class="switch-auth">
        Already have an account?
        <router-link to="/login">Login here</router-link>
      </p>
    </form>
  </div>
</template>

<style scoped>
.register-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f7f7f7;
}

.register-form {
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

.register-form h2 {
  margin-bottom: 1rem;
}

.register-form input {
  padding: 0.5rem;
  font-size: 1rem;
}

.register-form button {
  padding: 0.5rem;
  font-size: 1rem;
  cursor: pointer;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
}

.register-form button:hover {
  background-color: #369f77;
}

.switch-auth {
  font-size: 0.9rem;
}

.switch-auth a {
  color: #42b983;
  text-decoration: none;
}
</style>