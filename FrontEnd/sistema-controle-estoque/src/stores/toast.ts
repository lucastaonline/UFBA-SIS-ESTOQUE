import { ref, reactive, watch } from 'vue'
import { defineStore } from 'pinia'
import { Toast } from 'bootstrap'

export const useToastStore = defineStore('toast', () => {
  const opened = ref(false)
  const informacoesToast = reactive({
    title: '',
    message: '',
    type: 'primary'
  })

  function setTitle(title: string) {
    informacoesToast.title = title
  }

  function setType(type: string) {
    informacoesToast.type = type
  }

  function setMessage(message: string) {
    informacoesToast.message = message
  }

  function setOpened(openedValue: boolean) {
    opened.value = openedValue
  }

  function showMessage(type: string, title: string, message: string) {
    setType(type)
    setTitle(title)
    setMessage(message)
    setOpened(true)
  }

  watch(opened, (opened) => {
    const toastRespostaLogin: any = document.getElementById('toastSite')

    const toast = Toast.getOrCreateInstance(toastRespostaLogin)

    if (opened) {
      toast.show()
    } else {
      toast.hide()
    }
  })

  return {
    informacoesToast,
    showMessage,
    setOpened
  }
})
