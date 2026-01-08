<script setup>
import http from "@/api/http";

http.post(
  "/aaa",
  {},
  {
    baseURL: "/api",
    headers: {
      "content-type": "text/plain",
    },
  }
);
</script>

<template>
  <router-view></router-view>
</template>

<style scoped></style>
