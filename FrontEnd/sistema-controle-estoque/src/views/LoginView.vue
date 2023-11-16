<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import httpClient from '@/services/http-client'
import { useAuthStore } from '@/stores/auth'
import { useToastStore } from '@/stores/toast'
import { useRouter } from 'vue-router'
import type { LoginRequest, LoginResponse } from '@/types/requisition_models/users'
import type { AxiosResponse } from 'axios'
import VueJwtDecode from 'vue-jwt-decode'

const router = useRouter()

const props = defineProps({ sessionTimeout: Boolean })

const createUserRequest = reactive<LoginRequest>({
  username: '',
  password: ''
})

const loggingIn = ref(false)
const toastStore = useToastStore()
const authStore = useAuthStore()

if (props.sessionTimeout) {
  authStore.clear()
  toastStore.showMessage(
    'warning',
    'Aviso!',
    'Você perdeu sua sessão. Você deverá logar novamente.'
  )
}

function login() {
  loggingIn.value = true

  var authStore = useAuthStore()

  if (
    createUserRequest.username == null ||
    createUserRequest.username == '' ||
    createUserRequest.password == null ||
    createUserRequest.password == ''
  ) {
    loggingIn.value = false
    toastStore.showMessage(
      'danger',
      'Erro!',
      'Você deve preencher todos os campos para fazer login.'
    )

    return
  }

  httpClient
    .post('auth/login', createUserRequest)
    .then((response: AxiosResponse<LoginResponse>) => {
      loggingIn.value = false

      if (response.status == 200) {
        authStore.setToken(response.data.token)
        const decodedToken = VueJwtDecode.decode(response.data.token)
        authStore.setUser({ username: createUserRequest.username, decodedToken: decodedToken })

        router.push({ name: 'home' })
      } else {
        toastStore.showMessage(
          'danger',
          'Erro!',
          'O login não pode ser efetuado efetuado com sucesso por conta de um erro desconhecido.'
        )
      }
    })
    .catch((error) => {
      console.log(error)
      loggingIn.value = false
      if (import.meta.env.VITE_DEBUG === 'true') {
        authStore.setToken('DEBUG-TOKEN')
        authStore.setUser({ username: 'DEBUG' })
        router.push({ name: 'home' })
        return
      }

      toastStore.showMessage(
        'danger',
        'Erro!',
        'O login não pode ser efetuado efetuado com sucesso por conta de um erro desconhecido.'
      )
    })
}

function verificarEnter($event: KeyboardEvent) {
  if ($event.key == 'Enter') login()
}
</script>

<template>
  <div class="login">
    <div class="header y-5 text-center">
      <div class="row">
        <h1 class="fw-light">Controle de estoque | VitaSaúde</h1>
        <p class="lead text-muted">Realize o login para acessar o sistema</p>
      </div>
    </div>
    <div class="login-form-container" v-on:keypress="verificarEnter">
      <div class="bg-light p-5 rounded">
        <div class="form-fields">
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
          <button class="btn btn-primary" v-on:click="login">
            Login
            <font-awesome-icon v-if="loggingIn" :icon="['fas', 'fa-spinner']" :spin="true" />
          </button>
          <p>Não tem login? <RouterLink to="register"> Cadastre-se </RouterLink></p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
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
