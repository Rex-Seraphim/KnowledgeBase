import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// import store from './store' // 如果你使用了 Vuex

const app = createApp(App)

// 注册路由
app.use(router)
app.use(ElementPlus)
// app.use(store) // 可选：状态管理


// 全局设置 axios 请求拦截器
import axios from 'axios'

axios.defaults.baseURL = 'http://localhost:8080'

// 请求拦截器：自动带上 Token
axios.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = token
  }
  return config
})



// 响应拦截器
axios.interceptors.response.use(response => {
  if (response.data.code !== 200) {
    alert(response.data.message)
    return Promise.reject(response.data.message)
  }
  return response
}, error => {
  alert('网络异常，请重试')
  return Promise.reject(error)
})




app.mount('#app')
