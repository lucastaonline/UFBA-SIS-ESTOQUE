<script setup lang="ts">
import MainLayoutComponentVue from '@/components/MainLayoutComponent.vue'
import { PRODUCT_CATEGORY, PRODUCT_CATEGORY_LABEL } from '@/enums/product_category'
import httpClient from '@/services/http-client'
import { useAuthStore } from '@/stores/auth'
import { useToastStore } from '@/stores/toast'
import type { Produto } from '@/types/product'
import type {
  PersistProductRequest,
  PersistProductResponse
} from '@/types/requisition_models/products_requisitions'
import type { AxiosResponse } from 'axios'
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps(['productId'])

const authStore = useAuthStore()
const toastStore = useToastStore()
const router = useRouter()

const creating = ref(false)
const persistProductRequest = ref<PersistProductRequest>({
  productName: '',
  price: 0.0,
  description: '',
  stock: 0.0,
  categories: PRODUCT_CATEGORY.MEDICAMENTOS
})

const PRODUCT_CATEGORY_KEYS = computed(() =>
  Object.keys(PRODUCT_CATEGORY).filter((v) => isNaN(Number(v)))
)

onMounted(() => {
  if (props.productId) {
    httpClient
      .get(`products/${props.productId}`, {
        headers: {
          Authorization: authStore.token
        }
      })
      .then((response: AxiosResponse<Produto>) => {
        if (response.status == 200) {
          const produto = response.data
          persistProductRequest.value.productName = produto.name
          persistProductRequest.value.price = produto.price
          persistProductRequest.value.description = produto.description
          persistProductRequest.value.stock = produto.stock
          persistProductRequest.value.categories = produto.categories
        } else {
          toastStore.showMessage(
            'danger',
            'Erro!',
            'Não foi obter o produto selecionado. Redirecionando para tela de produtos.'
          )
          router.push({ name: 'products' })
        }
      })
      .catch((error: any) => {
        console.log(error)

        toastStore.showMessage(
          'danger',
          'Erro!',
          'Não foi obter o produto selecionado. Redirecionando para tela de produtos.'
        )
        router.push({ name: 'products' })
      })
  }
})

function saveProduct() {
  creating.value = true
  const onSuccess = (response: AxiosResponse<PersistProductResponse>) => {
    creating.value = false
    if (response.status == 200) {
      router.push({ name: 'products' })
      toastStore.showMessage('success', 'Sucesso!', 'O produto foi salvo na base de dados.')
    } else {
      toastStore.showMessage('danger', 'Erro!', 'Não foi possível salvar o produto.')
    }
  }

  const onError = (error: any) => {
    creating.value = false
    console.log(error)

    toastStore.showMessage('danger', 'Erro!', 'Não foi possível salvar o produto.')
  }

  if (props.productId) {
    httpClient
      .put(`products/${props.productId}`, persistProductRequest.value, {
        headers: {
          Authorization: authStore.token
        }
      })
      .then(onSuccess)
      .catch(onError)
  } else {
    httpClient
      .post('products', persistProductRequest.value, {
        headers: {
          Authorization: authStore.token
        }
      })
      .then(onSuccess)
      .catch(onError)
  }
}
</script>

<template>
  <MainLayoutComponentVue>
    <div class="container-xl">
      <div class="register">
        <div class="y-5">
          <div class="row">
            <h1 class="fw-light">Cadastro de produto</h1>
          </div>
        </div>
        <div>
          <div class="bg-light p-5 rounded">
            <div class="form-fields">
              <div class="form-group">
                <label>Nome</label>
                <input
                  class="form-control"
                  placeholder="Nome do produto"
                  v-model="persistProductRequest.productName"
                />
              </div>
              <div class="form-group">
                <label>Preço</label>
                <div class="input-group mb-3">
                  <input
                    type="number"
                    class="form-control"
                    placeholder="Preço do produto"
                    step="0.01"
                    v-model="persistProductRequest.price"
                  />
                  <span class="input-group-text">R$</span>
                </div>
              </div>
              <div class="form-group">
                <label>Descrição</label>
                <textarea
                  class="form-control"
                  placeholder="Descrição do produto"
                  v-model="persistProductRequest.description"
                ></textarea>
              </div>
              <div class="form-group">
                <label>Quantidade</label>
                <input
                  type="number"
                  class="form-control"
                  placeholder="Quantidade do produto em estoque"
                  v-model="persistProductRequest.stock"
                />
              </div>
              <div class="form-group">
                <label>Categoria</label>
                <select class="form-control" v-model="persistProductRequest.categories">
                  <option v-for="productCategory in PRODUCT_CATEGORY_KEYS" :value="productCategory">
                    {{ PRODUCT_CATEGORY_LABEL.get(Number(productCategory)) }}
                  </option>
                </select>
              </div>
            </div>
            <div class="form-actions">
              <button class="btn btn-primary" v-on:click="saveProduct">
                Criar produto
                <font-awesome-icon v-if="creating" :icon="['fas', 'fa-spinner']" :spin="true" />
              </button>
              <a class="btn btn-outline-primary" href="/products">Voltar</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </MainLayoutComponentVue>
</template>

<style>
.form-actions {
  margin-top: 1rem;
}

.form-actions button {
  margin-right: 1rem;
}
</style>
