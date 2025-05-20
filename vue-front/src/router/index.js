import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import Admin from '../views/Admin.vue'
import Register from "../views/Register.vue";
import KnowledgeBaseDetail from '../views/KnowledgeBaseDetail.vue'
import EditArticle from '../views/EditArticle.vue'





const routes = [
  { path: '/login', name: 'Login', component: Login },
  { path: '/register', name: 'Register', component: Register },
  { path: '/', name: 'Home', component: Home, meta: { requiresAuth: true } },
  { path: '/admin', name: 'Admin', component: Admin, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/kb/:kbId', name: 'KnowledgeBaseDetail', component: KnowledgeBaseDetail, meta: { requiresAuth: true } },
  {
    path: '/article/edit/:articleId',
    name: 'EditArticle',
    component: EditArticle
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userStr = localStorage.getItem('user')

  let user = null
  try {
    if (userStr) {
      user = JSON.parse(userStr)
    }
  } catch (e) {
    user = null
    console.error("localStorage.user 解析失败", e)
  }



  //检测登录状态
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else if (to.meta.requiresAdmin && (!user || user.status !== 2)) {
    alert('您不是管理员，无法访问该页面')
    next('/')
  } else {
    next()
  }
})

export default router




