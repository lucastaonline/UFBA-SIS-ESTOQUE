import type { TRANSACTION_TYPE_DESCRIPTION } from '@/enums/transaction_type_description'
import type { TRANSACTION_TYPE_DIRECTION } from '@/enums/transaction_type_direction'

export type TransactionType = {
  id: string
  direction: TRANSACTION_TYPE_DIRECTION
  description: TRANSACTION_TYPE_DESCRIPTION
}
