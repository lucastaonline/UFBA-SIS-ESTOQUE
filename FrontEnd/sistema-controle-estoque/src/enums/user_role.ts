export enum USER_ROLE {
  ADMIN,
  DEFAULT
}

export const USER_ROLE_LABEL = new Map<number, string>([
  [USER_ROLE.ADMIN, 'Administrador'],
  [USER_ROLE.DEFAULT, 'Usuário padrão']
])
