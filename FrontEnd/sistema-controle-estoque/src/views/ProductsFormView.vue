<script setup lang="ts">
import MainLayoutComponentVue from '@/components/MainLayoutComponent.vue'
import { PRODUCT_CATEGORY, PRODUCT_CATEGORY_LABEL } from '@/enums/product_category'
import httpClient from '@/services/http-client'
import { useAuthStore } from '@/stores/auth'
import { useToastStore } from '@/stores/toast'
import type { Product } from '@/types/product'
import type {
  PersistProductRequest,
  PersistProductResponse
} from '@/types/requisition_models/products.js'
import type { AxiosResponse } from 'axios'
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps(['productId'])

const authStore = useAuthStore()
const toastStore = useToastStore()
const router = useRouter()

const creating = ref(false)
const persistProductRequest = ref<PersistProductRequest>({
  name: '',
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
      .then((response: AxiosResponse<Product>) => {
        if (response.status == 200) {
          const product = response.data
          persistProductRequest.value.name = product.name
          persistProductRequest.value.price = product.price
          persistProductRequest.value.description = product.description
          persistProductRequest.value.stock = product.stock
          persistProductRequest.value.categories =
            PRODUCT_CATEGORY[product.categories.toString() as keyof typeof PRODUCT_CATEGORY]
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
        if (error.response?.status == 401) {
          router.push({ name: 'login', query: { sessionTimeout: 'true' } })
        } else {
          toastStore.showMessage(
            'danger',
            'Erro!',
            'Não foi obter o produto selecionado. Redirecionando para tela de produtos.'
          )
          router.push({ name: 'products' })
        }
      })
  }
})

function saveProduct() {
  creating.value = true

  if (
    persistProductRequest.value.name == null ||
    persistProductRequest.value.name == '' ||
    persistProductRequest.value.price == null ||
    persistProductRequest.value.description == null ||
    persistProductRequest.value.description == '' ||
    persistProductRequest.value.stock == null
  ) {
    creating.value = false
    toastStore.showMessage(
      'danger',
      'Erro!',
      'Você deve preencher todos os campos para fazer login.'
    )

    return
  }

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

    if (error.response?.status == 401)
      router.push({ name: 'login', query: { sessionTimeout: 'true' } })
    else toastStore.showMessage('danger', 'Erro!', 'Não foi possível salvar o produto.')
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

function verificarEnter($event: KeyboardEvent) {
  if ($event.key == 'Enter') saveProduct()
}
</script>

<template>
  <MainLayoutComponentVue>
    <div class="container-xl" v-on:keypress="verificarEnter">
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
                  v-model="persistProductRequest.name"
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
                  <option
                    v-for="productCategory in PRODUCT_CATEGORY_KEYS"
                    :value="PRODUCT_CATEGORY[productCategory as keyof typeof PRODUCT_CATEGORY]"
                  >
                    {{
                      PRODUCT_CATEGORY_LABEL.get(
                        PRODUCT_CATEGORY[productCategory as keyof typeof PRODUCT_CATEGORY]
                      )
                    }}
                  </option>
                </select>
              </div>
            </div>
            <div class="form-actions">
              <button class="btn btn-primary" v-on:click="saveProduct">
                Salvar produto
                <font-awesome-icon v-if="creating" :icon="['fas', 'fa-spinner']" :spin="true" />
              </button>
              <RouterLink to="/products" class="btn btn-outline-primary"> Voltar </RouterLink>
            </div>
          </div>
        </div>
      </div>
    </div>
  </MainLayoutComponentVue>
</template>

<style scoped>
.form-actions {
  margin-top: 1rem;
}

.form-actions button {
  margin-right: 1rem;
}
</style>
