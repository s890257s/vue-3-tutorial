# Vue 3 前端開發教學手冊

歡迎來到 Vue 3 教學手冊！本系列文章將帶領您從 ES6 基礎開始，一路深入 Vue 3 的核心概念、元件化開發、路由管理、狀態管理，最後介紹 Vuetify UI 框架。

我們會採用 **循序漸進** 的教學方式：
1. 先用 **CDN** 模式快速上手語法。
2. 再進入 **Vite** 專案模式學習現代化開發架構。
3. 最後整合 **Router**、**Pinia** 與 **Axios**，完成一個具備完整功能的 SPA 應用程式。

---

## 📚 章節列表

### [章節 1 ｜ ES6 必備基礎](./Chapter1_ES6.md)
在進入 Vue 之前，我們先把地基打穩。這章節整理了開發 Vue 最常用的 JavaScript 語法。
- `let` 與 `const` 變數宣告
- 箭頭函式 (Arrow Function)
- 模板字串 (Template Literals)
- 解構賦值 (Destructuring) 與 展開運算子 (Spread Operator)
- 常用陣列方法 (`map`, `filter`, `reduce`...)
- 非同步處理 (`async` / `await`)
- 模組化 (`import` / `export`)

### [章節 2 ｜ Vue 基礎（CDN 版本）](./Chapter2_VueBasics.md)
不需安裝複雜環境，直接用瀏覽器就能寫 Vue！適合初學者理解核心觀念。
- Vue 實例的建立與掛載
- 響應式系統：`ref` vs `reactive`
- 指令大法：`v-bind`, `v-on`, `v-model`, `v-if`, `v-for`
- 計算屬性 (`computed`) 與 監聽器 (`watch`)
- 生命週期鉤子 (Lifecycle Hooks)

### [章節 3 ｜ Vite + 元件化 + Plugins](./Chapter3_ViteComponents.md)
進入現代化前端開發領域，學習如何使用建置工具與元件化思維。
- **Vite** 專案建置與結構解析
- **SFC** (Single File Component) 單一檔案元件
- `<script setup>` 語法糖
- 元件溝通：`defineProps` (父傳子) 與 `defineEmits` (子傳父)
- 插槽 (`slot`) 與 動態元件
- Composables (組合式函式) 初體驗

### [章節 4 ｜ Vue Router](./Chapter4_VueRouter.md)
為單頁應用程式 (SPA) 加上地圖，實現頁面切換與導航。
- 基礎路由設定與 `<router-link>`
- 動態路由 (`/user/:id`) 與 404 頁面
- 巢狀路由 (Nested Routes) 與 Layout 設計
- 程式化導航 (`useRouter`, `useRoute`)
- 路由守衛 (Navigation Guards) 權限控管

### [章節 5 ｜ API + Pinia](./Chapter5_ApiPinia.md)
賦予應用程式靈魂，處理資料請求與全域狀態管理。
- **Axios**：HTTP 請求封裝、攔截器 (Interceptors) 設定
- **Composables**：封裝 `useFetch`、`useToggle` 等共用邏輯
- **Pinia**：Vue 3 官方推薦狀態管理庫
- Setup Store 寫法與 `storeToRefs` 正確解構
- 整合 API 與 Store 實現登入與資料邏輯分離

### [章節 6 ｜ UI 元件庫 & Vuetify](./Chapter6_Vuetify.md)
站在巨人的肩膀上，快速打造專業且美觀的響應式介面。
- UI 元件庫的概念與優勢
- **Vuetify** 安裝與配置
- 響應式格線系統 (Grid System)
- 強大的輔助類別 (Helpers)：Spacing, Typography, Flex
- 常用元件實戰：Button, Card, Form, Dialog

---

## 🛠️ 開發環境建議

- **編輯器**：Visual Studio Code
- **必裝套件**：
  - Vue - Official
  - Prettier - Code formatter
  - Vue 3 Snippets
- **執行環境**：Node.js LTS 版本

---

祝各學習愉快！Happy Coding! 🚀
