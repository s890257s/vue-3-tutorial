# 章節 4 ｜ Vue Router

## <a id="目錄"></a>目錄

- [4-1 基礎路由設定](#CH4-1)
  - [1. 安裝與配置](#CH4-1-1)
  - [2. 路由視圖與連結](#CH4-1-2)
  - [3. 程式化導航](#CH4-1-3)
- [4-2 動態路由 (Dynamic Route Matching)](#CH4-2)
  - [1. 基本語法介紹](#CH4-2-1)
  - [2. 404 頁面 (正則表達式配對)](#CH4-2-2)
  - [3. 監聽路由變化](#CH4-2-3)
- [4-3 巢狀路由 (Nested Routes)](#CH4-3)
  - [1. 基本語法介紹](#CH4-3-1)
  - [2. 實戰範例：Layout 系統](#CH4-3-2)
- [4-4 路由守衛 (Navigation Guards)](#CH4-4)
  - [1. 路由守衛的意義](#CH4-4-1)
  - [2. 全域前置守衛](#CH4-4-2)
  - [3. 路由 Meta 欄位](#CH4-4-3)

### 序

在前一章，我們學會了如何透過 **Vite** 建立專案，並掌握了 **元件化** 的核心概念，學會將網頁拆解成獨立的積木。

但是，你有沒有發現目前的應用程式似乎少了點什麼？沒錯，就是「**切換頁面**」的功能！

在現代網頁開發中，**單頁應用程式 (SPA)** 已成為主流。與傳統網頁不同，SPA 並不需要頻繁地向伺服器請求整張新的 HTML，而是透過「**前端路由**」來動態替換畫面上的內容，創造出流暢無比的使用者體驗。

本章節將帶你深入學習 **Vue Router**，這是 Vue 官方的路由管理器。我們將學習如何定義路徑、傳遞參數，以及如何保護特定的頁面（路由守衛）。

準備好為你的應用程式加上導航系統了嗎？讓我們繼續看下去！

---

## <a id="CH4-1"></a>[4-1 基礎路由設定](#目錄)

要建立一個多頁面的 Vue 應用程式，並不需要真的建立多個 HTML 檔案。我們需要的是「**路由 (Router)**」。

需要特別釐清的是，Vue Router 實作的 **單頁應用程式 (SPA)** 與傳統的多頁面網站運作方式截然不同。  
在 SPA 中，我們始終停留在同一個 HTML 頁面 (**index.html**) 裡。所謂的「換頁」，本質上其實是 Vue 根據網址的變化，**動態銷毀舊的元件 (Component)，並掛載新的元件**。

這就是為什麼頁面切換如此快速且流暢，因為瀏覽器不需要重新向伺服器請求並重新載入整張網頁。簡單來說，**路由就是管理網址與元件之間對應關係的機制**。

### <a id="CH4-1-1"></a>[1. 安裝與配置](#目錄)

如果你在建立專案時 (執行 `npm create vue`) 已經選擇了 Vue Router，那麼系統已經幫你設定好了。如果你是中途才決定加入，可以透過 npm 安裝：

```bash
npm install vue-router
```

接著，我們通常會依照慣例建立 `src/router/index.js` 檔案來管理路由設定。

> **💡 備註：為什麼是 `index.js`？**
>
> 這是 Node.js 與現代前端工具（如 Vite, Webpack）的模組解析慣例。
> 當我們 `import` 一個資料夾路徑時（例如 `import router from './router'`），系統會自動尋找該資料夾下的 `index.js` 檔案作為入口。
>
> 同樣的規則也適用於 TypeScript！若資料夾下有 `index.ts`，我們同樣只需引入資料夾路徑，無需寫成繁瑣的 `./router/index.ts`。這讓程式碼看起來更乾淨且易於維護。

**`src/router/index.js` 檔案內容：**

```javascript
// 基本配置
import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const router = createRouter({
  // 設定路由模式 (History Mode)
  history: createWebHistory(import.meta.env.BASE_URL),

  // 定義路由表 (Routes)
  routes: [
    {
      path: "/",
      component: HomeView, // 標準載入方式
    },
    {
      path: "/about",
      // 懶載入 (Lazy Loading)
      // 只有當使用者訪問 '/about' 時，才會下載 AboutView.vue 的程式碼
      // 這對於大型應用程式的效能優化非常重要！
      component: () => import("../views/AboutView.vue"),
    },
    {
      path: "/shop",
      name: "shop", // 路由名稱 (選填)，可用於程式化導航，當路徑較長時使用名稱會更簡潔
      component: () => import("../views/ShopView.vue"),
    },
  ],
});

export default router;
```

> **💡 備註：路由模式的選擇 (History vs Hash)**
>
> Vue Router 提供了兩種主要的路由模式，這也是新手會感到困惑的地方。讓我們來詳細比較一下：
>
> **1. Hash 模式 (`createWebHashHistory`)**
>
> - **原理**  
>   利用 URL 中的 `#` 符號 (Hash) 來模擬路由。因為 `#` 後面的內容**不會**被發送給伺服器，所以切換路由時，瀏覽器不會真的向伺服器請求新頁面。
> - **網址外觀**  
>   `https://example.com/#/user/id` (會多一個 `#`)
> - **優點**  
>   **部署最簡單**。完全不需要伺服器端的設定，非常適合部署到 GitHub Pages 或任何靜態空間。
> - **缺點**  
>   網址較醜，且 `#` 在某些 SEO 或第三方登入回調中可能會造成問題。
> - **新手建議**  
>   **建議初學者先使用此模式**，避免部署後遇到 404 錯誤而受挫。
>
> **2. History 模式 (`createWebHistory`)**
>
> - **原理**  
>   利用 HTML5 的 History API 來達成網址變換。
> - **網址外觀**  
>   `https://example.com/user/id` (乾淨、標準)
> - **優點**  
>   網址美觀，符合一般使用者習慣。
> - **缺點**  
>    **需要後端伺服器配合設定**。
>   - 【問題】  
>     當使用者直接在瀏覽器輸入 `https://example.com/user/id` 並按下 Enter 時，瀏覽器會向伺服器請求 `/user/id` 這個檔案。但伺服器上根本沒有這個檔案（只有 `index.html`），所以會回傳 404 錯誤。
>   - 【解法】  
>     必須在伺服器端（如 Apache, Nginx）設定「Rewrite Rule」，將所有找不到的路徑都導回 `index.html`，讓 Vue Router 接手處理。
>
> **🤔 為什麼使用 Vite 開發時，History 模式也沒問題？**  
> 你可能會發現，在電腦上用 `npm run dev` 啟動時，就算用 History 模式也能正常重新整理頁面。  
> 這是不是代表我不用設定後端？ **不是的！** 這是因為 Vite 的開發伺服器這層「中介」已經自動幫我們處理好「導回 `index.html`」這件事了。  
> 但請記住，這只是開發環境的福利，一旦打包部署到正式主機，如果沒有設定好伺服器，History 模式是一定會掛掉的！

### <a id="CH4-1-2"></a>[2. 路由視圖與連結](#目錄)

設定好 Router 後，別忘了在 `main.js` 中註冊它。

**`src/main.js`：**

```javascript
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router"; // 引入我們剛設定好的 router

const app = createApp(App);

app.use(router); // 使用 router 插件
app.mount("#app");
```

最後，在 `App.vue` (或是任何 Layout 元件) 中，我們需要告訴 Vue Router 「導航連結要放哪？」以及「切換後的頁面要顯示在哪？」。

**`src/App.vue`：**

```html
<template>
  <header>
    <nav>
      <!-- 1. 使用 router-link 建立導航連結 -->
      <!-- 它會被渲染成 <a> 標籤，但點擊時不會重新整理頁面 -->
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </nav>
  </header>

  <main>
    <!-- 2. 使用 router-view 指定頁面顯示的位置 -->
    <!-- 當網址切換時，對應的元件就會顯示在這裡 -->
    <router-view />
  </main>
</template>
```

> **💡 備註：`<a href>` vs `<router-link>` (重要觀念)**
>
> 在 SPA 開發中，請務必搞清楚這兩者的差異：
>
> - **`<a>` 標籤 (`<a href="/about">`)**：  
>   這是標準的 HTML 連結。點擊後，瀏覽器會**向伺服器重新請求整個頁面**，導致瀏覽器重新整理 (Full Page Reload)。這會破壞 SPA 的體驗，並讓應用程式的狀態（如變數資料）全部重置。**除非是要連結到外部網站，否則不建議使用。**
>
> - **`<router-link>` 標籤 (`<router-link to="/about">`)**：  
>   這是 Vue Router 提供的特製元件。點擊後，它會**攔截**瀏覽器的預設行為，改用 HTML5 History API 來更換網址，並通知 Vue Router 切換元件。整個過程**不會重新整理頁面**，體驗非常流暢。

### <a id="CH4-1-3"></a>[3. 程式化導航](#目錄)

除了在 Template 中使用連結，我們也可以透過 JavaScript 程式碼來控制路由跳轉。

#### useRouter (JS 控制跳轉)

當我們需要在完成某些邏輯後（例如：按鈕點擊事件、表單送出後、計時器結束後）才進行頁面跳轉，就必須使用 `useRouter` hook。

常用方法：

1.  **`router.push(path)`**：前往新頁面，**會**加入歷史紀錄 (可按上一頁返回)。
2.  **`router.replace(path)`**：前往新頁面，**使用取代方式**，**不會**加入歷史紀錄 (常用於登入後跳轉，避免使用者按上一頁又回到登入頁)。
3.  **`router.go(n)`**：前往或後退 n 頁。

```javascript
<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const handleLogin = async () => {
  // 1. 執行登入邏輯...
  await loginAPI()

  // 2. 登入成功後，使用 replace 跳轉到首頁 (使用者按上一頁時不會回到登入頁)
  router.replace({ name: 'home' })
}

const goToProduct = (id) => {
  // 帶查詢參數的跳轉 (Query) -> /search?q=vue
  router.push({ path: '/search', query: { q: 'vue' } })
}
</script>
```

---

## <a id="CH4-2"></a>[4-2 動態路由 (Dynamic Route Matching)](#目錄)

很多時候，我們需要把某種模式的路由對應到同一個元件。例如，我們有一個「商品詳情頁」，網址可能是 `/product/1`、`/product/2`...，這些頁面使用的是同一個元件，只是 ID 不同。

我們不可能為每個商品 ID 都寫一個路由，這樣會讓路由表變得非常龐大且難以維護。  
這時候我們就需要 **動態路由**。

### <a id="CH4-2-1"></a>[1. 基本語法介紹](#目錄)

我們可以在路由路徑中使用「冒號」 `:` 來定義參數。

**路由設定：**

```javascript
/* router/index.js */
const routes = [
  // ... 其他路由
  {
    // 定義參數 :id
    path: "/product/:id",
    name: "product",
    component: () => import("../views/ProductDetail.vue"),
  },
];
```

**在元件中取得參數：**

我們使用 `useRoute` 這個 Hook 來取得當前路由的資訊 (如 params, query, meta)。  
注意區分 `useRouter` (路由器，執行動作) 與 `useRoute` (路由物件，取得資訊)。

```html
<!-- ProductDetail.vue -->
<script setup>
  import { useRoute } from "vue-router";

  const route = useRoute();
  // 取得網址上的 id 參數【/product/123】
  console.log(route.params.id);

  // 取得網址上的 query 參數【/product/123?q=vue】
  console.log(route.query.q);
</script>

<template>
  <h1>商品 ID: {{ route.params.id }}</h1>
  <h2>查詢參數: {{ route.query.q }}</h2>
</template>
```

**如何跳轉至動態路由：**

我們可以使用 `<router-link>` 或 `router.push` 來進行跳轉。  
**注意：若要使用 `params`，請搭配 `name` (路由名稱) 使用，而非 `path`。**

```javascript
import { useRouter } from "vue-router";
const router = useRouter();

// 1. 帶參數的跳轉 (Params) -> /product/123
// 需對應路由設定: path: '/product/:id'
const goToProduct = (id) => {
  router.push({
    name: "product", // 使用 name
    params: { id },
  });
};

// 2. 帶查詢參數的跳轉 (Query) -> /search?q=vue
// 不需要特定的路由設定
const search = (keyword) => {
  router.push({
    path: "/search",
    query: { q: keyword },
  });
};
```

**Template 中的使用方式 (`<router-link>`)：**

```html
<!-- 1. 帶參數的連結 (Params) -->
<router-link :to="{ name: 'product', params: { id: 123 } }">
  前往商品 123
</router-link>

<!-- 2. 帶查詢參數的連結 (Query) -->
<router-link :to="{ path: '/search', query: { q: 'vue' } }">
  搜尋 Vue
</router-link>
```

### <a id="CH4-2-2"></a>[2. 404 頁面 (正則表達式配對)](#目錄)

在動態路由中，還有一種特殊的需求：**捕捉所有未定義的路由**（就像伺服器的 404 Not Found）。
我們可以利用括號中的**正則表達式 (Regular Expression)** 來達成。

```javascript
const routes = [
  // ... 你的其他路由

  // [注意] 這個路由必須放在最後面！
  // :pathMatch(.*)* 代表比對所有路徑內容
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    component: () => import("../views/NotFound.vue"),
  },
];
```

這樣一來，只要使用者輸入的網址沒有匹配到前面定義的任何一個路由，就會掉入這個 `NotFound` 頁面。

### <a id="CH4-2-3"></a>[3. 監聽路由變化](#目錄)

這裡有一個常見的陷阱！

當使用者從 `/product/1` 切換到 `/product/2` 時，因為兩個路由使用**同一個元件**，Vue 為了效能考量，會**直接重複使用 (Reuse)** 這個元件實例，而不會銷毀再重建。

這意味著：**元件的生命週期 Hook (如 `onMounted`) 不會再次被觸發！**

如果你原本在 `onMounted` 裡呼叫 API 抓資料，切換商品時畫面就不會更新。解決方法是使用 `watch` 來監聽 `route.params` 的變化。

```html
<script setup>
  import { ref, onMounted, watch } from "vue";
  import { useRoute } from "vue-router";

  const route = useRoute();
  const productData = ref(null);

  // 模擬抓取資料的函式
  const fetchProduct = (id) => {
    console.log(`正在抓取商品 ${id} 的資料...`);
    // productData.value = ...
  };

  // 1. 第一次進入時執行
  onMounted(() => {
    fetchProduct(route.params.id);
  });

  // 2. 監聽 ID 變化，當路由改變時再次執行
  watch(
    () => route.params.id,
    (newId, oldId) => {
      // 只有當 id 真的存在時才執行 (避免切換到其他頁面時報錯)
      if (newId) {
        fetchProduct(newId);
      }
    }
  );
</script>

<!-- 或者使用 watch 的 immediate 特性-->
<script setup>
  import { watch } from "vue";
  import { useRoute } from "vue-router";

  const route = useRoute();

  const fetchProduct = (id) => {
    console.log(`正在抓取商品 ${id} 的資料...`);
  };

  // 使用 immediate: true，會在偵聽開始時立即觸發一次 callback
  // 這樣就不需要額外寫 onMounted 了，程式碼更簡潔
  watch(
    () => route.params.id,
    (newId) => {
      if (newId) fetchProduct(newId);
    },
    { immediate: true }
  );
</script>
```

---

## <a id="CH4-3"></a>[4-3 巢狀路由 (Nested Routes)](#目錄)

在真實的應用程式中，頁面通常會有更複雜的結構。例如一個「後台管理系統」，它可能有通用的側邊欄 (Sidebar) 和頂部導航 (Header)，而中間的內容區域則會隨著路由切換。

這種「大頁面裡面包小頁面」的結構，就是 **巢狀路由** 的絕佳應用場景。

### <a id="CH4-3-1"></a>[1. 基本語法介紹](#目錄)

要在路由中定義巢狀結構，我們需要使用 `children` 選項。

```javascript
const routes = [
  {
    path: "/admin",
    component: AdminLayout, // 父路由元件 (通常是一個 Layout)
    // 定義子路由
    children: [
      {
        path: "", // 當網址為 /admin 時，顯示 Dashboard
        name: "admin-dashboard",
        component: AdminDashboard,
      },
      {
        path: "users", // 當網址為 /admin/users 時，顯示 UserList
        name: "admin-users",
        component: UserList,
      },
    ],
  },
];
```

**關鍵點：**

1. **Layout 元件要包含 `<router-view>`**：父元件 (`AdminLayout`) 的 Template 中必須要有 `<router-view>` 標籤，子路由的元件 (`AdminDashboard` 或 `UserList`) 才會渲染在裡面。
2. **路徑寫法**：子路由的 `path` 不需要加開頭的 `/` (例如寫 `'users'` 而不是 `'/users'` )，Vue Router 會自動幫你拼接成 `/admin/users`。如果是 `/` 開頭，會被視為根路徑。

### <a id="CH4-3-2"></a>[2. 實戰範例：Layout 系統](#目錄)

為了讓專案架構更清晰，我們通常會將元件分為三種類型：

1.  **Layouts (佈局)**：決定頁面的整體骨架，如頁首、側邊欄。
2.  **Views (頁面)**：對應到路由的頁面內容。
3.  **Components (元件)**：可重用的小元件，如按鈕、卡片。

**資料夾結構範例：**

```plaintext
src/
├── components/          # 按鈕、表格等小元件
├── layouts/             # 佈局元件
│   ├── MainLayout.vue   # 前台佈局 (Header + Footer)
│   ├── AdminLayout.vue  # 後台佈局 (Sidebar + Header)
│   └── LoginLayout.vue  # 登入佈局 (全螢幕置中)
└── views/               # 頁面元件
    ├── HomeView.vue
    ├── LoginView.vue
    └── admin/
        └── Dashboard.vue
```

**程式碼範例 (router/index.js)：**

```javascript
import MainLayout from "@/layouts/MainLayout.vue";
import AdminLayout from "@/layouts/AdminLayout.vue";
import LoginLayout from "@/layouts/LoginLayout.vue";

const routes = [
  // 1. 前台系列 (使用 MainLayout)
  {
    path: "/",
    component: MainLayout,
    children: [
      { path: "", component: () => import("@/views/HomeView.vue") },
      { path: "about", component: () => import("@/views/AboutView.vue") },
    ],
  },

  // 2. 後台系列 (使用 AdminLayout)
  {
    path: "/admin",
    component: AdminLayout,
    meta: { requiresAuth: true }, // 後台通常需要權限
    children: [
      { path: "", component: () => import("@/views/admin/Dashboard.vue") },
      {
        path: "settings",
        component: () => import("@/views/admin/Settings.vue"),
      },
    ],
  },

  // 3. 登入頁 (使用 LoginLayout)
  {
    path: "/login",
    component: LoginLayout,
    children: [{ path: "", component: () => import("@/views/LoginView.vue") }],
  },
];
```

透過這種方式，我們可以輕鬆管理不同區域的頁面佈局，切換路由時，只有中間的 View 會變動，Layout 保持不變。

---

## <a id="CH4-4"></a>[4-4 路由守衛 (Navigation Guards)](#目錄)

### <a id="CH4-4-1"></a>[1. 路由守衛的意義](#目錄)

路由守衛就像是大樓的保全，在進入某個頁面之前，先檢查你有沒有權限（例如是否已登入）。如果不符合資格，就擋下來或請你去登入頁。

### <a id="CH4-4-2"></a>[2. 全域前置守衛](#目錄)

最常用的是 `router.beforeEach`，它會在**每次**路由切換前執行。我們可以在這裡統一處理登入檢查。

```javascript
/* router/index.js */
const router = createRouter({ ... })

// 全域守衛
router.beforeEach((to, from, next) => {
  // to: 即將要去的路由物件
  // from: 正要離開的路由物件
  // next: (可選) 用來決定放行或重導向的函式

  // 範例：檢查是否登入
  const isAuthenticated = checkLoginStatus() // 假設這是一個檢查登入狀態的函式

  // 檢查目標路由是否需要權限 (透過 meta 欄位)
  if (to.meta.requiresAuth && !isAuthenticated) {
    // 如果需要權限且未登入，強制導向登入頁
    next({ name: 'login' })
  } else {
    // 否則放行
    next()
  }
})
```

### <a id="CH4-4-3"></a>[3. 路由 Meta 欄位](#目錄)

我們可以在定義路由時，透過 `meta` 屬性加上自訂的標籤，用來判斷該頁面是否需要特殊處理（如不需要驗證、或是需要特定角色）。

```javascript
const routes = [
  {
    path: '/admin',
    component: AdminLayout,
    // 自訂 meta 資訊
    meta: {
      requiresAuth: true,  // 需要登入
      role: 'admin'        // 需要管理員權限
    },
    children: [ ... ]
  },
  {
    path: '/login',
    component: LoginLayout,
    // 登入頁不需要權限
    meta: { requiresAuth: false },
    children: [ ... ]
  }
]
```

---

[上一章：章節 3 ｜ Vite + 元件化 + Plugins](./Chapter3_ViteComponents.md) | [下一章：章節 5 ｜ API + Pinia](./Chapter5_ApiPinia.md)
