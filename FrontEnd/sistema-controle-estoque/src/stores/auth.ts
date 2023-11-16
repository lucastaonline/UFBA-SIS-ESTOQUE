import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { AxiosResponse } from 'axios'
import httpClient from '@/services/http-client'
import { faL } from '@fortawesome/free-solid-svg-icons'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token'))
  const user = ref(JSON.parse(localStorage.getItem('user')!))

  function setToken(tokenValue: string) {
    localStorage.setItem('token', tokenValue)
    token.value = tokenValue
  }

  function setUser(userValue: any) {
    localStorage.setItem('user', JSON.stringify(userValue))
    user.value = userValue
  }

  function userIsAdmin() {
    return isAuthenticated() && user.value.decodedToken.role == 'ADMIN'
  }

  async function checkToken() {
    try {
      let resposta = await httpClient.get(`auth/validate-token`, {
        headers: {
          Authorization: token.value
        }
      })

      return resposta.status == 200
    } catch (error: any) {
      console.log(error)

      return false
    }
  }

  function isAuthenticated() {
    return token.value && user.value
  }

  function clear() {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    token.value = null
    user.value = null
  }

  return {
    token,
    setToken,
    setUser,
    checkToken,
    isAuthenticated,
    userIsAdmin,
    clear
  }
})
