<script setup>
// 引入 Vue 核心功能
import { ref, watch } from "vue";
import { getPhoto } from "@/utils/commonUtil";

// 定義組件接收的屬性 (Props)
const props = defineProps({
  modelValue: Boolean, // 控制對話框顯示的雙向綁定變數 (v-model)
  initialItem: Object, // 傳入的初始資料 (編輯時為該會員資料，新增時為空)
  title: String,       // 對話框標題
});

// 定義組件可觸發的事件 (Emits)
const emit = defineEmits(["update:modelValue", "save"]);

// 內部狀態
const dialog = ref(false); // 控制內部 v-dialog 的顯示
const localItem = ref({}); // 用於表單綁定的本地資料，避免直接修改 props

// 監聽 props.modelValue 變化，同步到內部 dialog
// 這是為了實現 v-model (父組件控制顯示)
watch(
  () => props.modelValue,
  (val) => {
    dialog.value = val;
  }
);

// 監聽內部 dialog 變化，通知父組件更新 modelValue
watch(
  () => dialog.value,
  (val) => {
    emit("update:modelValue", val);
  }
);

// 當 initialItem 變更時，同步到 localItem
// immediate: true 表示組件初始化時也會執行一次
// deep: true 表示深度監聽物件內部變化
watch(
  () => props.initialItem,
  (val) => {
    // 使用解構賦值複製物件，避免直接參考同一個記憶體位址
    localItem.value = { ...val };
  },
  { deep: true, immediate: true }
);

// 關閉對話框
const close = () => {
  dialog.value = false;
};

// 儲存資料
const save = () => {
  // 觸發 save 事件，將編輯後的資料回傳給父組件
  emit("save", localItem.value);
};

// 檔案上傳處理 (處理圖片轉 Base64)
const handlePhotoUpload = (e) => {
  const file = e.target.files[0];
  if (!file) return;

  // 使用 FileReader 讀取檔案內容
  const reader = new FileReader();
  reader.readAsDataURL(file); // 讀取為 Data URL (Base64)
  reader.onload = () => {
    // 讀取完成後執行
    // result 格式為 "data:image/jpeg;base64,....."
    const result = reader.result;
    // 我們只需要逗號後面的 Base64 字串
    localItem.value.memberPhoto = result.split(",")[1];
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
                v-model="localItem.email"
                label="Email"
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="12">
              <v-text-field
                v-model="localItem.memberName"
                label="姓名"
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="12">
              <v-switch
                v-model="localItem.isAdmin"
                label="管理員權限"
                color="primary"
                hide-details
              ></v-switch>
            </v-col>
            <v-col cols="12" sm="6" md="12" v-if="!localItem.memberId">
              <v-text-field
                v-model="localItem.password"
                label="密碼"
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="12">
              <v-file-input
                label="大頭貼"
                @change="handlePhotoUpload"
                accept="image/*"
                prepend-icon="mdi-camera"
              ></v-file-input>
              <v-avatar size="64" class="mt-2" v-if="localItem.memberPhoto">
                <v-img :src="getPhoto(localItem.memberPhoto)"></v-img>
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
