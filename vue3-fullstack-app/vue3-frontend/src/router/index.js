// 引入 Vue Router 的函式
// createRouter: 建立路由器
// createWebHistory: 使用 HTML5 History 模式 (網址不帶 #)
import { createRouter, createWebHistory } from "vue-router";

// 定義路由表
const router = createRouter({
  // 設定歷史模式，import.meta.env.BASE_URL 是 Vite 的環境變數，通常是 '/'
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      // 定義路徑 '/admin'
      path: "/admin",
      // 動態載入組件 (Lazy Loading) - 當使用者訪問此路徑時才載入 AdminLayout.vue
      // 這樣可以減少初始載入的檔案大小，優化效能
      component: () => import("@/views/AdminLayout.vue"),
      // 巢狀路由 (Children) - 以下路由會渲染在 AdminLayout 的 <router-view> 中
      children: [
        {
          // 對應網址: /admin/members
          path: "members",
          component: () => import("@/views/admin/MemberManager.vue"),
        },
        {
          // 對應網址: /admin/products
          path: "products",
          component: () => import("@/views/admin/ProductManager.vue"),
        },
        {
          // 對應網址: /admin/orders
          path: "orders",
          component: () => import("@/views/admin/OrderManager.vue"),
        },
      ],
    },
    // 你可以在這裡加入更多路由，例如登入頁面、前台頁面等
  ],
});

export default router;
