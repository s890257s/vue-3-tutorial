<template>
  <div>
    <h1>會員管理</h1>

    <v-data-table :items="members"></v-data-table>
  </div>
</template>

<script setup>
import { ref } from "vue";

const members = ref([
  {
    name: "Alice",
    email: "alice@gmail.com",
    phone: "123456789",
    address: "123 Main St",
  },
  {
    name: "Alice",
    email: "alice@gmail.com",
    phone: "123456789",
    address: "123 Main St",
  },
    {
    name: "Alice",
    email: "alice@gmail.com",
    phone: "123456789",
    address: "123 Main St",
  },
]);

const feature = ref();
</script>

<style scoped></style>
