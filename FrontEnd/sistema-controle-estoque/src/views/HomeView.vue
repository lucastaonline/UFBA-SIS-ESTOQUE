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
  <div class="container-fluid">
    <div>Login feito</div>
    <div>
      <button class="btn btn-primary" v-on:click="logout">Logout</button>
    </div>
  </div>
</template>
