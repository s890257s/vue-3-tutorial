<script setup>
// 引入 Vue 核心功能
import { ref, computed, nextTick } from "vue";
// 引入 Pinia Store
import { useProductStore } from "@/stores/productStore";
import { storeToRefs } from "pinia";
// 引入工具函式
import { getPhoto } from "@/utils/commonUtil";
// 引入組件
import ProductEditDialog from "@/components/ProductEditDialog.vue";
import ConfirmDialog from "@/components/ConfirmDialog.vue";

// 初始化 Store
const productStore = useProductStore();
// 將 Store 中的 state 解構為 ref
const { products, totalItems } = storeToRefs(productStore);

// --- 表格設定 ---
const itemsPerPage = ref(10);
const headers = [
  { title: "ID", key: "id", align: "start" },
  { title: "商品名稱", key: "name" },
  { title: "價格", key: "price" },
  { title: "顏色", key: "color" },
  { title: "圖片", key: "photo" },
  { title: "操作", key: "actions", sortable: false },
];
let lastOptions = { page: 1, itemsPerPage: 10 };

// --- 對話框與表單狀態 ---
const dialog = ref(false);
const dialogDelete = ref(false);
const editedIndex = ref(-1);
// 當前編輯的商品資料
const editedItem = ref({
  id: null,
  name: "",
  price: 0,
  color: "",
  photo: "",
});
// 預設商品資料
const defaultItem = {
  id: null,
  name: "",
  price: 0,
  color: "",
  photo: "",
};

// --- 計算屬性 ---
const formTitle = computed(() => {
  return editedIndex.value === -1 ? "新增商品" : "編輯商品";
});

// --- 資料獲取 ---
const loadItems = async ({ page, itemsPerPage }) => {
  lastOptions = { page, itemsPerPage };
  await productStore.fetchProducts(page, itemsPerPage);
};

// --- 使用者操作 ---
const editItem = (item) => {
  editedIndex.value = products.value.indexOf(item);
  editedItem.value = { ...item };
  dialog.value = true;
};

const deleteItem = (item) => {
  editedIndex.value = products.value.indexOf(item);
  editedItem.value = { ...item };
  dialogDelete.value = true;
};

const deleteItemConfirm = async () => {
  await productStore.remove(editedItem.value.id);
  closeDelete();
  await loadItems(lastOptions);
};

// 關閉對話框
const close = () => {
  dialog.value = false;
  nextTick(() => {
    editedItem.value = { ...defaultItem };
    editedIndex.value = -1;
  });
};

const closeDelete = () => {
  dialogDelete.value = false;
  nextTick(() => {
    editedItem.value = { ...defaultItem };
    editedIndex.value = -1;
  });
};

// 儲存商品
const save = async (item) => {
  if (editedIndex.value > -1) {
    // 更新
    await productStore.update(item.id, item);
  } else {
    // 新增
    await productStore.create(item);
  }
  close();
  await loadItems(lastOptions);
};
</script>

<template>
  <v-container>
    <v-card class="elevation-2 rounded-lg">
      <!-- 伺服器端分頁表格 -->
      <v-data-table-server
        v-model:items-per-page="itemsPerPage"
        :headers="headers"
        :items="products"
        :items-length="totalItems"
        item-value="id"
        @update:options="loadItems"
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-toolbar-title>商品管理</v-toolbar-title>
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>

            <ProductEditDialog
              v-model="dialog"
              :initial-item="editedItem"
              :title="formTitle"
              @save="save"
            />

            <ConfirmDialog
              v-model="dialogDelete"
              title="確定刪除此商品?"
              @confirm="deleteItemConfirm"
              @cancel="closeDelete"
            />

            <template v-slot:activator="{ props }">
              <v-btn
                color="primary"
                dark
                class="mb-2"
                v-bind="props"
                @click="dialog = true"
              >
                新增商品
              </v-btn>
            </template>
          </v-toolbar>
        </template>

        <!-- 顯示商品圖片 -->
        <template v-slot:item.photo="{ item }">
          <v-avatar size="40" rounded="0">
            <v-img :src="getPhoto(item.photo)"></v-img>
          </v-avatar>
        </template>

        <!-- 操作按鈕 -->
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
