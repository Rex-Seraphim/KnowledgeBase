<template>
  <div class="admin-container">
    <!-- 侧边栏 -->
    <div class="sidebar">
      <h3 class="sidebar-title">管理员面板</h3>
      <ul class="nav-menu">
        <li
          v-for="item in navItems"
          :key="item.key"
          :class="['nav-item', { active: currentTab === item.key }]"
          @click="currentTab = item.key"
          class="fade-hover"
        >
          <span class="icon">{{ item.icon }}</span>
          <span class="label">{{ item.label }}</span>
        </li>
      </ul>
    </div>

    <!-- 主体内容 -->
    <div class="main-content">
      <transition name="fade" mode="out-in">
        <component :is="currentComponent" />
      </transition>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import UserManagement from '@/components/UserManagement.vue'
import KBManagement from '@/components/KBManagement.vue'
import ArticleManagement from '@/components/ArticleManagement.vue'

const currentTab = ref('user') // 默认选中用户管理

const navItems = [
  { key: 'user', label: '用户管理', icon: '👤' },
  { key: 'kb', label: '知识库管理', icon: '📚' },
  { key: 'article', label: '文章管理', icon: '📝' }
]

const currentComponent = computed(() => {
  switch (currentTab.value) {
    case 'user': return UserManagement
    case 'kb': return KBManagement
    case 'article': return ArticleManagement
    default: return UserManagement
  }
})
</script>



<style scoped>
.admin-container {
  display: flex;
  height: 100vh;
  background: linear-gradient(to right, #f8f9fa, #e9ecef);
  font-family: 'Segoe UI', sans-serif;
}

/* 侧边栏样式 */
.sidebar {
  width: 240px;
  background-color: #ffffff;
  padding: 20px;
  box-shadow: 2px 0 6px rgba(0, 0, 0, 0.1);
  border-right: 1px solid #ddd;
}

.sidebar-title {
  margin-bottom: 20px;
  font-size: 1.2rem;
  color: #333;
  text-align: center;
}

.nav-menu {
  list-style: none;
  padding: 0;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: 12px 15px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #555;
}

.nav-item:hover,
.nav-item.active {
  background-color: #e8f4ff;
  color: #007bff;
  font-weight: bold;
}

.nav-item .icon {
  margin-right: 10px;
  font-size: 1.2em;
  transition: transform 0.3s ease;
}

.nav-item:hover .icon {
  transform: scale(1.2);
}

/* 主内容区域 */
.main-content {
  flex: 1;
  padding: 30px;
  overflow-y: auto;
  background-color: #fff;
  box-shadow: -2px 0 6px rgba(0, 0, 0, 0.05);
}

/* 切换动效 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>

<!-- 可选：添加 hover 动画类 -->
<style>
.fade-hover {
  transition: all 0.2s ease;
}
.fade-hover:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>
