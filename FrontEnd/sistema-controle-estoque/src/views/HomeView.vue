<script setup lang="ts">
import MainLayoutComponentVue from '@/components/MainLayoutComponent.vue'
import type { Product } from '@/types/product'
import { computed, onMounted, ref } from 'vue'
import httpClient from '@/services/http-client'
import { useAuthStore } from '@/stores/auth'
import type { AxiosError, AxiosResponse } from 'axios'
import { useToastStore } from '@/stores/toast'
import { useRouter } from 'vue-router'

const products = ref<Product[]>([])
const authStore = useAuthStore()
const toastStore = useToastStore()
const router = useRouter()

onMounted(() => {
  httpClient
    .get('products', {
      headers: {
        Authorization: authStore.token
      }
    })
    .then((response: AxiosResponse<Product[]>) => {
      if (response.status == 200) {
        products.value = response.data
      } else {
        toastStore.showMessage(
          'danger',
          'Erro!',
          'Não foi possível retornar o estado atual do estoque.'
        )
      }
    })
    .catch((error: AxiosError) => {
      console.log(error)

      if (error.response?.status == 401)
        router.push({ name: 'login', query: { sessionTimeout: 'true' } })
      else
        toastStore.showMessage(
          'danger',
          'Erro!',
          'Não foi possível retornar o estado atual do estoque.'
        )
    })
})

const sortedProducts = computed(() => {
  const sortFunction = (a: Product, b: Product) => {
    if (a.name.toLowerCase() > b.name.toLowerCase()) {
      return 1
    } else if (a.name.toLowerCase() < b.name.toLowerCase()) {
      return -1
    }

    return 0
  }

  return products.value.sort(sortFunction)
})
</script>

<template>
  <MainLayoutComponentVue>
    <div class="container-lg">
      <div class="y-5">
        <div class="row">
          <h1 class="fw-light">Estoque atual</h1>
        </div>
      </div>
      <table class="table">
        <thead>
          <tr>
            <th>Produto</th>
            <th>Quantidade em estoque</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="produto in sortedProducts">
            <td>{{ produto.name }}</td>
            <td>{{ produto.stock }} unidades</td>
          </tr>
        </tbody>
      </table>
    </div>
  </MainLayoutComponentVue>
</template>
