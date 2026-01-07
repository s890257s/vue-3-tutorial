import { ref } from "vue";
import { defineStore } from "pinia";

export const useUiStore = defineStore("ui", () => {
  const drawer = ref(true);

  const adminMenu = ref([
    {
      title: "會員管理",
      prependIcon: "mdi-account",
      link: true,
      to: "/admin/members",
    },
    {
      title: "商品管理",
      prependIcon: "mdi-cellphone",
      link: true,
      to: "/admin/products",
    },
    {
      title: "訂單管理",
      prependIcon: "mdi-receipt-text-edit-outline",
      link: true,
      to: "/admin/orders",
    },
  ]);

  return { drawer, adminMenu };
});
