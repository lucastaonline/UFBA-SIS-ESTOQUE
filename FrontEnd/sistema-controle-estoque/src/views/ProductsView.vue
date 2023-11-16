<script setup lang="ts">
import MainLayoutComponentVue from '@/components/MainLayoutComponent.vue'
import type { Produto } from '@/types/product'
import { onMounted, ref } from 'vue'
import httpClient from '@/services/http-client'
import { useAuthStore } from '@/stores/auth'
import type { AxiosResponse } from 'axios'
import { useToastStore } from '@/stores/toast'
import { PRODUCT_CATEGORY, PRODUCT_CATEGORY_LABEL } from '@/enums/product_category'

const produtos = ref<Produto[]>([])
const authStore = useAuthStore()
const toastStore = useToastStore()

onMounted(() => {
  httpClient
    .get('products', {
      headers: {
        Authorization: authStore.token
      }
    })
    .then((response: AxiosResponse<Produto[]>) => {
      if (response.status == 200) {
        produtos.value = response.data
      } else {
        toastStore.showMessage(
          'danger',
          'Erro!',
          'Não foi possível retornar o estado atual do estoque.'
        )
      }
    })
    .catch((error) => {
      console.log(error)

      toastStore.showMessage(
        'danger',
        'Erro!',
        'Não foi possível retornar o estado atual do estoque.'
      )
    })
})
</script>

<template>
  <MainLayoutComponentVue>
    <div class="container-lg">
      <div class="y-5">
        <div class="row">
          <h1 class="fw-light">
            Produtos
            <a class="btn btn-primary" href="/products-form"
              ><font-awesome-icon :icon="['fas', 'fa-plus']"
            /></a>
          </h1>
        </div>
      </div>
      <table class="table">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Quantidade em estoque</th>
            <th>Preço</th>
            <th>Descrição</th>
            <th>Categoria</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="produto in produtos">
            <td>{{ produto.name }}</td>
            <td>{{ produto.stock }} unidades</td>
            <td>{{ produto.price }} R$</td>
            <td>{{ produto.description }}</td>
            <td>{{ produto.categories }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </MainLayoutComponentVue>
</template>

<style></style>
