// 建立 APP
import { createApp } from "vue";
import App from "./App.vue";
const app = createApp(App);

// 路由
import router from "./router";
app.use(router);

// Pinia
import { createPinia } from "pinia";
app.use(createPinia());

// Vuetify
import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
const vuetify = createVuetify({ components, directives });
app.use(vuetify);

// Toastification
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";
app.use(Toast, {
  position: "top-right",
  timeout: 3000,
});

// 掛載
app.mount("#app");
