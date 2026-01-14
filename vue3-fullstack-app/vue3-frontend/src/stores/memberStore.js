import { defineStore } from "pinia";
import { ref } from "vue";
import { getMembers, createMember, updateMember, deleteMember } from "@/api/members";

// 定義 Store (狀態管理倉庫)
// defineStore(id, setupFunction)
// id: Store 的唯一識別碼，通常與檔名有關 (這裡叫 'member')
// setupFunction: 類似 Vue 的 setup() 語法，用來定義 State (狀態) 與 Actions (動作)
export const useMemberStore = defineStore("member", () => {
  // State (狀態) -> 相當於 Vue 組件中的 data
  // 這裡存放會員列表資料與總筆數
  const members = ref([]);
  const totalItems = ref(0);

  // Actions (動作) -> 相當於 Vue 組件中的 methods
  // 用來處理業務邏輯、呼叫 API 並更新 State

  // 取得會員列表 (分頁)
  const fetchMembers = async (page, size) => {
    // 呼叫 API
    const response = await getMembers(page, size);
    // 更新 State
    // response.data.content 是 Spring Boot Page 結構的內容
    members.value = response.data.content;
    // response.data.totalElements 是總筆數
    totalItems.value = response.data.totalElements;
  };

  // 新增會員
  const create = async (member) => {
    await createMember(member);
    // 新增成功後，通常會由 UI 層決定是否重新載入列表
  };

  // 更新會員
  const update = async (id, member) => {
    await updateMember(id, member);
  };

  // 刪除會員
  const remove = async (id) => {
    await deleteMember(id);
  };

  // 回傳要暴露給組件使用的屬性與方法
  return {
    members,
    totalItems,
    fetchMembers,
    create,
    update,
    remove,
  };
});
