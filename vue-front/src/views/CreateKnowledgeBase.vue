<template>
  <div class="create-kb-container">
    <div class="card">
      <h2>新建知识库</h2>
      <form @submit.prevent="submitKb">
        <div class="form-group">
          <label for="kbName">知识库名称</label>
          <input id="kbName" v-model="kbName" type="text" placeholder="请输入知识库名称" required />
        </div>
        <button type="submit" class="submit-btn">提交</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const kbName = ref('')

async function submitKb() {
  try {
    const user = JSON.parse(localStorage.getItem('user'))
    await axios.post('/api/kb', {
      kbName: kbName.value,
      creatorId: user.userId,
      createdAt: new Date()
    })
    alert("知识库创建成功")
    router.push('/')
  } catch (error) {
    alert("创建失败：" + error.response?.data?.message || '未知错误')
  }
}
</script>

<style scoped>
.create-kb-container {
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
  max-width: 500px;
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

input[type="text"] {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 16px;
  transition: border-color 0.3s ease;
}

input:focus {
  border-color: #3498db;
  outline: none;
}

.submit-btn {
  width: 100%;
  padding: 12px;
  background-color: #3498db;
  color: white;
  border: none;
  font-size: 16px;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-btn:hover {
  background-color: #2980b9;
}
</style>
