<script setup lang="ts">
import { USER_ROLE, USER_ROLE_LABEL } from '@/enums/user_role'
import { ref, computed } from '@vue/reactivity'
import { reactive } from 'vue'
import type { CreateUserRequest, CreateUserResponse } from '@/types/requisition_models/users'
import { useToastStore } from '@/stores/toast'
import httpClient from '@/services/http-client'
import type { AxiosResponse } from 'axios'
import router from '@/router'

const USER_ROLES_KEYS = computed(() => Object.keys(USER_ROLE).filter((v) => !isNaN(Number(v))))

const createUserRequest = reactive<CreateUserRequest>({
  username: '',
  email: '',
  password: '',
  phoneNumber: '',
  role: USER_ROLE.ADMIN
})

const registering = ref(false)

function register() {
  registering.value = true
  var toastStore = useToastStore()

  if (
    createUserRequest.username == null ||
    createUserRequest.username == '' ||
    createUserRequest.email == null ||
    createUserRequest.email == '' ||
    createUserRequest.password == null ||
    createUserRequest.password == '' ||
    createUserRequest.phoneNumber == null ||
    createUserRequest.phoneNumber == ''
  ) {
    registering.value = false
    toastStore.showMessage(
      'danger',
      'Erro!',
      'Você deve preencher todos os campos para cadastrar um usuário.'
    )

    return
  }

  httpClient
    .post('auth/createUser', createUserRequest)
    .then((response: AxiosResponse<CreateUserResponse>) => {
      registering.value = false

      if (response.status == 200) {
        router.push({ name: 'login' })
        toastStore.showMessage('success', 'Sucesso!', 'O usuário foi cadastrado com sucesso.')
      } else {
        toastStore.showMessage(
          'danger',
          'Erro!',
          'O usuário não pode ser cadastrado por conta de um erro desconhecido.'
        )
      }
    })
    .catch((error) => {
      console.log(error)
      registering.value = false

      toastStore.showMessage(
        'danger',
        'Erro!',
        'O usuário não pode ser cadastrado por conta de um erro desconhecido.'
      )
    })
}
</script>

<template>
  <div class="login">
    <div class="header y-5 text-center">
      <div class="row">
        <h1 class="fw-light">Cadastro de usuário | VitaSaúde</h1>
        <p class="lead text-muted">Preencha seus dados e cadastre-se</p>
      </div>
    </div>
    <div class="login-form-container">
      <div class="bg-light p-5 rounded">
        <div class="form-fields">
          <div class="form-group">
            <label>Função</label>
            <select
              class="form-control"
              placeholder="Nome de usuário"
              v-model="createUserRequest.role"
            >
              <option v-for="userRole in USER_ROLES_KEYS" :value="userRole">
                {{ USER_ROLE_LABEL.get(Number(userRole)) }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>E-mail</label>
            <input class="form-control" placeholder="E-mail" v-model="createUserRequest.email" />
          </div>
          <div class="form-group">
            <label>Telefone</label>
            <input
              class="form-control"
              placeholder="Telefone"
              v-model="createUserRequest.phoneNumber"
            />
          </div>
          <div class="form-group">
            <label>Usuário</label>
            <input
              class="form-control"
              placeholder="Nome de usuário"
              v-model="createUserRequest.username"
            />
          </div>
          <div class="form-group">
            <label>Senha</label>
            <input
              type="password"
              class="form-control"
              placeholder="Senha"
              v-model="createUserRequest.password"
            />
          </div>
        </div>
        <div class="form-actions">
          <button class="btn btn-primary" v-on:click="register">
            Cadastrar-se
            <font-awesome-icon v-if="registering" :icon="['fas', 'fa-spinner']" :spin="true" />
          </button>
          <p>Clicou sem querer? <RouterLink to="login"> Voltar para tela de login </RouterLink></p>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.login {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  padding: 10rem;
  align-content: center;
  height: 100%;
}

.header {
  width: 100%;
}

.login-form-container {
  width: 30rem;
}

.form-fields {
  margin-bottom: 2rem;
}

.form-actions {
  text-align: center;
}
</style>
