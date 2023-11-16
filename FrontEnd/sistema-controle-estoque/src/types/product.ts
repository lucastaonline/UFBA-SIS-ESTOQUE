import type { PRODUCT_CATEGORY } from '@/enums/product_category'

export type Produto = {
  id: string
  name: string
  stock: number
  price: number
  description: string
  categories: PRODUCT_CATEGORY
}
