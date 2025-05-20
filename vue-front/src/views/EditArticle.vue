<template>
  <div class="edit-article-container">
    <h2>编辑文章</h2>
    <form @submit.prevent="saveNewVersion" class="edit-form">
      <label>章节：
        <input v-model="article.chapter" required />
      </label>

      <label>标题：
        <input v-model="article.title" required />
      </label>

      <label>内容：
        <mavon-editor
          v-model="article.content"
          :toolbars="markdownToolbar"
          style="height: 400px;"
          placeholder="输入 Markdown 内容..."
        />
      </label>

      <button type="submit">保存为新版本</button>
    </form>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import axios from 'axios'

const route = useRoute()
const router = useRouter()

// 文章数据模型
const article = ref({
  articleId: null,
  articleNum: null,
  version: null,
  kbId: null,
  chapter: '',
  title: '',
  content: '',
  authorId: null
})

// 配置 markdown 工具栏（可自定义）
const markdownToolbar = {
  bold: true,
  italic: true,
  header: true,
  underline: true,
  strikethrough: true,
  mark: true,
  superscript: true,
  subscript: true,
  quote: true,
  ol: true,
  ul: true,
  link: true,
  imagelink: true,
  code: true,
  table: true,
  fullscreen: true,
  readmodel: true,
  htmlcode: true,
  help: true
}

// 获取文章详情
onMounted(async () => {
  const res = await axios.get(`/api/article/${route.params.articleId}`)
  article.value = res.data.data
})

// 提交新版本
async function saveNewVersion() {
  try {
    await axios.post('/api/article/publish', {
      articleNum: article.value.articleNum,
      kbId: article.value.kbId,
      chapter: article.value.chapter,
      title: article.value.title,
      content: article.value.content,
      authorId: article.value.authorId
    })
    alert("新版本已发布")
    router.push('/admin')
  } catch (error) {
    alert("发布失败：" + error.response?.data?.message || '未知错误')
  }
}
</script>

<style scoped>
.edit-article-container {
  max-width: 1000px;
  margin: 30px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.edit-form label {
  display: block;
  margin-bottom: 15px;
}

.edit-form input {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.edit-form button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #3498db;
  color: white;
  border: none;
  cursor: pointer;
  font-size: 16px;
  border-radius: 4px;
}

.edit-form button:hover {
  background-color: #2980b9;
}
</style>
