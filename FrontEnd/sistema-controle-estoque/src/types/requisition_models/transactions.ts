type PersistTransactionItemRequest = {
  productId: string
  quantity: number
}

type PersistTransactionRequest = {
  transactionTypeId: string
  userId: string
  items: PersistTransactionItemRequest[]
}

type PersistTransactionResponse = {
  id: string
  message: string
  createdAt: Date
  updatedAt: Date
}

export type { PersistTransactionRequest, PersistTransactionResponse, PersistTransactionItemRequest }
