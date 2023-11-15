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

  function setTitle(title) {
    informacoesToast.title = title
  }

  function setType(type) {
    informacoesToast.type = type
  }

  function setMessage(message) {
    informacoesToast.message = message
  }

  function setOpened(openedValue) {
    opened.value = openedValue
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
    setOpened,
    setTitle,
    setMessage,
    setType
  }
})
