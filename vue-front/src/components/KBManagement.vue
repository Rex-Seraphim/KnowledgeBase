<!-- src/components/KBManagement.vue -->
<template>
  <div class="kb-management">
    <h3>知识库管理</h3>

    <div class="add-kb">
      <input v-model="newKbName" placeholder="输入知识库名称" />
      <button @click="addKB">新建知识库</button>
    </div>

    <table class="kb-table">
      <thead>
      <tr>
        <th>ID</th>
        <th>名称</th>
        <th>创建者</th>
        <th>创建时间</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="kb in kbs" :key="kb.kbId">
        <td>{{ kb.kbId }}</td>
        <td><input v-model="kb.kbName" /></td>
        <td>{{ kb.creatorName || '-' }}</td>
        <td>{{ kb.createdAt }}</td>
        <td>
          <button @click="updateKB(kb)">更新</button>
          <button @click="deleteKB(kb.kbId)" style="margin-left: 5px;">删除</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted } from 'vue'

const kbs = ref([])
const newKbName = ref('')

onMounted(async () => {
  const res = await axios.get('/api/kb/list')
  kbs.value = res.data.data
})

async function addKB() {
  if (!newKbName.value) return alert("请输入知识库名称")
  const res = await axios.post('/api/kb', { kbName: newKbName.value })
  kbs.value.push(res.data.data)
  newKbName.value = ''
}

async function updateKB(kb) {
  try {
    await axios.put(`/api/kb/${kb.kbId}`, { kbName: kb.kbName })
    alert("知识库名称已更新")
  } catch (error) {
    alert("更新失败：" + error.response?.data?.message || '未知错误')
  }
}

async function deleteKB(kbId) {
  if (!confirm("确定要删除该知识库吗？")) return
  try {
    await axios.delete(`/api/kb/${kbId}`)
    kbs.value = kbs.value.filter(kb => kb.kbId !== kbId)
    alert("知识库已删除")
  } catch (error) {
    alert("删除失败：" + error.response?.data?.message || '未知错误')
  }
}
</script>

<style scoped>
.add-kb {
  margin-bottom: 15px;
}
.add-kb input {
  padding: 8px;
  width: 200px;
}
.kb-table {
  width: 100%;
  border-collapse: collapse;
}
.kb-table th, .kb-table td {
  border: 1px solid #ccc;
  padding: 8px;
}
</style>
