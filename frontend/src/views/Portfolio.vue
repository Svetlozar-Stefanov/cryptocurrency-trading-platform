<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'
const auth = useAuthStore()

const name = auth.currentUser.name
const balance = auth.currentUser.balance
</script>

<template>
  <div class="portfolio-container">
    <div class="header">
      <h2>{{ name }},</h2>
      <p>Balance: ${{ balance }}</p>
    </div>

    <div class="section">
      <h3>Your Portfolio</h3>
      <!-- Portfolio content goes here -->
      <div class="placeholder">No data yet.</div>
    </div>

    <div class="section">
      <h3>Transaction History</h3>
      <!-- Transaction content goes here -->
      <div class="placeholder">No data yet.</div>
    </div>
  </div>
</template>

<style scoped>
.portfolio-container {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

.header {
  text-align: left;
  margin-bottom: 30px;
}

.section {
  margin-top: 30px;
  background: #f9f9f9;
  padding: 16px;
  border-radius: 8px;
}

.placeholder {
  color: #888;
  font-style: italic;
  padding: 8px 0;
}
</style>