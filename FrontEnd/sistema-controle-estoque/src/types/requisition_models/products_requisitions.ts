import type { PRODUCT_CATEGORY } from '@/enums/product_category'

type PersistProductRequest = {
  productName: string
  price: number
  description: string
  stock: number
  categories: PRODUCT_CATEGORY
}

type PersistProductResponse = {
  id: string
  productName: string
  price: number
  description: string
  stock: number
  categories: PRODUCT_CATEGORY
}

export type { PersistProductRequest, PersistProductResponse }
