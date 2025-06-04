<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'
import Transactions from "@/components/Transactions.vue";
import {onMounted, ref, watch} from "vue";
import {useRoute} from "vue-router";
import Assets from "@/components/Assets.vue";

const route = useRoute();
const refreshKey = ref(0)

const auth = useAuthStore()

onMounted(fetchData)
watch(() => route.fullPath, fetchData)

function fetchData() {
  auth.checkSession()
  refreshKey.value++
}
</script>

<template>
  <div class="portfolio-container">
    <div class="header">
      <h2>{{ auth.currentUser.name }},</h2>
      <p>Balance: ${{ auth.currentUser.balance }}</p>
    </div>

    <div class="section">
      <Assets :refresh-key="refreshKey"/>
    </div>

    <div class="section">
      <Transactions :refresh-key="refreshKey"/>
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