import { defineStore } from "pinia";
import { ref } from "vue";
import { getMembers, createMember, updateMember, deleteMember } from "@/api/members";

export const useMemberStore = defineStore("member", () => {
  // State
  const members = ref([]);
  const totalItems = ref(0);

  const fetchMembers = async (page, size) => {
    const response = await getMembers(page, size);
    members.value = response.data.content;
    totalItems.value = response.data.totalElements;
  };

  const create = async (member) => {
    await createMember(member);
  };

  const update = async (id, member) => {
    await updateMember(id, member);
  };

  const remove = async (id) => {
    await deleteMember(id);
  };

  return {
    members,
    totalItems,
    fetchMembers,
    create,
    update,
    remove,
  };
});
