<script setup lang="ts">
import MainLayoutComponentVue from '@/components/MainLayoutComponent.vue'
import type { Transaction } from '@/types/transaction'
import { onMounted, ref } from 'vue'
import httpClient from '@/services/http-client'
import { useAuthStore } from '@/stores/auth'
import type { AxiosError, AxiosResponse } from 'axios'
import { useToastStore } from '@/stores/toast'
import { useRouter } from 'vue-router'
import {
  TRANSACTION_TYPE_DESCRIPTION_LABEL,
  TRANSACTION_TYPE_DESCRIPTION
} from '@/enums/transaction_type_description'
import {
  TRANSACTION_TYPE_DIRECTION_LABEL,
  TRANSACTION_TYPE_DIRECTION
} from '@/enums/transaction_type_direction'
import { Modal } from 'bootstrap'
import type { ProductOrder } from '@/types/product_order'

const transactions = ref<Transaction[]>([])
const authStore = useAuthStore()
const toastStore = useToastStore()
const router = useRouter()

const modal = ref<Modal>()
onMounted(() => {
  getTransactions()
  modal.value = new Modal(document.getElementById('productsModal')!)
})

function getTransactions() {
  httpClient
    .get('transactions', {
      headers: {
        Authorization: authStore.token
      }
    })
    .then((response: AxiosResponse<Transaction[]>) => {
      if (response.status == 200) {
        transactions.value = response.data
      } else {
        toastStore.showMessage(
          'danger',
          'Erro!',
          'Não foi possível retornar as movimentações de estoque.'
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
          'Não foi possível retornar as movimentações de estoque.'
        )
    })
}

const productOrdersModal = ref<ProductOrder[]>([])

function openProductsModal(products: ProductOrder[]) {
  productOrdersModal.value = products
  modal.value?.show()
}

function closeProductsModal() {
  productOrdersModal.value = []
  modal.value?.hide()
}
</script>

<template>
  <MainLayoutComponentVue>
    <div class="container-lg">
      <div class="y-5">
        <div class="row">
          <h1 class="fw-light">
            Movimentação de estoque
            <a class="btn btn-primary" href="/transactions-form"
              ><font-awesome-icon :icon="['fas', 'fa-plus']"
            /></a>
          </h1>
        </div>
      </div>
      <table class="table">
        <thead>
          <tr>
            <th>Tipo</th>
            <th>Sentido</th>
            <th>Produtos</th>
            <th>Preço total</th>
            <th>Data de criação</th>
            <th>Data de modificação</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="transaction in transactions">
            <td>
              {{
                TRANSACTION_TYPE_DESCRIPTION_LABEL.get(
                  Number(TRANSACTION_TYPE_DESCRIPTION[transaction.transactionType.description])
                )
              }}
            </td>
            <td>
              {{
                TRANSACTION_TYPE_DIRECTION_LABEL.get(
                  Number(TRANSACTION_TYPE_DIRECTION[transaction.transactionType.direction])
                )
              }}
            </td>
            <td>
              <button
                class="btn btn-primary"
                v-on:click="() => openProductsModal(transaction.productOrders)"
              >
                <font-awesome-icon :icon="['fas', 'fa-eye']" />
              </button>
            </td>
            <td>{{ transaction.value.toFixed(2) }} R$</td>
            <td>{{ transaction.createdAt }}</td>
            <td>{{ transaction.updateAt }}</td>
            <td>
              <a class="btn btn-primary" :href="`/transactions-form/${transaction.id}`"
                ><font-awesome-icon :icon="['fas', 'fa-edit']"
              /></a>
            </td>
          </tr>
        </tbody>
      </table>
      <div id="productsModal" class="modal" tabindex="-1">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Produtos</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <table class="table">
                <thead>
                  <tr>
                    <th>Produto</th>
                    <th>Preço do produto no momento da transação</th>
                    <th>Quantidade</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="productOrder in productOrdersModal">
                    <td>
                      {{ productOrder.product.name }}
                    </td>
                    <td>{{ productOrder.product.price }} R$</td>
                    <td>{{ productOrder.quantity }} unidades</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" v-on:click="closeProductsModal">
                Fechar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </MainLayoutComponentVue>
</template>

<style></style>
