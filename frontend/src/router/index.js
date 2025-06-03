import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from "@/views/Register.vue";
import BuyForm from "@/views/BuyForm.vue";
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
      path: '/buy',
      name: 'buy',
      component: BuyForm,
    },
    {
      path: '/portfolio',
      name: 'portfolio',
      component: Portfolio,
    }
  ],
})

export default router
