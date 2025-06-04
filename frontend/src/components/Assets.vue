<script setup lang="ts">
import {onMounted, onUnmounted, ref, watch} from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const props = defineProps({ refreshKey: Number })

const assets = ref([])
const currentPrices = ref({})

let ws = null

async function fetchData() {
  // Get assets in portfolio
  try {
    const response = await fetch('http://localhost:8080/assets', {
      credentials: 'include',
    })
    if (!response.ok) throw new Error('Failed to fetch assets')
    assets.value = await response.json()
    console.log(assets.value)
  } catch (err) {
    console.error('Error loading assets:', err)
  }

  // Prompt last prices
  try {
    for (const asset of assets.value) {
      const symbol = asset.symbol
      const formattedSymbol = symbol.replace("/", "")

      const response = await fetch(`https://api.kraken.com/0/public/Ticker?pair=${formattedSymbol}`)
      if (!response.ok) throw new Error('Failed to fetch initial prices')

      const data = await response.json()
      const key = Object.keys(data.result)[0]

      currentPrices.value[symbol] = data.result[key].c[0]
    }
  } catch (err) {
    console.error('Error loading assets:', err)
  }

  ws = new WebSocket('http://localhost:8080/market')

  ws.onmessage = (event) => {
    const data = JSON.parse(event.data)
    for (const entry of data) {
      currentPrices.value[entry.symbol] = entry.last
    }
  }

  ws.onerror = (err) => {
    console.error('WebSocket error:', err)
  }
}

onMounted(fetchData)
watch(() => props.refreshKey, fetchData)

onUnmounted(() => {
  if (ws) ws.close()
})

function sell(symbol) {
  const price = currentPrices.value[symbol]
  let max_quantities = 0
  for (const entry of assets.value) {
    if (entry.symbol === symbol) {
      max_quantities = entry.quantity
    }
  }
  router.push({ name: 'sell', query: { symbol, price, max_quantities } })
}
</script>

<template>
  <div class="portfolio-container">
    <h3>Your Portfolio</h3>
    <table class="portfolio-table">
      <thead>
      <tr>
        <th>Symbol</th>
        <th>Quantity</th>
        <th>Avg. Price</th>
        <th>Current Price</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="entry in assets" >
        <td>{{ entry.symbol }}</td>
        <td>{{ entry.quantity.toFixed(6) }}</td>
        <td>${{ entry.avg_price.toFixed(2) }}</td>
        <td>
          <span v-if="currentPrices[entry.symbol]">
            ${{ currentPrices[entry.symbol] }}
          </span>
          <span v-else>Loading...</span>
        </td>
        <td>
          <span v-if="currentPrices[entry.symbol]">
            <button @click="sell(entry.symbol)" class="sell-button">Sell</button>
          </span>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.portfolio-container {
  margin-top: 30px;
  padding: 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background: #fafafa;
  max-width: 800px;
  margin-left: auto;
  margin-right: auto;
}

.portfolio-table {
  width: 100%;
  border-collapse: collapse;
}

.portfolio-table th,
.portfolio-table td {
  padding: 12px 16px;
  border-bottom: 1px solid #ccc;
  text-align: left;
}

.portfolio-table thead {
  background-color: #f0f0f0;
}

.sell-button {
  padding: 6px 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.sell-button:hover {
  background-color: #0056b3;
}
</style>