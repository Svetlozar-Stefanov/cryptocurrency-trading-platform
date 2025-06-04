<script setup lang="ts">
import {ref, onMounted, onUnmounted, computed} from 'vue'
import {useRouter} from "vue-router";
import { useAuthStore } from '@/stores/auth'

const router = useRouter();
const auth = useAuthStore()

const tickerData = ref({})
const currentPrices = ref({})

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

onMounted(async () => {
  try {
    for (const symbol of Object.keys(nameMap)) {
      const formattedSymbol = symbol.replace("/", "")
      console.log(formattedSymbol)

      const response = await fetch(`https://api.kraken.com/0/public/Ticker?pair=${formattedSymbol}`)
      if (!response.ok) throw new Error('Failed to fetch initial prices')

      const data = await response.json()
      console.log(data)
      console.log(data.result)
      if (data.result != null) {
        const key = Object.keys(data.result)[0]

        currentPrices.value[symbol] = data.result[key].c[0]
        tickerData.value[symbol] = {
          symbol: symbol,
          last: currentPrices.value[symbol]
        }
      }
    }
  } catch (err) {
    console.error('Error loading assets:', err)
  }

  ws = new WebSocket('http://localhost:8080/market')

  ws.onmessage = (event) => {
    const data = JSON.parse(event.data)
    for (const entry of data) {
      tickerData.value[entry.symbol] = entry
      currentPrices.value[entry.symbol] = entry.last
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

function buy(symbol) {
  const price = tickerData.value[symbol].last
  router.push({ name: 'buy', query: { symbol, price } })
}
</script>

<template>
  <div class="container">
    <h1 class="header">Live Crypto Prices</h1>
    <div class="table-scroll">
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
          <td>
            <span v-if="currentPrices[symbol]">
              ${{ currentPrices[symbol] }}
            </span>
            <span v-else>Loading...</span>
          </td>
          <td v-if="auth.isLoggedIn">
            <button @click="buy(symbol)" class="buy-button">Buy</button>
          </td>
      </tr>
      </tbody>
    </table>
    </div>
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

.table-scroll {
  max-height: 600px;
  overflow-y: auto;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.buy-button {
  padding: 6px 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.buy-button:hover {
  background-color: #0056b3;
}
</style>