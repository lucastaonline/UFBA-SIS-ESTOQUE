import type { PRODUCT_CATEGORY } from '@/enums/categoria_produto'

type CreateProductRequest = {
  productName: string
  price: number
  description: string
  stock: number
  category: PRODUCT_CATEGORY
}

type CreateProductResponse = {
  id: string
  productName: string
  price: number
  description: string
  stock: number
  category: PRODUCT_CATEGORY
}

export type { CreateProductRequest, CreateProductResponse }
