import type { User } from '@/types/user'
import type { ProductOrder } from '@/types/product_order'
import type { TRANSACTION_TYPE_DESCRIPTION } from '@/enums/transaction_type_description'
import type { TRANSACTION_TYPE_DIRECTION } from '@/enums/transaction_type_direction'

export type Transaction = {
  id: string
  transactionTypeDescription: TRANSACTION_TYPE_DESCRIPTION
  transactionTypeDirection: TRANSACTION_TYPE_DIRECTION
  user: User
  productOrders: ProductOrder[]
  finalValue: number
  createdAt: Date
}
