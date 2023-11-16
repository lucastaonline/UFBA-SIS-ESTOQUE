import type { TRANSACTION_TYPE } from '@/enums/transaction_type'

export type Transaction = {
  id: string
  type: TRANSACTION_TYPE
  createdAt: Date
  updateAt: Date
}
