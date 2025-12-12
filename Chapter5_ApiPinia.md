# 章節 5 ｜ API + Pinia

## <a id="目錄"></a>目錄

- [5-1 API 基礎 (axios)](#CH5-1)
  - [1. 為什麼選擇 Axios？](#CH5-1-1)
  - [2. 安裝與基本使用](#CH5-1-2)
  - [3. 完整 Axios 範例](#CH5-1-3)
  - [4. 攔截器 (Interceptors) - 重要！](#CH5-1-4)
- [5-2 邏輯抽離 (Composables)](#CH5-2)
  - [1. 什麼是 Composables？](#CH5-2-1)
  - [2. 實戰：封裝 API 請求邏輯](#CH5-2-2)
- [5-3 Pinia 基礎使用](#CH5-3)
  - [1. 為什麼需要狀態管理？](#CH5-3-1)
  - [2. Pinia 簡介](#CH5-3-2)
  - [3. 安裝與 Setup](#CH5-3-3)
  - [4. 定義 Store (Setup Store)](#CH5-3-4)
  - [5. 正確解構 Store (storeToRefs)](#CH5-3-5)
- [5-4 Pinia 與 API 整合](#CH5-4)
  - [1. 資料邏輯與 UI 分離](#CH5-4-1)
  - [2. Pinia 資料持久化 (Persistence)](#CH5-4-2)
- [5-5 Pinia 進階技巧](#CH5-5)
  - [1. 關於狀態重置 ($reset)](#CH5-5-1)
  - [2. 路由守衛與 Store 的整合](#CH5-5-2)

### 序

如果說 **Vue Router** 是應用程式的「地圖」，那麼 **資料 (Data)** 就是應用程式的「靈魂」。
一個沒有資料的網站，就像是一個漂亮的空殼。在真實開發中，我們幾乎一定會需要跟後端伺服器溝通，取得商品列表、使用者資訊等動態資料。

本章節我們將介紹兩位重量級主角：

1.  **Axios**：最強大的 HTTP 請求套件，負責像郵差一樣幫我們送信（發送請求）與收信（接收資料）。
2.  **Pinia**：Vue 官方推薦的狀態管理庫，就像是整個應用程式的「中央大倉庫」，負責集中管理這些得來不易的資料。

準備好了嗎？讓我們開始賦予應用程式靈魂吧！

---

## <a id="CH5-1"></a>[5-1 API 基礎 (axios)](#目錄)

### <a id="CH5-1-1"></a>[1. 為什麼選擇 Axios？](#目錄)

雖然瀏覽器原生就提供了 `fetch` API，但大多數開發者（包括企業級專案）仍然首選 **Axios**。為什麼？  
因為 `fetch` 雖然輕量，但有些顯而易見的缺點（例如需要手動轉 JSON、處理 Http Error 較麻煩）。
Axios 則貼心地幫我們處理好了這些瑣事，並提供了強大的 **攔截器 (Interceptors)** 功能。

**Axios 主要優勢：**

1.  **自動轉換 JSON**：不用像 `fetch` 那樣還要多寫一行 `response.json()`。
2.  **更好的錯誤處理**：只要 HTTP 狀態碼不是 2xx，Axios 就會直接拋出錯誤 (catch)，而 `fetch` 只會在網路斷線時才報錯，其他錯誤得自行判斷。
3.  **攔截器 (Interceptors)**：這是最強大的功能！我們可以設定在 **請求發送前** 自動幫每個請求加上 Token，或是在 **回應回來後** 統一處理錯誤（例如 Token 過期自動登出）。

### <a id="CH5-1-2"></a>[2. 安裝與基本使用](#目錄)

首先，安裝 Axios：

```bash
npm install axios
```

**基本 GET 請求範例：**

```javascript
import axios from "axios";

const fetchData = async () => {
  try {
    // axios 會自動把 response.data 轉成 JSON 物件，不用像 fetch 還要多 await response.json()
    const response = await axios.get(
      "https://jsonplaceholder.typicode.com/users/1"
    );
    console.log(response.data);
  } catch (error) {
    console.error("發生錯誤:", error);
  }
};
```

### <a id="CH5-1-3"></a>[3. 完整 Axios 範例 (Cheat Sheet)](#目錄)

開發中常會遇到各種請求需求，這裡整理了幾種常用的情境。

```javascript
import axios from "axios";

// 1. 基礎 GET 請求
// GET: https://api.example.com/users
axios.get("/users");

// 2. GET 請求附帶 Query 參數
// GET: https://api.example.com/search?q=vue&page=1
axios.get("/search", {
  params: {
    q: "vue",
    page: 1,
  },
});

// 或者直接寫在 URL 內
axios.get("/search?q=vue&page=1");

// 3. POST 請求附帶 JSON 資料 (最常用)
// Content-Type: application/json
axios.post("/users", {
  name: "Alice",
  email: "alice@example.com",
});

// 4. POST 請求附帶 FormData (上傳檔案/圖片用)
// Content-Type: multipart/form-data
const formData = new FormData();
formData.append("username", "Alice");
formData.append("avatar", fileInput.files[0]); // 假設有檔案

axios.post("/upload", formData);

// 5. Header 內附帶 Token (通常會用攔截器統一處理，但也可以手動加)
axios.get("/profile", {
  headers: {
    Authorization: `Bearer YOUR_TOKEN_HERE`,
  },
});

// 6. 下載檔案 (Blob)
axios
  .get("/download", {
    responseType: "blob", // 告訴 axios 這是二進制資料
  })
  .then((response) => {
    const url = window.URL.createObjectURL(new Blob([response.data]));

    // 下載檔案
    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", "file.pdf");
    document.body.appendChild(link);
    link.click();
  });
```

### <a id="CH5-1-4"></a>[4. 攔截器 (Interceptors) - 重要！](#目錄)

攔截器是 Axios 最強大的功能。你可以把攔截器想像成 **「大樓的警衛」** 或 **「機場安檢」**。

- **請求攔截器 (Request Interceptors)**：在請求**送出前**攔截。
  - _應用場景_  
    像是機場安檢確認你有沒有護照。我們通常在這裡統一幫所有請求加上 **Token (身分證)**，這樣就不用在每個 API 呼叫都手動寫一次。
- **回應攔截器 (Response Interceptors)**：在資料**回來後**攔截。
  - _應用場景_  
    像是在包裹送到前先檢查有沒有破損。我們通常在這裡統一處理錯誤（例如 Token 過期就自動登出），或者只回傳 `data` 部分以簡化資料結構。

**實戰配置 (`src/api/axios.js`)：**

```javascript
import axios from "axios";

// 1. 建立 axios 實例 (Instance)
// 就像是建立一個專屬的通訊頻道，設定好基礎網址 (baseURL) 和超時時間
const api = axios.create({
  baseURL: "https://api.example.com", // 每次請求發送的目標 Server
  timeout: 5000, // 5秒沒回應就報錯
});

// 2. 請求攔截器 (Request Interceptor)
api.interceptors.request.use(
  (config) => {
    // 從 LocalStorage 取得 Token
    const token = localStorage.getItem("my-token");

    // 如果有 token，就加到 Header 裡，告訴後端我是誰
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// 3. 回應攔截器 (Response Interceptor)
api.interceptors.response.use(
  (response) => {
    // 成功收到資料，直接回傳
    return response;
  },
  (error) => {
    // 統一處理錯誤代碼
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 401 代表未授權 (Token 過期或無效)
          alert("登入已過期，請重新登入");
          // 強制導回登入頁邏輯...
          break;
        case 404:
          alert("找不到資源");
          break;
        case 500:
          alert("伺服器出錯了");
          break;
        default:
          alert("發生未知錯誤");
      }
    }
    return Promise.reject(error); // 將錯誤繼續往外拋
  }
);

export default api;
```

---

**如何使用配置好的 API：**

配置完成後，以後在元件中就不用再 `import axios`，而是引入我們自己封裝好的 `api`。

`src/views/HomeView.vue`：

```html
<script setup>
  import { onMounted } from "vue";
  import api from "@/api/axios"; // 引入我們封裝好的實例

  const getData = async () => {
    try {
      // 這裡的請求會自動：
      // 1. 加上 baseURL (https://api.example.com/products)
      // 2. 加上 Token Header (如果有)
      // 3. 通過回應攔截器的錯誤檢查
      const res = await api.get("/products");
      console.log(res.data);
    } catch (err) {
      // 錯誤已經在攔截器被統一處理過了 (例如 alert)，這裡通常只需要處理 UI 相關邏輯
      console.error("個別處理", err);
    }
  };

  onMounted(() => {
    getData();
  });
</script>
```

---

## <a id="CH5-2"></a>[5-2 邏輯抽離 (Composables)](#目錄)

### <a id="CH5-2-1"></a>[1. 什麼是 Composables？](#目錄)

在 Vue 2 時代，我們常用 Mixins 來共用程式碼，但它有很多缺點（如來源不明確、命名衝突）。
Vue 3 引入了 **Composition API** 之後，我們可以利用它的靈活性，將相關的邏輯（狀態、方法、監聽器）封裝成一個獨立的函式，這種函式我們稱為 **Composables**（組合式函式）。

**特點：**

1.  **慣例命名**：通常以 `use` 開頭，例如 `useMouse`, `useFetch`。
2.  **邏輯封裝**：把實現特定功能的 `ref`, `method`, `watch`, `onMounted` 全部打包在一起。
3.  **高度重用**：可以在不同元件中引入同一個 Composable，享受相同的邏輯。

---

### <a id="CH5-2-2"></a>[2. Composables 範例集](#目錄)

讓我們透過幾個範例來體驗 Composables 的威力。

#### 範例一：開關切換器 (useToggle)

我們常需要控制 Modal 的開關、密碼顯示的切換、或是下拉選單的展開。這些邏輯全都是一樣的：「`true` 變 `false`，`false` 變 `true`」。

**`src/composables/useToggle.js`**

```javascript
import { ref } from "vue";

export function useToggle(initialValue = false) {
  const value = ref(initialValue);

  const toggle = () => {
    value.value = !value.value;
  };

  // 提供明確的開啟與關閉方法，讓語義更清晰
  const show = () => (value.value = true);
  const hide = () => (value.value = false);

  return { value, toggle, show, hide };
}
```

**元件中使用：**

```javascript
<script setup>
import { useToggle } from '@/composables/useToggle'

// 兩個獨立的狀態，互不干擾！
const { value: isModalOpen, show: showModal } = useToggle()
const { value: isPasswordVisible, toggle: togglePassword } = useToggle()
</script>
```

**這樣封裝有什麼好處？**  
我們將**切換邏輯**與**狀態**封裝在函式內，確保每次呼叫 `useToggle()` 時都會產生**獨立的狀態實例**。這意味著你可以在同一個元件中多次使用它，分別控制不同的功能，彼此互不干擾。

#### 範例二：滑鼠追蹤器 (useMouse)

這個範例展示了如何在 Composable 中使用生命週期 Hook (`onMounted`, `onUnmounted`) 來管理與銷毀事件監聽器。

**`src/composables/useMouse.js`**

```javascript
import { ref, onMounted, onUnmounted } from "vue";

export function useMouse() {
  const x = ref(0);
  const y = ref(0);

  const update = (event) => {
    x.value = event.pageX;
    y.value = event.pageY;
  };

  // 當元件掛載時，開始監聽
  onMounted(() => window.addEventListener("mousemove", update));

  // 當元件銷毀時，移除監聽 (這點很重要，避免記憶體洩漏！)
  // 這部分邏輯被封裝在內部，使用元件的人完全不需要操心
  onUnmounted(() => window.removeEventListener("mousemove", update));

  return { x, y };
}
```

**元件中使用：**

```javascript
<script setup>
import { useMouse } from '@/composables/useMouse'

const { x, y } = useMouse()
</script>

<template>
  <h3>滑鼠座標：{{ x }}, {{ y }}</h3>
</template>
```

**這樣封裝有什麼好處？**  
**生命週期管理自動化**：開發者只需呼叫 `useMouse()` 就能獲得座標，完全不用擔心什麼時候要 addEventListener 或 removeEventListener，也不會因為忘記移除而造成記憶體洩漏。

#### 範例三：分頁邏輯 (usePagination) - 處理複雜計算

這是一個非常實用的範例。處理分頁通常需要計算 `totalPage`、切換頁碼、檢查是否第一頁/最後一頁...等繁瑣邏輯。

**`src/composables/usePagination.js`**

```javascript
import { ref, computed } from "vue";

export function usePagination(initPage = 1, initPageSize = 10) {
  const currentPage = ref(initPage);
  const pageSize = ref(initPageSize);
  const total = ref(0); // 總筆數 (通常由 API 回傳)

  // 計算總頁數
  const totalPage = computed(() => Math.ceil(total.value / pageSize.value));

  // 切換頁碼的方法
  const payload = computed(() => ({
    page: currentPage.value,
    limit: pageSize.value,
  }));

  const prev = () => {
    if (currentPage.value > 1) currentPage.value--;
  };

  const next = () => {
    if (currentPage.value < totalPage.value) currentPage.value++;
  };

  const setTotal = (count) => {
    total.value = count;
  };

  return {
    currentPage,
    pageSize,
    total,
    totalPage,
    prev,
    next,
    setTotal,
    payload,
  };
}
```

**元件中使用：**

```javascript
<script setup>
import { usePagination } from '@/composables/usePagination'

const { currentPage, totalPage, prev, next, setTotal } = usePagination()

// 模擬 API 回傳總數
setTotal(50)
</script>

<template>
  <div>
    <button @click="prev" :disabled="currentPage === 1">上一頁</button>
    <span> {{ currentPage }} / {{ totalPage }} </span>
    <button @click="next" :disabled="currentPage === totalPage">下一頁</button>
  </div>
</template>
```

**這樣封裝有什麼好處？**  
**邏輯複用**：不管是在「商品列表」、「訂單記錄」還是「用戶管理」，只要有分頁需求，直接引入 `usePagination` 即可，不用每次都重寫 `prev`, `next` 跟計算總頁數的公式！

#### 範例四：API 請求 (useFetch)

我們把 `data`, `error`, `isLoading` 等常見的三個 API 狀態封裝起來。

**`src/composables/useFetch.js`**

```javascript
import { ref } from "vue";
import axios from "axios";

// 接收一個 url 當參數
export function useFetch(url) {
  const data = ref(null);
  const error = ref(null);
  const isLoading = ref(false);

  const execute = async () => {
    isLoading.value = true;
    data.value = null;
    error.value = null;

    try {
      const res = await axios.get(url);
      data.value = res.data;
    } catch (err) {
      error.value = err;
    } finally {
      isLoading.value = false;
    }
  };

  // 自動執行 (選擇性)
  execute();

  return { data, error, isLoading, execute };
}
```

**元件中使用：**

```javascript
<script setup>
import { useFetch } from '@/composables/useFetch'

// 一行程式碼就搞定 API 呼叫與狀態管理！
const { data, error, isLoading } = useFetch('https://jsonplaceholder.typicode.com/users')
</script>

<template>
  <div v-if="isLoading">載入中...</div>
  <div v-else-if="error">出錯了: {{ error.message }}</div>
  <pre v-else>{{ data }}</pre>
</template>
```

**這樣封裝有什麼好處？**  
**統一的狀態管理**  
所有的 API 請求都具備一致的 Loading 與 Error 處理機制。你不需要在每個元件裡手動寫 `isLoading = true` 然後 `finally { isLoading = false }`，大幅減少了重複程式碼，也讓程式碼更乾淨易讀。

---

## <a id="CH5-3"></a>[5-3 Pinia 基礎使用](#目錄)

### <a id="CH5-3-1"></a>[1. 為什麼需要狀態管理？](#目錄)

在元件化開發中，最讓人頭痛的問題就是「**資料傳遞**」。

假設你有一個 **「使用者資訊 (User)」**，它在最頂層的 `App.vue` 取得，但最深層的 `UserCard.vue` 也需要用到它。
如果沒有狀態管理，你就必須一層一層地透過 properties (props) 傳遞下去：
`App -> Envirnoment -> MainLayout -> Header -> UserCard`

這就是所謂的 **Props Drilling (屬性鑽孔)** 現象。不僅程式碼冗長，而且中間任何一層元件改名或重構，整條傳遞鏈都要跟著改，維護起來非常痛苦。

**Pinia 的出現就是為了解決這個問題**。它就像是一個懸浮在所有元件上空的「雲端資料庫」，任何元件都可以直接伸手去拿資料，完全不需要透過父子元件層層傳遞。

> **💡 備註：為什麼不直接用 Provide/Inject？**
>
> `Provide/Inject` 適合傳遞 **UI 上下文**（如 Theme、API Client），但用於管理 **業務狀態** 會有以下痛點：
>
> 1.  **維護性差**：Inject 是「隱性依賴」，難以一眼看出元件使用了哪些資料，也不利於重構。
> 2.  **除錯困難**：缺乏 DevTools 支援，資料被改動時難以追蹤是誰改的。
> 3.  **測試麻煩**：必須掛載元件樹才能測試，不像 Pinia Store 可以獨立單元測試。
>
> **Pinia** 專注於全域狀態管理，提供 **DevTools**、**明確型別** 與 **獨立測試** 能力。
> **簡單來說：UI 依賴用 Provide/Inject，業務資料用 Pinia。**

### <a id="CH5-3-2"></a>[2. Pinia 簡介](#目錄)

**Pinia** 是 Vue 官方正式背書的狀態管理庫（Vuex 的繼承者）。它解決了 Vuex 過去為人詬病的複雜性（不再有 Mutation、Module 結構更扁平），並且對 TypeScript 的支援度極佳。

你可以把 Store 想像成一個**「全域的元件」**：

- **State** 就像是 `ref` / `reactive` (資料)
- **Getters** 就像是 `computed` (計算屬性)
- **Actions** 就像是 `function` (方法)

> **💡 備註：Pinia vs Composables**
>
> 兩者都能做狀態管理，但適用場景不同：
>
> | 特性         | Composables (組合式函式)                          | Pinia (Store)                       |
> | :----------- | :------------------------------------------------ | :---------------------------------- |
> | **狀態範圍** | 預設為區域 (Local)，需自行實作 Singleton 才能共用 | 天生全域 (Global) 單例模式          |
> | **除錯工具** | 無 (但在 Vue DevTools 可見 Ref)                   | 專屬 DevTools (時光機、Action 追蹤) |
> | **SSR 支援** | 需手動處理 Hydration                              | 內建支援                            |
> | **結構規範** | 自由發揮 (容易寫成義大利麵)                       | 強制規範 (State/Getter/Action)      |
> | **適用場景** | **邏輯複用** (如 useMouse, useFetch)              | **全域資料** (如 User, Cart, Theme) |
>
> **結論：**  
> 如果是為了「抽離邏輯」給多個元件用，選 Composables；如果是為了「跨元件共用資料」，選 Pinia。

### <a id="CH5-3-3"></a>[3. 安裝與 Setup](#目錄)

```bash
npm install pinia
```

**`src/main.js` 啟用 Pinia：**

```javascript
import { createApp } from "vue";
import { createPinia } from "pinia"; // 引入 createPinia
import App from "./App.vue";

const app = createApp(App);

app.use(createPinia()); // 使用 Pinia
app.mount("#app");
```

### <a id="CH5-3-4"></a>[4. 定義 Store (Setup Store)](#目錄)

Pinia 支援兩種寫法：Option Store (像 Vue 2) 和 Setup Store (像 Vue 3 Composition API)。
**我們強烈推薦使用 Setup Store**，因為它寫起來就跟平常寫元件一模一樣！

**`src/stores/counter.js`：**

```javascript
import { defineStore } from "pinia";
import { ref, computed } from "vue";

// 第一個參數 'counter' 是 Store 的唯一 ID (必填)
export const useCounterStore = defineStore("counter", () => {
  // 1. State (資料)
  const count = ref(0);

  // 2. Getters (計算屬性)
  const doubleCount = computed(() => count.value * 2);

  // 3. Actions (方法)
  function increment() {
    count.value++;
  }

  // 記得要把想公開的東西 return 出去
  return { count, doubleCount, increment };
});
```

> **💡 補充：Option Store 寫法 (Vue 2 風格)**
>
> 如果你比較習慣 Vue 2 的 `data`, `computed`, `methods` 結構，Pinia 也支援 Option API 的寫法：
>
> ```javascript
> export const useCounterStore = defineStore("counter", {
>   // 1. State: 必須是 Arrow Function (為了避免 SSR 污染)
>   state: () => ({
>     count: 0,
>   }),
>   // 2. Getters
>   getters: {
>     doubleCount: (state) => state.count * 2,
>   },
>   // 3. Actions
>   actions: {
>     increment() {
>       // 在這裡可以用 this 存取 state
>       this.count++;
>     },
>   },
> });
> ```

### <a id="CH5-3-5"></a>[5. 正確解構 Store (storeToRefs)](#目錄)

這是新手最常踩到的坑！請務必注意！

當我們在元件中使用 Store 時，如果直接用 ES6 解構語法取出 State，**它會失去響應性 (Reactivity)**。這意味著當 Store 裡的資料變了，你的畫面卻不會跟著更新。

**解決方法：使用 `storeToRefs`。**

```javascript
<script setup>
import { useCounterStore } from '@/stores/counter'
import { storeToRefs } from 'pinia'

const store = useCounterStore()

// ❌ 危險！這樣解構出來的 count 只是個普通的數字，不會連動更新
// const { count } = store

// ✅ 正確！使用 storeToRefs 包住，解構出來的會是 ref，保有響應性
// 適用於：State 和 Getters
const { count, doubleCount } = storeToRefs(store)

// 💡 備註：Actions (方法) 可以直接解構，不需要 storeToRefs
// 原因：Actions 本身是函式，不具備響應性 (Reactivity)，跟普通的 JavaScript Function 一樣，
// 所以直接解構使用即可，不會有失去連結的問題。
const { increment } = store
</script>

<template>
  <h1>{{ count }}</h1>
  <button @click="increment">+1</button>
</template>
```

### 💡 觀念補充

1. **名詞定義與功能**：
   在 Setup Store 中，它們直接對應到 Vue 的 Composition API，但各司其職：

   - **State (`ref` / `reactive`)**：  
     Store 的核心，負責 **儲存** 應用程式的狀態資料（例如：使用者資訊、購物車列表）。
   - **Getters (`computed`)**：  
     負責從 State **衍生** 出新的狀態（例如：計算購物車總金額、過濾後的商品列表）。具有快取特性，只有依賴的 State 改變時才會重新計算。
   - **Actions (`function`)**：  
     負責定義 **業務邏輯**（例如：登入、加入購物車、發送 API 請求）。它是唯一可以包含非同步操作 (Async) 的地方，通常用來修改 State。

2. **Actions 的價值**：  
   雖然我們可以透過 `storeToRefs` 取得響應式資料並直接在元件中修改 (例如 `count.value++`)，但 **Actions** 仍然有其不可取代的意義：它負責 **合併邏輯 (Encapsulate Logic)**。  
   將業務邏輯（如：驗證、API 請求、錯誤處理）封裝在 Actions 中，可以讓元件更專注於 UI 呈現，也讓邏輯更容易被重複使用與維護。

---

## <a id="CH5-4"></a>[5-4 Pinia 與 API 整合](#目錄)

### <a id="CH5-4-1"></a>[1. 資料邏輯與 UI 分離](#目錄)

在真實專案中，我們建議將 **API 請求** 與 **資料邏輯** 封裝在 Pinia Store 中，而不是直接寫在元件的 `script setup` 裡。
這樣做有兩大好處：

1.  **UI 乾淨單純**：元件只負責「呼叫 Action」與「顯示 State」，不處理複雜的非同步流程與錯誤捕捉。
2.  **邏輯可重用**：同一個 API 請求（例如「取得使用者資料」或「登入」）可能會在多個頁面或元件用到，封裝在 Store 裡就不用到處複製貼上。

**範例：使用者登入 Store (`src/stores/user.js`)**

我們將 `token` 管理、API 請求、錯誤處理都藏在 Store 裡面。

```javascript
import { defineStore } from "pinia";
import { ref, computed } from "vue";
import api from "@/api/axios"; // 假設已封裝好的 axios

export const useUserStore = defineStore("user", () => {
  // State
  // 這裡先簡單示範手動讀取 LocalStorage，下一節會教更棒的插件寫法
  const token = ref(localStorage.getItem("token") || "");
  const userInfo = ref(null);

  // Actions
  const login = async (username, password) => {
    try {
      // 1. 發送 API
      const res = await api.post("/auth/login", { username, password });

      // 2. 更新 State
      token.value = res.data.token;
      userInfo.value = res.data.user;

      // 3. (手動持久化) 更新 LocalStorage
      localStorage.setItem("token", token.value);

      return true;
    } catch (error) {
      console.error("登入失敗", error);
      return false;
    }
  };

  const logout = () => {
    token.value = "";
    userInfo.value = null;
    localStorage.removeItem("token");
  };

  return { token, userInfo, login, logout };
});
```

**元件中使用：**

```html
<script setup>
  import { ref } from "vue";
  import { useRouter } from "vue-router";
  import { useUserStore } from "@/stores/user";

  const username = ref("");
  const password = ref("");
  const router = useRouter();
  const userStore = useUserStore();

  const handleLogin = async () => {
    // 呼叫 Store 的 Action，不僅處理 API，還同時更新了全域狀態
    const success = await userStore.login(username.value, password.value);
    if (success) {
      router.push("/");
    } else {
      alert("登入失敗");
    }
  };
</script>
```

**範例二：商品列表 Store (`src/stores/product.js`)**

```javascript
import { defineStore } from "pinia";
import { ref } from "vue";
import api from "@/api/axios";

export const useProductStore = defineStore("product", () => {
  const products = ref([]);
  const isLoading = ref(false);

  const fetchProducts = async () => {
    // 簡單快取：如果有資料就不重抓
    if (products.value.length > 0) return;

    isLoading.value = true;
    try {
      const res = await api.get("/products");
      products.value = res.data;
    } catch (error) {
      console.error(error);
    } finally {
      isLoading.value = false;
    }
  };

  return { products, isLoading, fetchProducts };
});
```

**元件中使用：**

```html
<script setup>
import { onMounted } from 'vue'
import { useProductStore } from '@/stores/product'
import { storeToRefs } from 'pinia'

const store = useProductStore()
// State 必須用 storeToRefs 解構以維持響應性
const { products, isLoading } = storeToRefs(store)
// Action 直接解構即可
const { fetchProducts } = store

onMounted(() => {
  fetchProducts()
})
</script>

<template>
  <div v-if="isLoading">載入中...</div>
  <ul v-else>
    <li v-for="p in products" :key="p.id">{{ p.title }}</li>
  </ul>
</template>
```

> **💡 備註：是否要把所有的資料處理都獨立成 Pinia store？**
>
> **不需要，也不建議。**   
> 比較好的做法是：Pinia 負責「跨頁共享、需要被多處使用、要被持久化」的狀態與流程；其餘就留在元件或抽成 Composable。
>
> **什麼時候該放進 Pinia Store？** (前端的小型 Domain / Use-Case 層)
> *   **跨多個頁面/元件會用到的狀態** (如：使用者資訊、購物車、全站設定)
> *   **需要一致性與可追蹤的狀態變更流程** (如：複雜的非同步流程)
> *   **需要快取與去重複請求** (如：商品資料快取)
> *   **需要持久化** (如：Token、偏好設定)
>
> **什麼時候不要放進 Store？**
> *   **只在單一頁/單一元件用到的狀態** (如：表單輸入、Modal 開關) → 用 `ref` 即可
> *   **純展示用的計算** (如：日期格式化) → 用 `computed` 或 `utils`
> *   **跟路由強綁的「頁面生命週期」資料** (進頁打一次、離頁就丟) → 給 Page Component + Composable
>
> **📌 建議的分層結構：**
> *   **Component**：UI、使用者互動、組裝資料（盡量薄）
> *   **Composable**：可重用的「頁面邏輯」或「小流程」
> *   **Store**：跨頁共享 State + 業務流程 (Actions) + 快取/同步
> *   **API/Service**：純 API 呼叫、資料轉換


### <a id="CH5-4-2"></a>[2. Pinia 資料持久化 (Persistence)](#目錄)

**為什麼需要持久化？**

Pinia 的 Store 預設是儲存在 **記憶體 (RAM)** 中的。
這意味著當使用者 **重新整理網頁 (F5)** 或 **關閉分頁** 時，所有的 State (包含登入後的 Token) 都會被清空重置，導致使用者被迫登出。
為了避免這種體驗，我們必須將關鍵資料同步存入瀏覽器的 `localStorage` 或 `sessionStorage`。

**方法一：手動實作 (如上例)**
最原始的方法就是在 `state` 初始化時讀取 LocalStorage，並在每次 `action` 修改資料後，手動呼叫 `localStorage.setItem`。
這種方法雖然直觀，但缺點是**程式碼重複性高**，每個 Store 都要寫一遍讀寫邏輯，容易忘記或寫錯。

**方法二：使用插件 (推薦)**
Vue 社群非常活躍，已經有成熟的插件 `pinia-plugin-persistedstate` 可以幫我們自動完成「讀取」與「寫入」的工作。

**1. 安裝插件**

```bash
npm install pinia-plugin-persistedstate
```

**2. 在 `main.js` 註冊插件**

```javascript
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate); // 啟用插件
```

**3. 在 Store 中開啟功能**

使用 Setup Store 時，將第三個參數 (Options) 加入 `persist: true` 即可。

```javascript
export const useUserStore = defineStore(
  "user",
  () => {
    const token = ref("");
    const userInfo = ref(null);

    // Actions...

    return { token, userInfo };
  },
  {
    persist: true, // 🔥 只要加這行，整個 Store 的 State 都會自動存到 LocalStorage！
  }
);
```

### 💡 備註：指定持久化目標

預設情況下，插件會將資料存入 `localStorage` (永久有效)。如果你希望資料在 **關閉分頁後自動清除**，可以改用 `sessionStorage`：

```javascript
{
  persist: {
    storage: sessionStorage, // 改為儲存在當次會話(session)中，關閉瀏覽器後就會銷毀
    paths: ['token'], // (選填) 指定只持久化 token，忽略 userInfo
  }
}
```

使用了插件後，你完全不需要再手動寫 `localStorage.setItem` 或 `getItem`，插件會在背後自動幫你處理好一切，非常方便！

### ⚠️ 注意：什麼該存？什麼不該存？

**並不是所有資料都需要持久化！** 濫用持久化可能會導致安全問題或效能下降。

| 類型           | 適合持久化 (Persist)       | 原因                           |
| :------------- | :------------------------- | :----------------------------- |
| **身分驗證**   | Token, Refresh Token       | 確保重整後不用重新登入         |
| **使用者偏好** | 語言設定, 主題 (Dark Mode) | 提升使用者體驗，記住他們的習慣 |
| **購物車**     | 未結帳商品 (匿名使用者)    | 避免誤關分頁導致挑選商品消失   |
| **多步驟表單** | 填寫到一半的草稿           | 防止誤觸上一頁導致資料全毀     |

| 類型         | **❌ 不建議持久化**      | 原因                                                          |
| :----------- | :----------------------- | :------------------------------------------------------------ |
| **敏感資料** | 密碼, 信用卡號           | **安全風險**！LocalStorage 容易被 XSS 攻擊竊取                |
| **大量列表** | 商品列表, 訂單紀錄       | **效能問題**！且資料容易過時 (Stale)，應每次重新透過 API 抓取 |
| **UI 暫態**  | Modal 開關, Loading 狀態 | 重整後應該要恢復預設值 (關閉/原有狀態)，不需要記住            |

---

## <a id="CH5-5"></a>[5-5 Pinia 進階技巧](#目錄)

### <a id="CH5-5-1"></a>[1. 關於狀態重置 (`$reset`)](#目錄)

在使用 Vue 2 風格的 Option Store 時，Pinia 內建了一個 `$reset()` 方法，可以一鍵把 State 還原成初始值。
但在 **Setup Store (Composition API)** 中，為了保持彈性，**Pinia 並沒有提供內建的 `$reset`**。

如果你需要在「登出」時清空所有資料，建議採用以下簡單的實作方式：

```javascript
export const useUserStore = defineStore("user", () => {
  // 1. 定義一個回傳初始值的函式
  const initState = () => {
    return {
      token: "",
      userInfo: null,
    };
  };

  // 也可使用簡寫
  // const initState = () => ({
  //   token: "",
  //   userInfo: null,
  // });

  // 2. 初始化 State
  const token = ref(initState().token);
  const userInfo = ref(initState().userInfo);

  // 3. 實作 reset Action
  const reset = () => {
    const init = initState();
    token.value = init.token;
    userInfo.value = init.userInfo;
  };

  return { token, userInfo, reset };
});
```

### <a id="CH5-5-2"></a>[2. 路由守衛與 Store 的整合](#目錄)

在真實應用中，我們最常需要在「切換頁面之前」檢查使用者是否登入。
如果未登入，就強制踢回登入頁。這就是 **Navigation Guards (路由守衛)** 的職責。

要在 `src/router/index.js` (或是其他非 Vue 元件的 JS 檔) 中使用 Pinia Store，方法其實一模一樣：

**`src/router/index.js`：**

```javascript
import { createRouter, createWebHistory } from "vue-router";
import { useUserStore } from "@/stores/user"; // 引入 Store

const router = createRouter({
  // ... 路由設定
});

// 全域前置守衛
router.beforeEach((to, from, next) => {
  // ⚠️ 注意：必須在 beforeEach 函式內部呼叫 useUserStore()
  // 因為 Pinia 必須在 app.use(pinia) 之後才能被使用
  const userStore = useUserStore();

  // 檢查該頁面是否需要權限 (假設路由 meta 有設定 requiresAuth)
  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    alert("請先登入！");
    next("/login"); // 導向登入頁
  } else {
    next(); // 放行
  }
});

export default router;
```

> **重點**：不要在檔案最外層 (Top-level) 呼叫 `useUserStore()`，一定要在 `beforeEach` 等函式內部呼叫，否則會因為 Pinia 還沒實例化而報錯！

---

恭喜你完成了 Vue 開發中最核心的拼圖！

在本章節中，我們不僅學會了如何透過 `Axios` 與 `Interceptors` 優雅地處理 API 請求，更掌握了 `Composables` 的邏輯封裝技巧，以及現代化的 `Pinia` 狀態管理。現在，你的應用程式已經擁有了處理真實數據的「大腦」與「靈魂」。

接下來，只有功能強大還不夠，我們需要讓使用者一眼就愛上你的 App。
下一章，我們將引入 **Vuetify** 這個強大的 UI 框架，讓你的應用程式瞬間擁有專業級的視覺與互動體驗！

---

[上一章：章節 4 ｜ Vue Router](./Chapter4_VueRouter.md) | [下一章：章節 6 ｜ Vuetify（簡易版本）](./Chapter6_Vuetify.md)
