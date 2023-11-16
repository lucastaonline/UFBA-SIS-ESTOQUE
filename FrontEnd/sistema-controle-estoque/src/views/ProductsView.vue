<script setup lang="ts">
import MainLayoutComponentVue from '@/components/MainLayoutComponent.vue'
import type { Product } from '@/types/product'
import { computed, onMounted, ref } from 'vue'
import httpClient from '@/services/http-client'
import { useAuthStore } from '@/stores/auth'
import type { Axios, AxiosError, AxiosResponse } from 'axios'
import { useToastStore } from '@/stores/toast'
import { PRODUCT_CATEGORY, PRODUCT_CATEGORY_LABEL } from '@/enums/product_category'
import { useRouter } from 'vue-router'

const products = ref<Product[]>([])
const authStore = useAuthStore()
const toastStore = useToastStore()
const router = useRouter()

onMounted(() => {
  getProducts()
})

function getProducts() {
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
          'Não foi possível retornar os produtos cadastrados.'
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
          'Não foi possível retornar os produtos cadastrados.'
        )
    })
}

function deleteProduct(id: string) {
  if (confirm('Tem certeza de que deseja excluir este produto?')) {
    httpClient
      .delete(`products/${id}`, {
        headers: {
          Authorization: authStore.token
        }
      })
      .then((response: AxiosResponse) => {
        if (response.status == 200) {
          toastStore.showMessage('success', 'Sucesso!', 'O produto foi deletado com sucesso.')
          getProducts()
        } else {
          toastStore.showMessage('danger', 'Erro!', 'Ocorreu um erro ao tentar deletar o produto.')
        }
      })
      .catch((error) => {
        console.log(error)
        if (error.response?.status == 401)
          router.push({ name: 'login', query: { sessionTimeout: 'true' } })
        else
          toastStore.showMessage(
            'danger',
            'Erro!',
            `Ocorreu um erro ao tentar deletar o produto: ${error.response?.data?.message}.`
          )
      })
  }
}

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
          <h1 class="fw-light">
            Produtos
            <RouterLink
              to="/products-form"
              :class="['btn btn-primary', authStore.userIsAdmin() ? '' : 'disabled-link']"
            >
              <font-awesome-icon :icon="['fas', 'fa-plus']" />
            </RouterLink>
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
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="product in sortedProducts">
            <td>{{ product.name }}</td>
            <td>{{ product.stock }} unidades</td>
            <td style="white-space: nowrap">{{ product.price.toFixed(2) }} R$</td>
            <td>{{ product.description }}</td>
            <td>{{ PRODUCT_CATEGORY_LABEL.get(Number(PRODUCT_CATEGORY[product.categories])) }}</td>
            <td>
              <RouterLink
                :to="`/products-form/${product.id}`"
                :class="['btn btn-primary', authStore.userIsAdmin() ? '' : 'disabled-link']"
              >
                <font-awesome-icon :icon="['fas', 'fa-edit']" />
              </RouterLink>
            </td>
            <td>
              <button
                class="btn btn-danger"
                v-on:click="() => deleteProduct(product.id)"
                :disabled="!authStore.userIsAdmin()"
              >
                <font-awesome-icon :icon="['fas', 'fa-times']" />
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </MainLayoutComponentVue>
</template>

<style scoped>
.disabled-link {
  opacity: 0.65;
  pointer-events: none;
}
</style>
