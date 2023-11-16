type PersistTransactionItemRequest = {
  productId: string
  quantity: number
}

type PersistTransactionRequest = {
  transactionTypeId: string
  items: PersistTransactionItemRequest[]
}

type PersistTransactionResponse = {
  id: string
  message: string
}

export type { PersistTransactionRequest, PersistTransactionResponse, PersistTransactionItemRequest }
