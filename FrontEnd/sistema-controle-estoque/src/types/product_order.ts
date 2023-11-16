import type { Product } from '@/types/product'

export type ProductOrder = {
  id: string
  product: Product
  quantity: number
  value: number
}
