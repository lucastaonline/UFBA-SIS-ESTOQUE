import type { User } from '@/types/user'
import type { TransactionType } from '@/types/transaction_type'
import type { ProductOrder } from '@/types/product_order'

export type Transaction = {
  id: string
  transactionType: TransactionType
  user: User
  productOrders: ProductOrder[]
  value: number
  createdAt: Date
  updateAt: Date
}
