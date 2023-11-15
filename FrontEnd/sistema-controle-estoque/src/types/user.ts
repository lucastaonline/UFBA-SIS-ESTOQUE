import type { USER_ROLE } from '@/enums/user_role'
import type { GrantedAuthority } from './granted_authority'

export type User = {
  id: string
  username: string
  password: string
  email: string
  phoneNumber: string
  role: USER_ROLE
  enabled: boolean
  accountNonLocked: boolean
  authorities: GrantedAuthority
  credentialsNonExpired: boolean
  accountNonExpired: boolean
}
