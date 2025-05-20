<!-- src/components/ArticleManagement.vue -->
<template>
  <div class="article-management">
    <h3>文章管理（仅查看最新版本）</h3>

    <table class="article-table">
      <thead>
      <tr>
        <th>ID</th>
        <th>标题</th>
        <th>章节</th>
        <th>作者</th>
        <th>浏览数</th>
        <th>点赞数</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="a in articles" :key="a.articleId">
        <td>{{ a.articleId }}</td>
        <td>{{ a.title }}</td>
        <td>{{ a.chapter }}</td>
        <td>{{ a.authorId }}</td>
        <td>{{ a.viewCount }}</td>
        <td>{{ a.likeCount }}</td>
        <td>
          <router-link :to="`/article/edit/${a.articleId}`">编辑</router-link>
          <a href="#" @click.prevent="showHistory(a.articleNum)">查看历史</a>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- 历史版本弹窗 -->
    <div v-if="showVersionModal" class="modal">
      <div class="modal-content">
        <span @click="closeVersionModal">&times;</span>
        <h4>历史版本</h4>
        <ul>
          <li v-for="v in versionList" :key="v.version">
            版本 {{ v.version }} - 创建于 {{ v.createdAt }}
            <button @click="setCurrentVersion(v.articleId)">设为展示版本</button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted } from 'vue'

const articles = ref([])
const showVersionModal = ref(false)
const versionList = ref([])
const selectedArticleNum = ref(null)

onMounted(async () => {
  const res = await axios.get('/api/article/current')
  articles.value = res.data.data
})

async function showHistory(articleNum) {
  selectedArticleNum.value = articleNum
  const res = await axios.get(`/api/article/${articleNum}/versions`)
  versionList.value = res.data.data
  showVersionModal.value = true
}

function closeVersionModal() {
  showVersionModal.value = false
}

async function setCurrentVersion(articleId) {
  try {
    await axios.put(`/api/article/${selectedArticleNum.value}/set-current/${articleId}`)
    closeVersionModal()
    alert("已设置为当前版本")
  } catch (err) {
    alert("设置失败：" + err.response?.data?.message || '未知错误')
  }
}
</script>

<style scoped>
.modal {
  position: fixed;
  top: 20%;
  left: 30%;
  background: white;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0,0,0,0.2);
}
.modal-content {
  max-width: 400px;
}
.modal span {
  float: right;
  cursor: pointer;
  font-size: 20px;
}
</style>






