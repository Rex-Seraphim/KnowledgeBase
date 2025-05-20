<template>
  <div class="auth-container">
    <h2>注册</h2>
    <form @submit.prevent="register" class="auth-form">
      <input v-model="username" placeholder="用户名" required />
      <input v-model="password" type="password" placeholder="密码" required />
      <input v-model="email" type="email" placeholder="邮箱" required />

      <button type="submit">注册</button>

      <p class="switch-link">
        已有账号？
        <router-link to="/login">去登录</router-link>
      </p>
    </form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      username: '',
      password: '',
      email: ''
    }
  },
  methods: {
    async register() {
      try {
        const res = await axios.post('/api/auth/register', {
          username: this.username,
          password: this.password,
          email: this.email
        })
        alert('注册成功，请登录')
        this.$router.push('/login')
      } catch (error) {
        alert(error.response?.data?.message || '注册失败')
      }
    }
  }
}
</script>

<style scoped>
.auth-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 30px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}

.auth-form input {
  display: block;
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.auth-form button {
  width: 100%;
  padding: 10px;
  margin-top: 15px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.switch-link {
  margin-top: 15px;
  font-size: 14px;
}
</style>
