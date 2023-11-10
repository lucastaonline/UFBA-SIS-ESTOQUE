<script setup lang="ts"></script>

<template>
  <div class="login">
    <div class="header y-5 text-center">
      <div class="row">
        <h1 class="fw-light">Cadastro de usuário | VitaSaúde</h1>
        <p class="lead text-muted">Preencha seus dados e cadastre-se</p>
      </div>
    </div>
    <div class="login-form-container">
      <form class="bg-light p-5 rounded">
        <div class="form-fields">
          <div class="form-group">
            <label>Função</label>
            <select class="form-control" placeholder="Nome de usuário">
              <option>Administrador</option>
              <option>Não administrador</option>
            </select>
          </div>
          <div class="form-group">
            <label>Usuário</label>
            <input class="form-control" placeholder="Nome de usuário" />
          </div>
          <div class="form-group">
            <label>Senha</label>
            <input type="password" class="form-control" placeholder="Senha" />
          </div>
        </div>
        <div class="form-actions">
          <button class="btn btn-primary">Cadastrar-se</button>
          <p>Clicou sem querer? <RouterLink to="login"> Voltar para tela de login </RouterLink></p>
        </div>
      </form>
    </div>
  </div>
</template>

<style>
.login {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  padding: 10rem;
  align-content: center;
  height: 100%;
}

.header {
  width: 100%;
}

.login-form-container {
  width: 30rem;
}

.form-fields {
  margin-bottom: 2rem;
}

.form-actions {
  text-align: center;
}
</style>
