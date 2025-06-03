<script setup lang="ts">
import {ref, onMounted, onUnmounted, computed} from 'vue'

const tickerData = ref({})

const nameMap = {
  'XBT/USD': 'Bitcoin',
  'ETH/USD': 'Ethereum',
  'USDT/USD': 'Tether',
  'BNB/USD': 'BNB',
  'SOL/USD': 'Solana',
  'XRP/USD': 'XRP',
  'ADA/USD': 'Cardano',
  'DOGE/USD': 'Dogecoin',
  'TON/USD': 'Toncoin',
  'AVAX/USD': 'Avalanche',
  'DOT/USD': 'Polkadot',
  'LINK/USD': 'Chainlink',
  'MATIC/USD': 'Polygon',
  'LTC/USD': 'Litecoin',
  'TRX/USD': 'TRON',
  'SHIB/USD': 'Shiba Inu',
  'ICP/USD': 'Internet Computer',
  'XLM/USD': 'Stellar',
  'UNI/USD': 'Uniswap',
  'ATOM/USD': 'Cosmos',
}

let ws = null

onMounted(() => {
  ws = new WebSocket('http://localhost:8080/market')

  ws.onmessage = (event) => {
    const data = JSON.parse(event.data)
    for (const entry of data) {
      tickerData.value[entry.symbol] = entry
    }
  }

  ws.onerror = (err) => {
    console.error('WebSocket error:', err)
  }
})

onUnmounted(() => {
  if (ws) ws.close()
})

const sortedSymbols = computed(() =>
    Object.keys(tickerData.value).sort((a, b) => {
      const priceA = parseFloat(tickerData.value[a]?.last || 0)
      const priceB = parseFloat(tickerData.value[b]?.last || 0)
      return priceB - priceA // descending
    })
)
</script>

<template>
  <div class="container">
    <h1 class="header">Live Crypto Prices</h1>
    <table class="price-table">
      <thead>
      <tr>
        <th>Symbol</th>
        <th>Name</th>
        <th>Price (USD)</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="symbol in sortedSymbols" :key="symbol">
        <td>{{ symbol }}</td>
        <td>{{ nameMap[symbol] || symbol }}</td>
        <td>${{ tickerData[symbol].last }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.container {
  width: 90%;
  max-width: 1200px;
  margin: 0 auto;
  font-family: Arial, sans-serif;
  padding: 20px;
}

.header {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 16px;
  text-align: center;
}

.price-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
  background-color: #fff;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.1);
  table-layout: fixed;
}

.price-table th,
.price-table td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #eaeaea;
}

.price-table th {
  background-color: #f6f6f6;
  font-weight: 600;
}

.price-table tr:hover {
  background-color: #f0f8ff;
}
</style>