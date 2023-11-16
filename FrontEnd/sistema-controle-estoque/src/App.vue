<script setup lang="ts">
import { RouterView, useRouter } from 'vue-router'
import { useToastStore } from '@/stores/toast'

const toastStore = useToastStore()
</script>

<template>
  <router-view v-slot="{ Component, route }">
    <transition appear>
      <component :is="Component" :key="route.path" />
    </transition>
  </router-view>
  <div class="toast-container position-fixed bottom-0 end-0 p-3">
    <div
      id="toastSite"
      :class="['toast', `text-bg-${toastStore.informacoesToast.type}`]"
      role="alert"
      aria-live="assertive"
      aria-atomic="true"
      data-bs-autohide="false"
    >
      <div class="toast-header">
        <strong class="me-auto">{{ toastStore.informacoesToast.title }}</strong>
        <small>agora</small>
        <button
          type="button"
          class="btn-close"
          aria-label="Close"
          v-on:click="() => toastStore.setOpened(false)"
        ></button>
      </div>
      <div class="toast-body">{{ toastStore.informacoesToast.message }}</div>
    </div>
  </div>
</template>

<style scoped>
.v-enter-active,
.v-leave-active {
  transition: opacity 0.4s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}

.v-leave-to {
  position: absolute;
  width: 100%;
}
</style>
