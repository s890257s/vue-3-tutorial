<script setup>
// App.vue 是 Vue 專案的根組件
// 所有頁面都會渲染在 <router-view> 裡面
</script>

<template>
  <!-- router-view 是 Vue Router 提供的組件 -->
  <!-- 它會根據目前的網址，動態顯示對應的路由組件 (例如 HomeView.vue 或 MemberManager.vue) -->
  <router-view></router-view>
</template>

<style scoped></style>
