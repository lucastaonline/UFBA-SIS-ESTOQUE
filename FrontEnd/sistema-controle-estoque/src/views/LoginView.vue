<script setup lang="ts">
import { ref, reactive } from 'vue'
import httpClient from '@/services/http-client'
import { useAuthStore } from '@/stores/auth'
import { useToastStore } from '@/stores/toast'
import { useRouter } from 'vue-router'

const router = useRouter()

const userLogin = reactive({
  user: null,
  password: null
})

const informacoesToast = reactive({
  titulo: '',
  mensagem: ''
})

const loggingIn = ref(false)

function login() {
  loggingIn.value = true
  var toastStore = useToastStore()
  var authStore = useAuthStore()

  if (
    userLogin.user == null ||
    userLogin.user == '' ||
    userLogin.password == null ||
    userLogin.password == ''
  ) {
    loggingIn.value = false
    toastStore.setTitle('Erro!')
    toastStore.setMessage('Você deve preencher todos os campos para fazer login.')
    toastStore.setType('danger')
    toastStore.setOpened(true)

    return
  }

  httpClient
    .post('login', userLogin)
    .then((response: any) => {
      // dependo do backend
      const sucesso = true
      loggingIn.value = false

      if (sucesso) {
        authStore.setToken(response?.token)
        authStore.setUser(response?.user)
        router.push({ name: 'home' })
        return
      } else {
        toastStore.setTitle('Erro!')
        toastStore.setMessage('O login não pode ser efetuado efetuado com sucesso...')
        toastStore.setType('danger')
        toastStore.setOpened(true)
      }
    })
    .catch((error) => {
      loggingIn.value = false
      if (import.meta.env.VITE_DEBUG) {
        authStore.setToken('DEBUG-TOKEN')
        authStore.setUser({ username: 'DEBUG' })
        router.push({ name: 'home' })
        return
      }

      toastStore.setTitle('Erro!')
      toastStore.setMessage('O login não pode ser efetuado efetuado com sucesso...')
      toastStore.setType('danger')
      toastStore.setOpened(true)
    })
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
    <div class="login-form-container">
      <div class="bg-light p-5 rounded">
        <div class="form-fields">
          <div class="form-group">
            <label>Usuário</label>
            <input class="form-control" placeholder="Nome de usuário" v-model="userLogin.user" />
          </div>
          <div class="form-group">
            <label>Senha</label>
            <input
              type="password"
              class="form-control"
              placeholder="Senha"
              v-model="userLogin.password"
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
