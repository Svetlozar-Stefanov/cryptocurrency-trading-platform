import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from "@/views/Register.vue";
import TradeForm from "@/views/TradeForm.vue";
import Portfolio from "@/views/Portfolio.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/register',
      name: 'register',
      component: Register,
    },
    {
      path: '/trade',
      name: 'trade',
      component: TradeForm,
    },
    {
      path: '/portfolio',
      name: 'portfolio',
      component: Portfolio,
    }
  ],
})

export default router
