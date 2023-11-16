import type { USER_ROLE } from '@/enums/user_role'

type CreateUserRequest = {
  username: string
  email: string
  role: USER_ROLE
  password: string
  phoneNumber: string
}

type CreateUserResponse = {
  id: string
  username: string
  email: string
  role: USER_ROLE
}

type LoginRequest = {
  username: string
  password: string
}

type LoginResponse = {
  message: string
  token: string
}

export type { CreateUserRequest, CreateUserResponse, LoginRequest, LoginResponse }
