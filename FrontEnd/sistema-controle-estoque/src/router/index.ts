import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import RegisterView from '@/views/RegisterView.vue'
import ProductsView from '@/views/ProductsView.Vue'
import ProductsFormView from '@/views/ProductsFormView.vue'
import { useAuthStore } from '@/stores/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      meta: {
        auth: true
      }
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/products',
      name: 'products',
      component: ProductsView
    },
    {
      path: '/products-form/:productId',
      props: true,
      name: 'products-form',
      component: ProductsFormView
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.meta?.auth) {
    const authStore = useAuthStore()

    if (authStore.token) {
      const isAuthenticated = authStore.checkToken()

      if (isAuthenticated) next()
      else next({ name: 'login' })
    } else next({ name: 'login' })
  } else {
    next()
  }
})

export default router
