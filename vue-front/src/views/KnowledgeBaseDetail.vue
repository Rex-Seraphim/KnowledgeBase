<template>
  <div class="kb-detail">
    <!-- 左侧目录 -->
    <div class="sidebar left">
      <ul>
        <li v-for="article in articles" :key="article.articleId"
            @click="selectArticle(article)"
            :class="{ active: selected?.articleId === article.articleId }">
          {{ article.chapter }}
        </li>
      </ul>
    </div>

    <!-- 中间内容 -->
    <div class="content" ref="contentRef" v-html="selectedContent"></div>

    <!-- 右侧边栏 -->
    <div class="sidebar right" ref="tocRef">
      <h4>目录</h4>
      <ul>
        <li v-for="header in headers" :key="header.id"
            :class="{ active: header.id === currentHeader }"
            @click="scrollTo(header.id)">
          {{ header.text }}
        </li>
      </ul>
      <div class="stats">
        <p><strong>浏览：</strong>{{ viewCount }}</p>
        <button class="like-btn" @click="like">❤️ 赞 {{ likeCount }}</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { marked } from 'marked'
import hljs from 'highlight.js'
import axios from 'axios'
import { useRoute } from 'vue-router'

const route = useRoute()
const kbId = route.params.kbId

const articles = ref([])
const selected = ref(null)
const selectedContent = ref('')
const headers = ref([])
const currentHeader = ref(null)

const viewCount = ref(0)
const likeCount = ref(0)

onMounted(async () => {
  const res = await axios.get(`/api/kb/${kbId}/articles`)
  articles.value = res.data.data
  if (articles.value.length > 0) {
    selectArticle(articles.value[0])
  }
})

function selectArticle(article) {
  selected.value = article
  parseContent(article.content)
  updateViewCount()
}

function parseContent(content) {
  marked.setOptions({
    highlight: code => hljs.highlight(code, { language: 'markdown' }).value,
    gfm: true
  })
  const html = marked.parse(content)
  selectedContent.value = html
  extractHeaders(html)
}

function extractHeaders(html) {
  const parser = new DOMParser()
  const doc = parser.parseFromString(html, 'text/html')
  const hTags = ['H1', 'H2', 'H3']
  const headings = []
  for (let el of Array.from(doc.body.children)) {
    if (hTags.includes(el.tagName)) {
      const id = el.textContent.toLowerCase().replace(/\s+/g, '-')
      el.setAttribute('id', id)
      headings.push({ id, text: el.textContent, level: el.tagName })
    }
  }
  headers.value = headings
}

watch(() => selected.value, () => {
  if (selected.value) {
    parseContent(selected.value.content)
  }
})

function scrollTo(id) {
  const element = document.getElementById(id)
  if (element) {
    element.scrollIntoView({ behavior: 'smooth' })
  }
}

function handleScroll() {
  let topId = null
  headers.value.forEach(h => {
    const el = document.getElementById(h.id)
    if (el && el.getBoundingClientRect().top <= 100) {
      topId = h.id
    }
  })
  currentHeader.value = topId
}

window.addEventListener('scroll', handleScroll)

// 更新浏览数
async function updateViewCount() {
  if (!selected.value) return
  try {
    const res = await axios.put(`/api/article/${selected.value.articleId}/view`);

    // 只更新 viewCount 字段，不需要重新加载整个文章内容
    selected.value.viewCount += 1;
    viewCount.value = selected.value.viewCount;

  } catch (error) {
    console.error("更新浏览数失败", error);
  }
}

async function like() {
  if (!selected.value) return
  try {
    const res = await axios.put(`/api/article/${selected.value.articleId}/like`);

    // 只更新 likeCount 字段
    selected.value.likeCount += 1;
    likeCount.value = selected.value.likeCount;

  } catch (error) {
    alert("点赞失败")
    console.error("点赞失败", error)
  }
}
</script>

<style scoped>
.kb-detail {
  display: flex;
  height: 100vh;
  padding: 20px;
  background-color: #f5f7fa;
  font-family: 'Segoe UI', sans-serif;
  transition: background-color 0.3s ease;
}

.sidebar {
  width: 280px;
  padding: 15px;
  background: #ffffff;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  overflow-y: auto;
  position: sticky;
  top: 20px;
}

.left ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.left li {
  padding: 12px;
  cursor: pointer;
  border-radius: 6px;
  margin-bottom: 6px;
  transition: all 0.2s ease;
}

.left li:hover,
.left li.active {
  background: #e8f4ff;
  font-weight: bold;
}

.content {
  flex: 1;
  padding: 30px;
  background: #fff;
  margin: 0 20px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  overflow-y: auto;
  scroll-behavior: smooth;
}

.content h1, .content h2, .content h3 {
  color: #2c3e50;
  margin-top: 1em;
  border-bottom: 1px solid #ddd;
  padding-bottom: 5px;
}

.content p {
  line-height: 1.6;
  color: #333;
}

.content pre {
  background: #f4f4f4;
  padding: 15px;
  border-radius: 6px;
  overflow-x: auto;
}

.right {
  width: 280px;
  padding: 15px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 20px;
}

.right h4 {
  margin-top: 0;
  font-size: 16px;
  color: #333;
}

.right ul {
  list-style: none;
  padding-left: 0;
  margin-top: 10px;
}

.right li {
  padding: 8px 12px;
  cursor: pointer;
  border-radius: 6px;
  transition: background 0.2s;
}

.right li:hover,
.right li.active {
  background: #ecf0f1;
  color: #3498db;
}

.stats {
  margin-top: 20px;
  padding-top: 15px;
  border-top: 1px solid #eee;
  text-align: center;
}

.like-btn {
  background: transparent;
  color: red;
  border: none;
  font-size: 16px;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.like-btn:hover {
  transform: scale(1.1);
}
</style>

