<script setup>
// 引入 Vue 核心功能
import { ref, watch } from "vue";
import { getPhoto } from "@/utils/commonUtil";

// 定義 Props
const props = defineProps({
  modelValue: Boolean, // 控制顯示
  initialItem: Object, // 初始資料
  title: String,       //標題
});

// 定義 Events
const emit = defineEmits(["update:modelValue", "save"]);

// 狀態
const dialog = ref(false);
const localItem = ref({});

// 雙向綁定 dialog 顯示狀態 (v-model)
watch(
  () => props.modelValue,
  (val) => {
    dialog.value = val;
  }
);

watch(
  () => dialog.value,
  (val) => {
    emit("update:modelValue", val);
  }
);

// 同步初始資料到本地編輯變數
watch(
  () => props.initialItem,
  (val) => {
    localItem.value = { ...val };
  },
  { deep: true, immediate: true }
);

// 關閉
const close = () => {
  dialog.value = false;
};

// 儲存
const save = () => {
  emit("save", localItem.value);
};

// 圖片上傳 (轉 Base64)
const handlePhotoUpload = (e) => {
  const file = e.target.files[0];
  if (!file) return;
  const reader = new FileReader();
  reader.readAsDataURL(file);
  reader.onload = () => {
    const result = reader.result;
    // 移除前綴，只保留 Base64 編碼部分
    localItem.value.photo = result.split(",")[1];
  };
};
</script>

<template>
  <v-dialog v-model="dialog" max-width="500px">
    <v-card>
      <v-card-title>
        <span class="text-h5">{{ title }}</span>
      </v-card-title>

      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" sm="6" md="12">
              <v-text-field
                v-model="localItem.name"
                label="商品名稱"
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="12">
              <v-text-field
                v-model.number="localItem.price"
                label="價格"
                type="number"
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="12">
              <v-text-field
                v-model="localItem.color"
                label="顏色"
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="12">
              <v-file-input
                label="商品圖片"
                @change="handlePhotoUpload"
                accept="image/*"
                prepend-icon="mdi-camera"
              ></v-file-input>
              <v-avatar size="64" class="mt-2" v-if="localItem.photo">
                <v-img :src="getPhoto(localItem.photo)"></v-img>
              </v-avatar>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue-darken-1" variant="text" @click="close">
          取消
        </v-btn>
        <v-btn color="blue-darken-1" variant="text" @click="save"> 儲存 </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped></style>
