import type { PRODUCT_CATEGORY } from '@/enums/product_category'

export type Product = {
  id: string
  name: string
  stock: number
  price: number
  description: string
  categories: PRODUCT_CATEGORY
}
