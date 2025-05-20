

<template>
  <div id="app">
    <main>
      <!-- 路由组件渲染位置 -->
      <router-view />
    </main>
  </div>
</template>

<script setup>// 不需要导入任何组件，因为直接使用的是 router-view
</script>

<style scoped>#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
}
</style>
