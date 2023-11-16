<script setup lang="ts">
import MainLayoutComponentVue from '@/components/MainLayoutComponent.vue'
import httpClient from '@/services/http-client'
import { useAuthStore } from '@/stores/auth'
import { useToastStore } from '@/stores/toast'
import type { Transaction } from '@/types/transaction'
import type {
  PersistTransactionRequest,
  PersistTransactionResponse
} from '@/types/requisition_models/transactions'
import type { AxiosError, AxiosResponse } from 'axios'
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import type { Product } from '@/types/product'
import type { TransactionType } from '@/types/transaction_type'
import {
  TRANSACTION_TYPE_DESCRIPTION_LABEL,
  TRANSACTION_TYPE_DESCRIPTION
} from '@/enums/transaction_type_description'

import {
  TRANSACTION_TYPE_DIRECTION_LABEL,
  TRANSACTION_TYPE_DIRECTION
} from '@/enums/transaction_type_direction'

const authStore = useAuthStore()
const toastStore = useToastStore()
const router = useRouter()

const creating = ref(false)
const persistTransactionRequest = ref<PersistTransactionRequest>({
  transactionTypeId: '',
  items: []
})

const products = ref<Product[]>([])
const transactionTypes = ref<TransactionType[]>([])

function addItem() {
  persistTransactionRequest.value.items.push({
    productId: sortedProducts.value[0].id,
    quantity: 1
  })
}

function removeItem(index: number) {
  persistTransactionRequest.value.items.splice(index, 1)
}

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

function getTransactionTypes() {
  httpClient
    .get('transactionTypes', {
      headers: {
        Authorization: authStore.token
      }
    })
    .then((response: AxiosResponse<TransactionType[]>) => {
      if (response.status == 200) {
        transactionTypes.value = response.data
      } else {
        toastStore.showMessage(
          'danger',
          'Erro!',
          'Não foi possível retornar os tipos de movimentações de estoque.'
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
          'Não foi possível retornar os tipos de movimentações de estoque.'
        )
    })
}

onMounted(() => {
  getProducts()
  getTransactionTypes()
})

function saveTransaction() {
  creating.value = true

  httpClient
    .post('transactions', persistTransactionRequest.value, {
      headers: {
        Authorization: authStore.token
      }
    })
    .then((response: AxiosResponse<PersistTransactionResponse>) => {
      creating.value = false
      if (response.status == 200) {
        router.push({ name: 'transactions' })
        toastStore.showMessage(
          'success',
          'Sucesso!',
          'A movimentação de estoque foi salva na base de dados.'
        )
      } else {
        toastStore.showMessage(
          'danger',
          'Erro!',
          'Não foi possível salvar a movimentação de estoque.'
        )
      }
    })
    .catch((error: any) => {
      creating.value = false
      console.log(error)

      if (error.response?.status == 401)
        router.push({ name: 'login', query: { sessionTimeout: 'true' } })
      else
        toastStore.showMessage(
          'danger',
          'Erro!',
          `Não foi possível salvar a movimentação de estoque: ${error.response?.data?.message}.`
        )
    })
}

const sortedProducts = computed(() => {
  const sortFunction = (a: Product, b: Product) => {
    if (a.name > b.name) {
      return 1
    } else if (a.name < b.name) {
      return -1
    }

    return 0
  }

  return products.value.sort(sortFunction)
})
</script>

<template>
  <MainLayoutComponentVue>
    <div class="container-xl">
      <div class="register">
        <div class="y-5">
          <div class="row">
            <h1 class="fw-light">Cadastro de movimentação de estoque</h1>
          </div>
        </div>
        <div>
          <div class="bg-light p-5 rounded">
            <div class="form-fields">
              <div class="form-group">
                <label>Tipo de movimentação</label>
                <select
                  class="form-control"
                  placeholder="Tipo de movimentação"
                  v-model="persistTransactionRequest.transactionTypeId"
                >
                  <option v-for="transactionType in transactionTypes" :value="transactionType.id">
                    {{
                      TRANSACTION_TYPE_DESCRIPTION_LABEL.get(
                        Number(TRANSACTION_TYPE_DESCRIPTION[transactionType.description])
                      )
                    }}
                    -
                    {{
                      TRANSACTION_TYPE_DIRECTION_LABEL.get(
                        Number(TRANSACTION_TYPE_DIRECTION[transactionType.direction])
                      )
                    }}
                  </option>
                </select>
              </div>
              <div class="form-group">
                <label
                  >Produtos
                  <button class="btn btn-primary" v-on:click="addItem">
                    <font-awesome-icon :icon="['fas', 'fa-plus']" /></button
                ></label>
                <div>
                  <div v-for="(item, index) in persistTransactionRequest.items" class="row">
                    <div class="form-group col-5">
                      <label>Produto</label>
                      <select class="form-control" v-model="item.productId">
                        <option v-for="product in sortedProducts" :value="product.id">
                          {{ product.name }}
                        </option>
                      </select>
                    </div>
                    <div class="form-group col-5">
                      <label>Quantidade</label>
                      <input class="form-control" type="number" v-model="item.quantity" />
                    </div>
                    <div class="form-group col-2">
                      <label></label>
                      <button
                        class="btn btn-danger"
                        v-on:click="removeItem(index)"
                        style="display: block"
                      >
                        <font-awesome-icon :icon="['fas', 'fa-times']" />
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-actions">
              <button class="btn btn-primary" v-on:click="saveTransaction">
                Salvar movimentação de estoque
                <font-awesome-icon v-if="creating" :icon="['fas', 'fa-spinner']" :spin="true" />
              </button>
              <a class="btn btn-outline-primary" href="/transactions">Voltar</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </MainLayoutComponentVue>
</template>

<style>
.form-fields input,
.form-fields select {
  margin-bottom: 1rem;
}

.form-actions {
  margin-top: 1rem;
}

.form-actions button {
  margin-right: 1rem;
}
</style>
