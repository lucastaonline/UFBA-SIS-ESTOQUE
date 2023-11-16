<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'

const router = useRouter()

const authStore = useAuthStore()

function logout() {
  authStore.clear()
  router.push({ name: 'login' })
}
</script>
<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid">
        <span class="navbar-brand">Controle de estoque | VitaSaúde</span>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <RouterLink
                to="/"
                :class="['nav-link', router.currentRoute.value.name == 'home' ? 'active' : '']"
              >
                Home
              </RouterLink>
            </li>
            <li class="nav-item">
              <RouterLink
                to="/products"
                :class="['nav-link', router.currentRoute.value.name == 'home' ? 'active' : '']"
              >
                Produtos
              </RouterLink>
            </li>
            <li class="nav-item">
              <RouterLink
                to="/transactions"
                :class="['nav-link', router.currentRoute.value.name == 'home' ? 'active' : '']"
              >
                Movimentação de estoque
              </RouterLink>
            </li>
          </ul>
          <button class="btn btn-primary" v-on:click="logout">Logout</button>
        </div>
      </div>
    </nav>
    <div>
      <slot></slot>
    </div>
  </div>
</template>
<style></style>
