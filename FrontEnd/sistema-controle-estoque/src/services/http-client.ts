import { useToastStore } from '@/stores/toast'
import axios, { AxiosError } from 'axios'
import { useRouter } from 'vue-router'

console.log(import.meta.env.VITE_API_URL)

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    'Content-type': 'application/json'
  }
})

export default axiosInstance
