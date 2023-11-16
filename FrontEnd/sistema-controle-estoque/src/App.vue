<script setup lang="ts">
import { RouterView, useRouter } from 'vue-router'
import { useToastStore } from '@/stores/toast'

const toastStore = useToastStore()
</script>

<template>
  <router-view v-slot="{ Component, route }">
    <transition name="slide-fade">
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
.slide-fade-enter-active {
  transition: all 1.2s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from {
  transform: translateX(20px);
  opacity: 0;
}
.slide-fade-leave-to {
  transform: translateX(20px);
  opacity: 0;
}
</style>
