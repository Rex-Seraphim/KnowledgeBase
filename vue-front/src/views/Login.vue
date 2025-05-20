<template>
  <div class="auth-container">
    <h2>登录</h2>
    <form @submit.prevent="login" class="auth-form">
      <input v-model="username" placeholder="用户名" required />
      <input v-model="password" type="password" placeholder="密码" required />

      <button type="submit">登录</button>

      <p class="switch-link">
        没有账号？
        <router-link to="/register">立即注册</router-link>
      </p>
    </form>
  </div>
</template>

<script>
import axios from 'axios'
import { setStorageLogin } from "@/utils/storage.js";

export default {
  data() {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    async login() {
      try {
        const res = await axios.post('/api/auth/login', {
          username: this.username,
          password: this.password
        })
        //测试data是否存在
        console.log("登录返回数据：" ,res.data)


        // 处理登录结果
        // 使用封装方法写入 token 和 user
        setStorageLogin(res.data.data)
        this.$router.push('/')
      } catch (error) {
        alert(res.data.message || '登录失败')
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
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.auth-form input {
  display: block;
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.auth-form button {
  width: 100%;
  padding: 10px;
  margin-top: 15px;
  background-color: #3498db;
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

