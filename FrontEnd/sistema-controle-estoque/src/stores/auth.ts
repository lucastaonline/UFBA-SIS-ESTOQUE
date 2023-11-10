import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token'))
  const user = ref(JSON.parse(localStorage.getItem('user')))

  function setToken(tokenValue) {
    localStorage.setItem('token', tokenValue)
    token.value = tokenValue
  }

  function setUser(userValue) {
    localStorage.setItem('user', JSON.stringify(userValue))
    user.value = userValue
  }

  function checkToken() {
    try {
      const tokenAuth = 'Bearer ' + token

      // preciso aguardar backend

      //const {data} = await http.get('url-verificar-token', {
      //    headers: {
      //        Authorization: tokenAuth
      //    }
      //})
      //return data
      return true
    } catch (error) {
      console.log(error.response.data)
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
    clear
  }
})
