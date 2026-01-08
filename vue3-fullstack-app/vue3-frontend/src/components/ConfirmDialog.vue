<script setup>
import { computed } from "vue";

const props = defineProps({
  modelValue: Boolean,
  title: String,
  message: {
    type: String,
    default: "",
  },
});

const emit = defineEmits(["update:modelValue", "confirm", "cancel"]);

const dialog = computed({
  get: () => props.modelValue,
  set: (val) => emit("update:modelValue", val),
});

const onCancel = () => {
  dialog.value = false;
  emit("cancel");
};

const onConfirm = () => {
  // 保持對話框開啟？通常是的，父組件會在非同步操作後或立即關閉它。
  // 根據目前的邏輯，由父組件負責關閉。
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
