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
        <a class="navbar-brand" href="#">Controle de estoque | VitaSaúde</a>
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
              <a
                :class="['nav-link', router.currentRoute.value.name == 'home' ? 'active' : '']"
                href="/"
                >Home</a
              >
            </li>
            <li class="nav-item">
              <a
                :class="['nav-link', router.currentRoute.value.name == 'products' ? 'active' : '']"
                href="/products"
                >Produtos</a
              >
            </li>
            <li class="nav-item">
              <a
                :class="[
                  'nav-link',
                  router.currentRoute.value.name == 'transactions' ? 'active' : ''
                ]"
                href="/transactions"
                >Movimentação de estoque</a
              >
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
