<template>
  <div class="user-management">
    <el-card class="search-card">
      <h3>用户管理</h3>
      <el-form :inline="true" @submit.prevent="onSubmit">
        <el-form-item label="用户名">
          <el-input v-model="searchForm.username" placeholder="输入用户名搜索" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadUsers">搜索</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-table :data="users" border style="width: 100%" :row-style="{ height: '60px' }">
      <el-table-column prop="userId" label="ID" width="80"></el-table-column>
      <el-table-column label="用户名">
        <template #default="{ row }">
          <el-input v-model="row.username" />
        </template>
      </el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column label="状态（角色）" width="150">
        <template #default="{ row }">
          <el-select v-model.number="row.status" placeholder="选择状态">
            <el-option label="普通用户" :value="1" />
            <el-option label="管理员" :value="2" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button size="small" @click="updateUser(row)">更新</el-button>
          <el-button size="small" type="danger" @click="deleteUser(row.userId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      layout="prev, pager, next"
      :total="total"
      :page-size="pageSize"
      @current-change="handlePageChange"
      style="margin-top: 20px; text-align: right;"
    />
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const users = ref([])
const searchForm = ref({ username: '' })
const total = ref(0)
const pageSize = 10
const currentPage = ref(1)

async function loadUsers(page = 1) {
  try {
    const res = await axios.get('/api/user/list')
    users.value = res.data.data.slice((page - 1) * pageSize, page * pageSize)
    total.value = res.data.data.length
  } catch (error) {
    ElMessage.error('加载失败：' + error.message)
  }
}

async function updateUser(user) {
  try {
    await axios.put(`/api/user/${user.userId}`, user)
    ElMessage.success('用户已更新')
  } catch (error) {
    ElMessage.error("更新失败：" + error.response?.data?.message || '未知错误')
  }
}

async function deleteUser(userId) {
  try {
    await ElMessageBox.confirm('确定要删除该用户吗？', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    await axios.delete(`/api/user/${userId}`)
    users.value = users.value.filter(u => u.userId !== userId)
    ElMessage.success('用户已删除')
  } catch {
    ElMessage.info('已取消删除')
  }
}

function handlePageChange(page) {
  currentPage.value = page
  loadUsers(page)
}

onMounted(() => {
  loadUsers()
})
</script>

<style scoped>
.user-management {
  padding: 20px;
  background-color: #f9f9f9;
}

.search-card {
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.el-table {
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow: hidden;
}
</style>
