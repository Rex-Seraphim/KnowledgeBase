<template>
  <div class="home-container">
    <!-- 导航栏 -->
    <nav class="top-nav">
      <span>欢迎回来，{{ username }}</span>
      <div class="nav-links">
        <a @click="logout">退出登录</a>
        <router-link v-if="isAdmin" to="/admin">管理员面板</router-link>
        <span v-else style="color: gray; font-size: 14px;">非管理员用户</span>
      </div>
    </nav>

    <!-- 主体内容 -->
    <div class="hero-section">
      <h1>知识库系统主页</h1>
<!--      <p>这里是知识库首页内容</p>-->
    </div>

    <!-- 搜索 + 排序 -->
    <div class="search-bar">
      <input type="text" placeholder="输入关键字搜索..." v-model="searchQuery" />
      <select v-model="sortBy">
        <option value="kbName">按名称排序</option>
        <option value="createdAt">按时间排序</option>
      </select>
    </div>

    <!-- 知识库列表 -->
    <div class="kb-gallery">
      <div v-for="kb in filteredAndSortedKbs" :key="kb.kbId" class="kb-card" @click="goToKb(kb)">
        <div class="kb-header">
          <h3>{{ kb.kbName }}</h3>
        </div>
        <div class="kb-body">
          <p>创建者：{{ kb.creatorName || '无名' }}</p>
          <p>创建时间：{{ formatTime(kb.createdAt) }}</p>
        </div>
        <div class="kb-footer">
          <button class="btn">进入知识库</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { clearStorageLogin, getStorageUser } from '@/utils/storage'

const router = useRouter()

// 用户信息
const user = getStorageUser()
const username = ref('')
const isAdmin = ref(false)

if (!user) {
  router.push('/login')
} else {
  username.value = user.username
  isAdmin.value = user.status === 2
}

// 知识库列表
const kbs = ref([])
const searchQuery = ref('')
const sortBy = ref('kbName')

onMounted(async () => {
  try {
    const res = await axios.get('/api/kb/list')
    let list = res.data.data

    // 获取创建者名字
    const creatorIds = list.map(kb => kb.creatorId)
    if (creatorIds.length > 0) {
      const usersRes = await axios.post('/api/user/names', { ids: [...new Set(creatorIds)] })

      const userData = usersRes.data.data;

      // 注意：后端返回的是一个 Map<Integer, String>，即 {1: 'test', 2: 'girl', ...}
      const nameMap = {};

      if (userData && typeof userData === 'object') {
        Object.keys(userData).forEach(userId => {
          nameMap[userId] = userData[userId] || '未知用户';
        });
      }

      kbs.value = list.map(kb => ({
        ...kb,
        creatorName: nameMap[kb.creatorId] || '无名'
      }))
    }
  } catch (error) {
    console.error("获取知识库失败", error)
    alert('获取知识库失败，请刷新重试')
  }
})

function logout() {
  clearStorageLogin()
  router.push('/login')
}

function formatTime(date) {
  if (!date) return ''
  const d = new Date(date)
  return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}`
}

function pad(n) {
  return n < 10 ? '0' + n : n
}

function goToKb(kb) {
  router.push(`/kb/${kb.kbId}`)
}

// 过滤器
const filteredAndSortedKbs = computed(() => {
  let result = kbs.value

  // 搜索过滤
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    result = result.filter(kb =>
      kb.kbName.toLowerCase().includes(query)
    )
  }

  // 排序
  result = [...result]
  if (sortBy.value === 'kbName') {
    result.sort((a, b) => a.kbName.localeCompare(b.kbName))
  } else if (sortBy.value === 'createdAt') {
    result.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
  }

  return result
})
</script>

<style scoped>
.home-container {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  padding: 20px;
  background-color: #f5f7fa;
}

.top-nav {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background-color: #3498db;
  color: white;
  padding: 15px 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  z-index: 1000;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.nav-links a {
  color: white;
  margin-left: 20px;
  text-decoration: none;
  cursor: pointer;
  font-weight: 500;
}

.hero-section {
  margin-top: 80px; /* 避开固定导航栏 */
  text-align: center;
  padding-bottom: 20px;
  border-bottom: 1px solid #ddd;
}

/* 搜索 + 排序区域 */
.search-bar {
  max-width: 800px;
  margin: 20px auto;
  display: flex;
  justify-content: space-between;
  gap: 10px;
}

.search-bar input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 14px;
}

.search-bar select {
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 14px;
}

/* 知识库列表容器 */
.kb-gallery {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
  margin-top: 20px;
  padding: 0 20px;
}

/* 单个知识库卡片 */
.kb-card {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  height: 100%;
}

/* Hover 效果 */
.kb-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

/* 卡片头部 */
.kb-header {
  padding: 15px;
  background-color: #ecf0f1;
  border-bottom: 1px solid #ddd;
}

.kb-header h3 {
  margin: 0;
  font-size: 18px;
  color: #2c3e50;
}

/* 卡片内容 */
.kb-body {
  padding: 15px;
  flex-grow: 1;
  color: #555;
}

.kb-body p {
  margin: 8px 0;
  font-size: 14px;
}

/* 卡片底部按钮区域 */
.kb-footer {
  padding: 10px 15px;
  text-align: right;
}

.btn {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 8px 12px;
  font-size: 14px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn:hover {
  background-color: #2980b9;
}
</style>
