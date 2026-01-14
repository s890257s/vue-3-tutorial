// 引入 Vue 的 createApp 函式，這是建立 Vue 應用程式的起點
// `createApp` 是 Vue 3 的 API，用來產生一個應用程式實體
import { createApp } from "vue";
import App from "./App.vue";
// 建立應用程式實體
const app = createApp(App);

// 引入 Vue Router
// 這是我們定義的路由設定檔 (src/router/index.js)
import router from "./router";
// 告訴 Vue 應用程式使用這個路由器
app.use(router);

// 引入 Pinia
// Pinia 是 Vue 3 官方推薦的狀態管理庫 (取代 Vuex)
import { createPinia } from "pinia";
// 建立並使用 Pinia
app.use(createPinia());

// 引入 Vuetify
// Vuetify 是一個基於 Material Design 的 Vue UI 框架
import "@mdi/font/css/materialdesignicons.css"; // 引入圖示字型檔
import "vuetify/styles";                        // 引入 Vuetify 樣式
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";

// 建立 Vuetify 實體，並載入所有組件與指令
const vuetify = createVuetify({ components, directives });
app.use(vuetify);

// 引入 Vue Toastification
// 這是一個用來顯示通知訊息 (Toast) 的套件
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";
// 設定全域通知選項
app.use(Toast, {
  position: "top-right", // 訊息顯示在右上角
  timeout: 3000,         // 訊息顯示 3 秒後自動消失
});

// 掛載應用程式
// 將 Vue 應用程式掛載到 HTML 中的 #app 元素上 (通常在 index.html)
app.mount("#app");
