<script setup>
// 引入 Vue 核心功能
import { ref, computed, nextTick } from "vue";
// 引入 Pinia Store
import { useMemberStore } from "@/stores/memberStore";
import { storeToRefs } from "pinia";
// 引入工具函式
import { getPhoto } from "@/utils/commonUtil";
// 引入組件
import MemberEditDialog from "@/components/MemberEditDialog.vue";
import ConfirmDialog from "@/components/ConfirmDialog.vue";

// 初始化 Store
const memberStore = useMemberStore();
// 將 Store 中的 state 解構為 ref，以保持響應性
const { members, totalItems } = storeToRefs(memberStore);

// --- 表格設定 ---
const itemsPerPage = ref(10);
// 定義表格欄位
const headers = [
  { title: "ID", key: "memberId", align: "start" },
  { title: "Email", key: "email" },
  { title: "姓名", key: "memberName" },
  { title: "管理員", key: "isAdmin" },
  { title: "大頭貼", key: "memberPhoto" },
  { title: "操作", key: "actions", sortable: false }, // 操作欄位通常不排序
];
// 記錄最後一次的查詢條件，用於新增/修改/刪除後的重新載入
let lastOptions = { page: 1, itemsPerPage: 10 };

// --- 對話框與表單狀態 ---
const dialog = ref(false);       // 控制編輯/新增對話框顯示
const dialogDelete = ref(false); // 控制刪除確認對話框顯示
const editedIndex = ref(-1);     // -1 表示新增模式，>=0 表示編輯模式(與列表索引對應但通常用物件判斷)
// 當前編輯的項目內容
const editedItem = ref({
  memberId: null,
  email: "",
  memberName: "",
  memberPhoto: "",
  password: "",
  isAdmin: false,
});
// 預設項目內容 (用於重置表單)
const defaultItem = {
  memberId: null,
  email: "",
  memberName: "",
  memberPhoto: "",
  password: "",
  isAdmin: false,
};

// --- 計算屬性 ---
// 根據 editedIndex 判斷是對話框標題
const formTitle = computed(() => {
  return editedIndex.value === -1 ? "新增會員" : "編輯會員";
});

// --- 資料獲取 ---
// 當表格分頁或排序改變時觸發
const loadItems = async ({ page, itemsPerPage }) => {
  lastOptions = { page, itemsPerPage };
  await memberStore.fetchMembers(page, itemsPerPage);
};

// --- 使用者操作 ---

// 點擊編輯按鈕
const editItem = (item) => {
  editedIndex.value = members.value.indexOf(item); // 標記為編輯模式
  editedItem.value = { ...item }; // 複製物件，避免直接修改到列表中的資料
  dialog.value = true; // 開啟對話框
};

// 點擊刪除按鈕
const deleteItem = (item) => {
  editedIndex.value = members.value.indexOf(item);
  editedItem.value = { ...item };
  dialogDelete.value = true; // 開啟刪除確認框
};

// 確認刪除
const deleteItemConfirm = async () => {
  await memberStore.remove(editedItem.value.memberId);
  closeDelete(); // 關閉對話框
  await loadItems(lastOptions); // 重新載入列表
};

// 關閉編輯對話框
const close = () => {
  dialog.value = false;
  // 等待 DOM 更新後重置表單
  nextTick(() => {
    editedItem.value = { ...defaultItem };
    editedIndex.value = -1; // 回復到新增模式
  });
};

// 關閉刪除對話框
const closeDelete = () => {
  dialogDelete.value = false;
  nextTick(() => {
    editedItem.value = { ...defaultItem };
    editedIndex.value = -1;
  });
};

// 儲存 (新增或更新)
const save = async (item) => {
  if (editedIndex.value > -1) {
    // 編輯模式: 更新會員
    await memberStore.update(item.memberId, item);
  } else {
    // 新增模式: 建立會員
    await memberStore.create(item);
  }
  close(); // 關閉對話框
  await loadItems(lastOptions); // 重新載入列表
};
</script>

<template>
  <v-container>
    <v-card class="elevation-2 rounded-lg">
      <!-- 
        v-data-table-server: 伺服器端分頁表格
        v-model:items-per-page: 綁定每頁筆數
        :items-length: 總筆數 (需要後端提供)
        @update:options: 當分頁/排序改變時觸發 loadItems
      -->
      <v-data-table-server
        v-model:items-per-page="itemsPerPage"
        :headers="headers"
        :items="members"
        :items-length="totalItems"
        item-value="id"
        @update:options="loadItems"
      >
        <!-- 表格頂部工具列 -->
        <template v-slot:top>
          <v-toolbar flat>
            <v-toolbar-title>會員管理</v-toolbar-title>
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>

            <!-- 編輯/新增對話框組件 -->
            <MemberEditDialog
              v-model="dialog"
              :initial-item="editedItem"
              :title="formTitle"
              @save="save"
            />

            <!-- 刪除確認對話框組件 -->
            <ConfirmDialog
              v-model="dialogDelete"
              title="確定刪除此會員?"
              @confirm="deleteItemConfirm"
              @cancel="closeDelete"
            />

            <!-- 新增按鈕 (觸發 dialog = true) -->
            <template v-slot:activator="{ props }">
              <v-btn
                color="primary"
                dark
                class="mb-2"
                v-bind="props"
                @click="dialog = true"
              >
                新增會員
              </v-btn>
            </template>
          </v-toolbar>
        </template>

        <!-- 自定義欄位顯示: 管理員狀態 -->
        <template v-slot:item.isAdmin="{ item }">
          <v-icon v-if="item.isAdmin" color="success">mdi-check</v-icon>
          <v-icon v-else color="grey">mdi-close</v-icon>
        </template>

        <!-- 自定義欄位顯示: 大頭貼 -->
        <template v-slot:item.memberPhoto="{ item }">
          <v-avatar size="40">
            <v-img :src="getPhoto(item.memberPhoto)"></v-img>
          </v-avatar>
        </template>

        <!-- 自定義欄位顯示: 操作按鈕 -->
        <template v-slot:item.actions="{ item }">
          <v-icon class="me-2" size="small" @click="editItem(item)">
            mdi-pencil
          </v-icon>
          <v-icon size="small" @click="deleteItem(item)"> mdi-delete </v-icon>
        </template>
      </v-data-table-server>
    </v-card>
  </v-container>
</template>

<style scoped></style>
