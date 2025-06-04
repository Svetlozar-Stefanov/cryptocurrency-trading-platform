<script setup lang="ts">
import {ref, onMounted, watch} from 'vue'

const props = defineProps({ refreshKey: Number })

const transactions = ref([])

async function fetchData() {
  try {
    const response = await fetch('http://localhost:8080/transactions', {
      credentials: 'include',
    })
    if (!response.ok) throw new Error('Failed to fetch transactions')
    transactions.value = await response.json()
  } catch (err) {
    console.error('Error loading transactions:', err)
  }
}

onMounted(fetchData)
watch(() => props.refreshKey, fetchData)
</script>

<template>
  <div class="transactions-container">
    <h3>Transaction History</h3>
    <div class="table-scroll">
      <table class="transactions-table">
        <thead>
        <tr>
          <th>Date</th>
          <th>Symbol</th>
          <th>Type</th>
          <th>Amount</th>
          <th>Price</th>
          <th>Total</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="tx in transactions" :key="tx.id">
          <td>{{ tx.timestamp }}</td>
          <td>{{ tx.symbol }}</td>
          <td :class="tx.type === 'BUY' ? 'buy' : 'sell'">{{ tx.type }}</td>
          <td>{{ tx.quantity }}</td>
          <td>${{ tx.price.toFixed(2) }}</td>
          <td>${{ (tx.quantity * tx.price).toFixed(2) }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.transactions-container {
  margin-top: 20px;
}

.table-scroll {
  max-height: 300px;
  overflow-y: auto;
  overflow-x: auto;
  border: 1px solid #ccc;
  border-radius: 6px;
}

.transactions-table {
  width: 100%;
  border-collapse: collapse;
  min-width: 600px;
}

.transactions-table th,
.transactions-table td {
  padding: 12px 16px;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
  white-space: nowrap;
}

.transactions-table thead {
  background-color: #f2f2f2;
  position: sticky;
  top: 0;
}

.buy {
  color: green;
  font-weight: bold;
}

.sell {
  color: red;
  font-weight: bold;
}
</style>