<script setup>
import { computed } from "vue";

// 定義 Props
const props = defineProps({
  modelValue: Boolean, // 控制對話框顯示 (v-model)
  title: String,       // 標題
  message: {           // 訊息內容 (可選)
    type: String,
    default: "",
  },
});

// 定義 Events
const emit = defineEmits(["update:modelValue", "confirm", "cancel"]);

// 使用 computed 屬性簡化 v-model 的雙向綁定邏輯
// 當讀取 dialog.value 時，回傳 props.modelValue
// 當設定 dialog.value 時，觸發 update:modelValue 事件
const dialog = computed({
  get: () => props.modelValue,
  set: (val) => emit("update:modelValue", val),
});

// 取消按鈕
const onCancel = () => {
  dialog.value = false; // 這會觸發 computed 的 set，進而觸發 emit
  emit("cancel");       // 觸發 cancel 事件
};

// 確認按鈕
const onConfirm = () => {
  // 只觸發 confirm 事件，關閉對話框的邏輯由父組件決定 (通常父組件會在處理完後關閉)
  emit("confirm");
};
</script>

<template>
  <v-dialog v-model="dialog" max-width="500px">
    <v-card>
      <v-card-title class="text-h5" v-if="title">{{ title }}</v-card-title>
      <v-card-text v-if="message">{{ message }}</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue-darken-1" variant="text" @click="onCancel">
          取消
        </v-btn>
        <v-btn color="blue-darken-1" variant="text" @click="onConfirm">
          確定
        </v-btn>
        <v-spacer></v-spacer>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped></style>
