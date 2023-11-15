import type { PRODUCT_CATEGORY } from '@/enums/categoria_produto'

export type Produto = {
  id: string
  name: string
  stock: number
  price: number
  description: string
  categories: PRODUCT_CATEGORY
}
