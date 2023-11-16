import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import RegisterView from '@/views/RegisterView.vue'
import ProductsView from '@/views/ProductsView.vue'
import ProductsFormView from '@/views/ProductsFormView.vue'
import TransactionsView from '@/views/TransactionsView.vue'
import TransactionsFormView from '@/views/TransactionsFormView.vue'
import { useAuthStore } from '@/stores/auth'
import { useToastStore } from '@/stores/toast'

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
      component: LoginView,
      props: (route) => ({ sessionTimeout: route.query.sessionTimeout == 'true' })
    },
    {
      path: '/products',
      name: 'products',
      component: ProductsView,
      meta: {
        auth: true
      }
    },
    {
      path: '/products-form/:productId?',
      props: true,
      name: 'products-form',
      component: ProductsFormView,
      meta: {
        auth: true
      }
    },
    {
      path: '/transactions',
      name: 'transactions',
      component: TransactionsView,
      meta: {
        auth: true
      }
    },
    {
      path: '/transactions-form',
      name: 'transactions-form',
      component: TransactionsFormView,
      meta: {
        auth: true
      }
    }
  ]
})

router.beforeEach(async (to, from, next) => {
  if (to.meta?.auth) {
    const authStore = useAuthStore()
    const toastStore = useToastStore()

    if (authStore.token) {
      const isAuthenticated = await authStore.checkToken()

      if (isAuthenticated) {
        next()
      } else {
        next({ name: 'login', query: { sessionTimeout: 'true' } })
      }
    } else next({ name: 'login' })
  } else {
    next()
  }
})

export default router
