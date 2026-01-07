import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/admin",
      component: () => import("@/layouts/AdminLayout.vue"),
      children: [
        {
          path: "members",
          component: () => import("@/views/admin/MemberManager.vue"),
        },
        {
          path: "products",
          component: () => import("@/views/admin/ProductManager.vue"),
        },
        {
          path: "orders",
          component: () => import("@/views/admin/OrderManager.vue"),
        },
      ],
    },
  ],
});

export default router;
