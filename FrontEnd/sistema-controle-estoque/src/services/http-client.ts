import axios from 'axios'

console.log(import.meta.env.VITE_API_URL)

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    'Content-type': 'application/json'
  }
})

export default axiosInstance
