<script setup lang="ts">
import MainLayoutComponentVue from '@/components/MainLayoutComponent.vue'
import type { Transaction } from '@/types/transaction'
import { onMounted, ref } from 'vue'
import httpClient from '@/services/http-client'
import { useAuthStore } from '@/stores/auth'
import type { AxiosError, AxiosResponse } from 'axios'
import { useToastStore } from '@/stores/toast'
import { useRouter } from 'vue-router'

const transactions = ref<Transaction[]>([])
const authStore = useAuthStore()
const toastStore = useToastStore()
const router = useRouter()

onMounted(() => {
  getTransactions()
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
            <td>{{ transaction.transactionType.description }}</td>
            <td>{{ transaction.transactionType.direction }}</td>
            <td>
              <button class="btn btn-primary">
                <font-awesome-icon :icon="['fas', 'fa-eye']" />
              </button>
            </td>
            <td>{{ transaction.value }} R$</td>
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
    </div>
  </MainLayoutComponentVue>
</template>

<style></style>
