<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <h1 class="text-h4 font-weight-bold mb-4">會員管理</h1>
      </v-col>
    </v-row>

    <v-card class="elevation-2 rounded-lg">
      <v-card-text>
        <v-data-table-server
          v-model:items-per-page="itemsPerPage"
          :headers="headers"
          :items="members"
          :items-length="totalItems"
          :loading="loading"
          item-value="id"
          @update:options="loadItems"
        >
        </v-data-table-server>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref } from "vue";
import { getMembers } from "@/api/members";

const itemsPerPage = ref(10);
const headers = [
  { title: "ID", key: "id", align: "start" },
  { title: "Email", key: "email" },
  { title: "姓名", key: "name" }, // 假設欄位有 name
  // 可以根據實際 API 回傳欄位增加
   { title: "權限", key: "role" },
];

const members = ref([]);
const totalItems = ref(0);
const loading = ref(false);

const loadItems = async ({ page, itemsPerPage }) => {
  loading.value = true;
  try {
    // API 呼叫：注意這裡假設 API 接受 (page, size) 參數
    // 且 page 從 1 開始 (通常 Spring Data JPA 是 0-based，但這裡先傳 1 讓後端處理或之後調整)
    const response = await getMembers(page, itemsPerPage);
    
    // 這裡需要根據後端實際回傳的格式進行調整
    // 假設格式為 { content: [], totalElements: 100 } (Spring一般格式)
    // 或是 { list: [], total: 100 }
    
    // 暫時做個判斷來相容常見格式
    const data = response.data;
    if (Array.isArray(data)) {
        // 如果只回傳陣列，那就不支援分頁資訊
        members.value = data;
        totalItems.value = data.length;
    } else {
        // 嘗試讀取 content 或 list
        members.value = data.content || data.list || [];
        // 嘗試讀取 totalElements 或 total
        totalItems.value = data.totalElements || data.total || 0;
    }

  } catch (error) {
    console.error("載入會員失敗:", error);
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped></style>
