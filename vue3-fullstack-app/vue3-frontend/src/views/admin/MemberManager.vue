<script setup>
// 引入
import { ref, computed, nextTick } from "vue";
import { useMemberStore } from "@/stores/memberStore";
import { storeToRefs } from "pinia";
import { getPhoto } from "@/utils/commonUtil";
import MemberEditDialog from "@/components/MemberEditDialog.vue";
import ConfirmDialog from "@/components/ConfirmDialog.vue";

// Store 與 狀態
const memberStore = useMemberStore();
const { members, totalItems } = storeToRefs(memberStore);

// 表格設定
const itemsPerPage = ref(10);
const headers = [
  { title: "ID", key: "memberId", align: "start" },
  { title: "Email", key: "email" },
  { title: "姓名", key: "memberName" },
  { title: "大頭貼", key: "memberPhoto" },
  { title: "操作", key: "actions", sortable: false },
];
let lastOptions = { page: 1, itemsPerPage: 10 };

// 對話框與表單狀態
const dialog = ref(false);
const dialogDelete = ref(false);
const editedIndex = ref(-1);
const editedItem = ref({
  memberId: null,
  email: "",
  memberName: "",
  memberPhoto: "",
  password: "",
});
const defaultItem = {
  memberId: null,
  email: "",
  memberName: "",
  memberPhoto: "",
  password: "",
};

// 計算屬性
const formTitle = computed(() => {
  return editedIndex.value === -1 ? "新增會員" : "編輯會員";
});

// 資料獲取
const loadItems = async ({ page, itemsPerPage }) => {
  lastOptions = { page, itemsPerPage };
  await memberStore.fetchMembers(page, itemsPerPage);
};

// 使用者操作
const editItem = (item) => {
  editedIndex.value = members.value.indexOf(item);
  editedItem.value = { ...item };
  dialog.value = true;
};

const deleteItem = (item) => {
  editedIndex.value = members.value.indexOf(item);
  editedItem.value = { ...item };
  dialogDelete.value = true;
};

const deleteItemConfirm = async () => {
  await memberStore.remove(editedItem.value.memberId);
  closeDelete();
  await loadItems(lastOptions);
};

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

const save = async (item) => {
  if (editedIndex.value > -1) {
    await memberStore.update(item.memberId, item);
  } else {
    // 新增會員
    await memberStore.create(item);
  }
  close();
  await loadItems(lastOptions);
};
</script>

<template>
  <v-container>
    <v-card class="elevation-2 rounded-lg">
      <v-data-table-server
        v-model:items-per-page="itemsPerPage"
        :headers="headers"
        :items="members"
        :items-length="totalItems"
        item-value="id"
        @update:options="loadItems"
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-toolbar-title>會員管理</v-toolbar-title>
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>

            <MemberEditDialog
              v-model="dialog"
              :initial-item="editedItem"
              :title="formTitle"
              @save="save"
            />

            <ConfirmDialog
              v-model="dialogDelete"
              title="確定刪除此會員?"
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
                新增會員
              </v-btn>
            </template>
          </v-toolbar>
        </template>

        <template v-slot:item.memberPhoto="{ item }">
          <v-avatar size="40">
            <v-img :src="getPhoto(item.memberPhoto)"></v-img>
          </v-avatar>
        </template>

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
