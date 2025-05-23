<template>
  <div class="create-article-container">
    <div class="card">
      <h2>新建文章</h2>
      <form @submit.prevent="submitArticle">
        <div class="form-group">
          <label for="kbId">知识库ID</label>
          <input id="kbId" v-model.number="kbId" type="number" placeholder="请输入知识库ID" required />
        </div>
        <div class="form-group">
          <label for="chapter">章节</label>
          <input id="chapter" v-model="chapter" type="text" placeholder="例如：第一章" required />
        </div>
        <div class="form-group">
          <label for="title">标题</label>
          <input id="title" v-model="title" type="text" placeholder="请输入文章标题" required />
        </div>
        <div class="form-group">
          <label for="content">内容</label>
          <textarea id="content" v-model="content" rows="8" placeholder="使用 Markdown 编写内容..." required></textarea>
        </div>
        <button type="submit" class="submit-btn">发布文章</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const kbId = ref(null)
const chapter = ref('')
const title = ref('')
const content = ref('')

async function submitArticle() {
  try {
    const user = JSON.parse(localStorage.getItem('user'))

    // 发布文章
    const res = await axios.post('/api/article/publish', {
      kbId: kbId.value,
      chapter: chapter.value,
      title: title.value,
      content: content.value,
      authorId: user.userId,
      createdAt: new Date()
    })

    alert("文章已发布")
    router.push('/')
  } catch (error) {
    alert("发布失败：" + error.response?.data?.message || '未知错误')
  }
}
</script>

<style scoped>
.create-article-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f7fa;
  padding: 20px;
}

.card {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  padding: 30px;
  width: 100%;
  max-width: 700px;
  margin: auto;
}

.card h2 {
  margin-top: 0;
  color: #333;
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 6px;
  color: #555;
}

input[type="text"],
input[type="number"],
textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 16px;
  resize: vertical;
  transition: border-color 0.3s ease;
}

input:focus,
textarea:focus {
  border-color: #3498db;
  outline: none;
}

.submit-btn {
  width: 100%;
  padding: 12px;
  background-color: #2ecc71;
  color: white;
  border: none;
  font-size: 16px;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-btn:hover {
  background-color: #27ae60;
}
</style>
