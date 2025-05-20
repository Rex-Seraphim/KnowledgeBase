<!-- src/components/UserManagement.vue -->
<template>
  <div class="user-management">
    <h3>用户管理</h3>
    <table class="user-table">
      <thead>
      <tr>
        <th>ID</th>
        <th>用户名</th>
        <th>邮箱</th>
        <th>状态（角色）</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.userId">
        <td>{{ user.userId }}</td>
        <td><input v-model="user.username" /></td>
        <td>{{ user.email }}</td>
        <td>
          <select v-model.number="user.status">
            <option value="1">普通用户</option>
            <option value="2">管理员</option>
          </select>
        </td>
        <td>
          <button @click="updateUser(user)">更新</button>
          <button @click="deleteUser(user.userId)" style="margin-left: 5px;">删除</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted } from 'vue'

const users = ref([])

onMounted(async () => {
  const res = await axios.get('/api/user/list')
  users.value = res.data.data
})

async function updateUser(user) {
  try {
    await axios.put(`/api/user/${user.userId}`, user)
    alert("用户已更新")
  } catch (error) {
    alert("更新失败：" + error.response?.data?.message || '未知错误')
  }
}

async function deleteUser(userId) {
  if (!confirm("确定要删除该用户吗？")) return
  try {
    await axios.delete(`/api/user/${userId}`)
    users.value = users.value.filter(u => u.userId !== userId)
    alert("用户已删除")
  } catch (error) {
    alert("删除失败：" + error.response?.data?.message || '未知错误')
  }
}
</script>

<style scoped>
.user-table {
  width: 100%;
  border-collapse: collapse;
}
.user-table th, .user-table td {
  border: 1px solid #ccc;
  padding: 8px;
}
.user-table input {
  width: 100%;
  padding: 5px;
}
</style>
