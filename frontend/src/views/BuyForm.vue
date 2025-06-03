<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'
import { ref, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
const route = useRoute()
const router = useRouter()
const auth = useAuthStore()

const user = auth.currentUser

const symbol = ref(route.query.symbol)
const price = ref(parseFloat(route.query.price))

const quantity = ref(0)
const balance = user.balance

const totalCost = computed(() => quantity.value * price.value)

function confirmTrade() {
  if (totalCost.value > balance.value) {
    alert('Insufficient balance!')
    return
  }
  alert(`Purchased ${quantity.value} of ${symbol.value} for $${totalCost.value.toFixed(2)}`)

  buy(symbol.value, price.value, quantity.value)

  router.push("/")
}

async function buy(symbol, price, quantity) {
  try {
    const response = await fetch('http://localhost:8080/buy', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        symbol: symbol,
        price: price,
        quantity: quantity
      }),
      credentials: 'include'
    })

    if (!response.ok) {
      const errorData = await response.json();
      console.error('Transaction failed:', errorData.error);
      alert(`Transaction failed: ${errorData.error}`);
      return;
    }

    const data = await response.json();
    console.log('Transaction successful', data.message);
  } catch (err) {
    console.error('Network or unexpected error:', err);
    alert('Login unsuccessful: could not reach server. Please try again later.');
  }
}

function cancelTrade() {
  router.push("/")
}
</script>

<template>
  <div class="trade-container">
    <form class="trade-form" @submit.prevent="confirmTrade">
      <h2>Trade Cryptocurrency</h2>

      <div class="form-group">
        <label>Symbol</label>
        <div class="readonly-field">{{ symbol }}</div>
      </div>

      <div class="form-group">
        <label for="amount">Quantity</label>
        <input id="amount" v-model.number="quantity" type="number" min="0.01" step="0.01" required />
      </div>

      <div class="form-info">
        <p><strong>Balance:</strong> ${{ balance.toFixed(2) }}</p>
        <p><strong>Current Price:</strong> ${{ price }}</p>
        <p><strong>Final Cost:</strong> ${{ totalCost.toFixed(2) }}</p>
      </div>

      <div class="form-actions">
        <button type="submit" class="confirm-btn">Confirm</button>
        <button type="button" class="cancel-btn" @click="cancelTrade">Cancel</button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.trade-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #f4f7fa;
}

.trade-form {
  background: white;
  padding: 24px 32px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 400px;
}

.trade-form h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 16px;
}

.form-group label {
  display: block;
  margin-bottom: 6px;
  font-weight: bold;
}

.form-group input {
  width: 100%;
  padding: 8px;
  font-size: 14px;
  box-sizing: border-box;
}

.form-info {
  font-size: 14px;
  margin-bottom: 16px;
}

.form-actions {
  display: flex;
  justify-content: space-between;
}

.confirm-btn, .cancel-btn {
  padding: 8px 16px;
  border: none;
  font-weight: bold;
  cursor: pointer;
  width: 48%;
}

.confirm-btn {
  background-color: #28a745;
  color: white;
}

.cancel-btn {
  background-color: #dc3545;
  color: white;
}

.readonly-field {
  padding: 8px;
  background-color: #f1f1f1;
  border: 1px solid #ccc;
  border-radius: 4px;
}
</style>