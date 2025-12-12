# 章節 2 ｜ Vue 基礎（CDN 版本）

## <a id="目錄"></a>目錄

- [2-1 Vue 框架簡介](#CH2-1)
  - [1. Vue 的核心優勢](#CH2-1-1)
  - [2. Vue 3 的現代化特性](#CH2-1-2)
  - [3. 完整的生態系](#CH2-1-3)
  - [4. 小結](#CH2-1-4)
- [開始之前...先安裝一些 VS Code 插件](#CH2-EXT)
- [2-2 Vue App 與插值語法](#CH2-2)
  - [1. 建立第一個 Vue 應用程式](#CH2-2-1)
  - [2. 插值語法 (Mustache Syntax) {{ }}](#CH2-2-2)
  - [3. Vue API 風格比較 (Options vs Composition)](#CH2-2-3)
- [2-3 ref 與 reactive](#CH2-3)
  - [1. ref](#CH2-3-1)
  - [2. reactive](#CH2-3-2)
  - [3. 比較 ref 與 reactive](#CH2-3-3)
- [2-4 v-bind 與 v-on 指令](#CH2-4)
  - [1. v-bind (縮寫 :)](#CH2-4-1)
  - [2. v-on (縮寫 @)](#CH2-4-2)
- [2-5 v-model 雙向綁定](#CH2-5)
  - [1. 基本概念](#CH2-5-1)
  - [2. 表單元素應用](#CH2-5-2)
  - [3. 修飾符 (Modifiers)](#CH2-5-3)
- [2-6 v-if 與 v-show 條件渲染](#CH2-6)
  - [1. v-if](#CH2-6-1)
  - [2. v-show](#CH2-6-2)
- [2-7 v-for 列表渲染與 key](#CH2-7)
  - [1. 遍歷陣列 (Array)](#CH2-7-1)
  - [2. 遍歷物件 (Object)](#CH2-7-2)
  - [3. 遍歷範圍 (Range)](#CH2-7-3)
  - [4. 狀態維持 (key 的重要性)](#CH2-7-4)
  - [5. v-for 與 v-if](#CH2-7-5)
- [2-8 computed 與 watch](#CH2-8)
  - [1. computed (計算屬性)](#CH2-8-1)
  - [2. watch (監聽器)](#CH2-8-2)
- [2-9 watchEffect](#CH2-9)
  - [1. 核心特性](#CH2-9-1)
  - [2. 常見用法](#CH2-9-2)
  - [3. 清除副作用](#CH2-9-3)
  - [4. 停止追蹤](#CH2-9-4)
  - [5. watch vs watchEffect 比較](#CH2-9-5)

---

### 序

Vue.js 是一個廣受歡迎的 JavaScript 前端框架，專注於打造現代化的使用者介面。它誕生於 2014 年，並在 2020 年推出了作為當前主流的 Vue 3 版本。在前端開發領域，Vue 與 Meta 開發的 React、Google 開發的 Angular 齊名，並列為最核心的三大框架。

在這個章節，我們會一起來學習 Vue 3 的基礎知識。為了讓大家能無痛上手，專心在 Vue 的核心語法上，我們先跳過那些複雜的設定和工具，改用最簡單、最輕便的 CDN 方式來玩 Vue。

用這個方法，你只需要一個 HTML 檔案，就能直接在裡面寫 Vue 程式碼並看到成果，對於理解 Vue 的基本運作原理非常有幫助喔！

---

## <a id="CH2-1"></a>[2-1 Vue 框架簡介](#目錄)

### <a id="CH2-1-1"></a>[1. Vue 的核心優勢](#目錄)

- **宣告式渲染 (Declarative Rendering)**：Vue 允許開發者使用簡潔的模板語法，將 DOM 與底層的 JavaScript 資料狀態宣告式地綁定。當資料變更時，Vue 會自動更新對應的 DOM，無需手動操作。
- **響應式系統 (Reactivity)**：Vue 擁有高效的響應式系統，能夠精確追蹤依賴關係，並在狀態變更時僅更新必要的元件，最小化 DOM 操作以提升效能。
- **元件化架構 (Component-Based)**：Vue 鼓勵將介面拆解為獨立、可重複使用且易於管理的元件。這不僅提高了程式碼的可維護性，也促進了大型專案的協作效率。

### <a id="CH2-1-2"></a>[2. Vue 3 的現代化特性](#目錄)

隨著 Vue 3 的發布，框架引入了多項現代化改進，進一步提升了開發靈活性與執行效能：

- **Composition API**：這是 Vue 3 的一項重大變革。相比於傳統的 Options API，Composition API 提供了一種更靈活的邏輯組織方式，允許開發者將相關的業務邏輯聚合在一起，並更容易地在不同元件間提取與重用邏輯。
- **效能優化**：Vue 3 核心採用 TypeScript 重寫，並引入了更輕量的虛擬 DOM 演算法與編譯器優化，大幅減少了打包體積並提升了執行速度。

### <a id="CH2-1-3"></a>[3. 完整的生態系](#目錄)

Vue 擁有一個成熟且豐富的官方生態系，能夠滿足各種開發需求：

- **Vue Router**：官方路由管理器，用於建構單頁應用程式 (SPA)。
- **Pinia**：新一代的狀態管理庫，旨在取代 Vuex，提供更直觀的 API 與更佳的 TypeScript 支援。
- **Vite**：新一代的前端建置工具，提供極速的冷啟動與熱模組替換 (HMR) 體驗。
- **Nuxt**：基於 Vue 的全端框架，提供伺服器端渲染 (SSR)、靜態網站生成 (SSG) 等進階功能，有利於 SEO 與效能優化。

### <a id="CH2-1-4"></a>[4. 小結](#目錄)

Vue 是一個極具彈性的框架，它「可大可小」：小到可以直接在單一 HTML 檔案中透過 CDN 引入，用來處理簡單的互動邏輯；大到可以搭配完整的工具鏈 (如 Vite, Vue Router, Pinia)，架構出複雜且高效的大型單頁應用程式 (SPA)。這種漸進式的特性，讓開發者能根據專案規模選擇最適合的開發方式。

> **💡 備註：什麼是 SPA (Single Page Application)？**
> 單頁應用程式 (SPA) 是一種網頁應用程式或網站，它透過動態重寫當前頁面來與使用者互動，而非從伺服器重新載入整個新頁面。這種方式能提供更流暢、類似原生應用程式的使用者體驗。

> **💡 備註：SSR 與 CSR**
>
> - **CSR (Client-Side Rendering)**：
>   客戶端渲染。瀏覽器只下載最小限度的 HTML 與 JavaScript，剩下的畫面渲染工作都由使用者的瀏覽器執行。
>
>   - **優點**：互動體驗佳，使用者感受較流暢，分工明確、適合大型專案開發。
>   - **缺點**：SEO 較差(Search Engine Optimization，搜尋引擎最佳化)，搜尋引擎可能爬不到動態產生的內容。初次載入速度較慢，因為要下載整包 JS。
>   - **舉例**：Vue、JS、jQuery 等前端瀏覽器程式渲染。
>
> - **SSR (Server-Side Rendering)**：
>   伺服器端渲染。網頁的 HTML 結構在伺服器端就先組裝好，再送給瀏覽器顯示，隨後才執行 JavaScript。
>   - **優點**：SEO 友善，適合內容型網站。
>   - **缺點**：伺服器運算壓力較大，頁面切換可能較慢（需重新載入）。
>   - **舉例**：傳統 SpringMVC + Thymeleaf。

---

## <a id="CH2-EXT"></a>[開始之前...先安裝一些 VS Code 插件](#目錄)

為了讓開發過程更加順利，請先安裝以下 Visual Studio Code 插件：

### 建議安裝

1.  **Live Server**：提供本地開發伺服器，具備即時重新整理功能。
2.  **Prettier - Code formatter**：程式碼格式化工具，保持程式碼風格一致。
3.  **Vue - Official**：Vue 官方提供的語言支援插件，提供語法 highlight、智能提示、自動補全等功能。
4.  **Vue 3 Snippets**：提供 Vue 3 的程式碼片段，加速開發。
    > **💡 備註**  
    > Windows 系統下，可到 **「C:\Users\\[使用者名稱]\\.vscode\extensions\hollowtree.vue-snippets-[版本號]\snippets\vue.json」** 中，修改預設的程式碼片段。

### 未來專案中可能會遇見的插件或設定

- **Vue - TypeScript support**：當你開始使用 TypeScript 開發 Vue 時，這會是必備的插件。
- **ESLint**：靜態程式碼分析工具，用來尋找有問題的模式或不符合團隊風格規範的程式碼。
- **.prettierrc**：這不是插件，而是 Prettier 的設定檔。通常團隊開發會統一一份設定檔，確保大家的程式碼排版格式一致。
- **.eslintrc.js / .eslintrc.json**：ESLint 的設定檔。用來定義檢查規則，例如是否允許使用 `var`、是否強制使用單引號等。

---

## <a id="CH2-2"></a>[2-2 Vue App 與插值語法](#目錄)

### <a id="CH2-2-1"></a>[1. 建立第一個 Vue 應用程式](#目錄)

建立 Vue 應用程式的步驟相當直觀：引入 Vue 核心庫 -> 建立實例 -> 掛載至 DOM。

```html
<div id="app">
  <div>Hello, {{title}}</div>
</div>

<script type="module">
  // 引入 Vue 核心庫
  import { createApp } from "https://cdnjs.cloudflare.com/ajax/libs/vue/3.5.22/vue.esm-browser.prod.min.js";

  // 建立應用程式實例
  const app = createApp({
    setup() {
      const title = "Vue 3!";
      return { title };
    },
  });

  // 將應用程式掛載到 DOM 元素上
  app.mount("#app");
</script>
```

> **深入解析**：
> `createApp` 產生的 `app` 實例是一個「應用程式物件」，它擁有全域的設定能力（如註冊全域元件、插件）。而 `mount` 之後回傳的則是「根元件實例」。

> **關於 CDN (Content Delivery Network，內容傳遞網路)**  
> 在範例中我們使用了 `cdnjs` 這個 CDN 服務來引入 Vue。CDN 就像是分佈在世界各地的倉庫，當你請求檔案時，它會從離你最近的倉庫發貨，讓載入速度更快。  
> 對於學習或快速原型開發，直接在 HTML 中引入 CDN 連結是最方便的方式，不需要安裝 Node.js 或設定複雜的建置工具。

### <a id="CH2-2-2"></a>[2. 插值語法 (Mustache Syntax) {{ }}](#目錄)

在 HTML 模板中，使用雙大括號 `{{ }}` 可將 JavaScript 變數的值動態渲染至頁面上。Vue 會自動追蹤這些變數的變化並更新 DOM。注意：雙大括號內可以寫簡單的 JavaScript 表達式（如 `{{ number + 1 }}` 或 `{{ ok ? 'YES' : 'NO' }}`），但不能寫宣告語句（如 `var a = 1`）。

---

### <a id="CH2-2-3"></a>[3. Vue API 風格比較 (Options vs Composition)](#目錄)

Vue 提供了兩種不同的 API 風格來撰寫元件邏輯：

- **Options API (選項式 API)**：Vue 2 的經典寫法，透過 `data`, `methods`, `mounted` 等選項物件來組織邏輯。結構清晰，適合初學者或小型專案。
- **Composition API (組合式 API)**：Vue 3 引入的新寫法 (搭配 `<script setup>`)，將邏輯依據功能進行分組。提供了更佳的邏輯復用性與 TypeScript 支援，是目前**官方推薦的主流寫法**。

**比較範例 (計數器)：**

**1. Options API**

```html
<div id="app">
  <button @click="increment">Count is: {{ count }}</button>
</div>

<script type="module">
  import { createApp } from "https://cdnjs.cloudflare.com/ajax/libs/vue/3.5.22/vue.esm-browser.prod.min.js";

  createApp({
    data() {
      return {
        count: 0,
      };
    },
    methods: {
      increment() {
        this.count++;
      },
    },
  }).mount("#app");
</script>
```

**2. Composition API**

```html
<div id="app"></div>

<script type="module">
  import {
    createApp,
    ref,
  } from "https://cdnjs.cloudflare.com/ajax/libs/vue/3.5.22/vue.esm-browser.prod.min.js";

  const app = createApp({
    setup() {
      const count = ref(0);
      const increment = () => {
        count.value++;
      };
      return { count, increment };
    },
    template: `<button @click="increment">Count is: {{ count }}</button>`,
  });

  app.mount("#app");
</script>
```

---

## <a id="CH2-3"></a>[2-3 ref 與 reactive](#目錄)

在 Vue 3 的 Composition API 中，若要使變數具有「響應式 (Reactivity)」特性，必須使用 `ref` 或 `reactive` 進行包裝。

### <a id="CH2-3-1"></a>[1. ref](#目錄)

- **適用範圍**：任何資料型別（包含基本型別如數字、字串，以及物件、陣列）。
- **原理**：Vue 會將傳入的值包裝在一個物件的 `.value` 屬性中。若傳入的是物件或陣列，Vue 內部會自動透過 `reactive` 將其轉為 Proxy 物件。

  > **💡 備註：Proxy 是什麼？**
  > Proxy 是 ES6 的新特性，允許我們攔截並自定義物件的基本操作（如屬性查找、賦值）。這讓 Vue 3 能更全面地監聽資料變動，解決了 Vue 2 使用 `Object.defineProperty` 時的諸多限制（例如無法偵測屬性新增）。

  ```javascript
  // Proxy 簡單範例
  const handler = {
    get: function (obj, prop) {
      return prop in obj ? obj[prop] : "屬性不存在";
    },
    set: function (obj, prop, value) {
      console.log(`正在修改屬性 ${prop} 為 ${value}`);
      obj[prop] = value;
      return true;
    },
  };
  const p = new Proxy({}, handler);
  p.a = 1; // 控制台輸出: 正在修改屬性 a 為 1
  console.log(p.a); // 1
  console.log(p.b); // "屬性不存在"
  ```

- **注意事項**：在 JavaScript 邏輯中讀取或修改值時，**必須**存取 `.value` 屬性；在 HTML 模板中則可直接使用，Vue 會自動解包 (Unwrap)。

```javascript
// ref 簡單範例
const count = ref(0);
console.log(count.value); // 0
count.value++;
```

### <a id="CH2-3-2"></a>[2. reactive](#目錄)

- **適用範圍**：僅限於物件或陣列（不支援基本型別）。
- **原理**：基於 ES6 Proxy 實作，直接攔截物件的讀寫操作。
- **限制**：若對 reactive 物件進行解構賦值 (Destructuring)，會失去響應性（除非搭配 `toRefs` 使用）。

  > **💡 備註：toRefs**  
  > 當你需要解構 `reactive` 物件，同時又希望保持響應性時，可以使用 `toRefs`。它會將物件中的每個屬性都轉換為獨立的 `ref`。
  >
  > ```javascript
  > import { reactive, toRefs } from "vue";
  >
  > const state = reactive({ name: "Vue", version: 3 });
  > // 直接解構會失去響應性： const { name } = state;
  >
  > // 使用 toRefs 保持響應性
  > const { name, version } = toRefs(state);
  > // 此時 name 和 version 都是 ref 物件，需用 .value 存取
  > ```

### <a id="CH2-3-3"></a>[3. 比較 ref 與 reactive](#目錄)

| 特性         | ref                                          | reactive                                 |
| :----------- | :------------------------------------------- | :--------------------------------------- |
| **資料型別** | 支援所有型別（基本型別 + 物件/陣列）         | 僅支援物件或陣列                         |
| **存取方式** | 需透過 `.value` 存取                         | 直接存取屬性                             |
| **重新賦值** | 可以直接替換整個 `.value`                    | 不能直接替換整個物件（會失去響應性連結） |
| **建議場景** | 單一變數、基本型別、或者希望能替換整個物件時 | 資料結構較複雜且屬性相關聯的物件         |

> **開發建議**：  
> 許多開發者傾向**全部使用 `ref`**，因為統一使用 `.value` 可以減少選擇判斷，且避免 reactive 解構失去響應性的問題。但在處理大型巢狀物件時，`reactive` 依然有其便利性。

---

## <a id="CH2-4"></a>[2-4 v-bind 與 v-on 指令](#目錄)

Vue 提供了一系列以 `v-` 開頭的指令 (Directives)，用於擴充 HTML 的功能。

### <a id="CH2-4-1"></a>[1. v-bind](#目錄)

用於動態綁定 HTML 標籤的屬性 (Attribute)，例如 `src`、`href`、`title`、`class`、`style` 等。

**完整寫法與縮寫：**

因為 `v-bind` 使用頻率非常高，Vue 提供了縮寫語法 `:`。

```html
<!-- 完整寫法 -->
<img v-bind:src="imageSrc" v-bind:title="imageTitle" />

<!-- 縮寫 (推薦) -->
<img :src="imageSrc" :title="imageTitle" />
```

**基本用法：**

```html
<button :disabled="isButtonDisabled">Click Me</button>
```

**Class 與 Style 綁定：**

Class 與 Style 綁定提供了特殊的語法增強，支援**物件 (Object)** 與 **陣列 (Array)** 寫法，讓樣式切換更直覺。

- **物件語法 (Object Syntax)**：根據 Truthy/Falsy 值決定是否套用。

```html
<!-- 若 isActive 為 true，則 class="active" -->
<div :class="{ active: isActive, 'text-danger': hasError }"></div>

<!-- Style 綁定 -->
<div :style="{ color: activeColor, fontSize: fontSize + 'px' }"></div>
```

- **陣列語法 (Array Syntax)**：套用多個 Class。

```html
<div :class="[activeClass, errorClass]"></div>
```

### <a id="CH2-4-2"></a>[2. v-on](#目錄)

用於監聽 DOM 事件，並在觸發時執行 JavaScript 程式碼。

**完整寫法與縮寫：**

`v-on` 同樣擁有縮寫語法 `@`。

```html
<!-- 完整寫法 -->
<button v-on:click="count++">Add 1</button>

<!-- 縮寫 (推薦) -->
<button @click="count++">Add 1</button>
```

**基本用法：**

```html
<!-- 內聯處理器 (Inline Handlers)：事件觸發時執行內聯的 JavaScript 語句 -->
<button @click="count++">Add 1</button>

<!-- 方法處理器 (Method Handlers)：事件觸發時呼叫元件中定義的方法 -->
<button @click="greet">Greet</button>
```

**傳遞參數與事件物件：**

如果在呼叫方法時需要傳遞參數，同時又需要存取原生的 DOM 事件物件，可以使用特殊的 `$event` 變數。

```html
<!-- 傳遞參數 -->
<button @click="say('hello')">Say Hello</button>

<!-- 傳遞參數 + 原生事件物件 -->
<button @click="warn('Cannot submit', $event)">Submit</button>
```

**常用的事件修飾符 (Event Modifiers)：**

Vue 提供了修飾符來處理常見的 DOM 事件細節（如 `event.preventDefault()`），讓我們能專注在邏輯處理上。

- `.self`：只在事件是從該元素本身觸發時才觸發 (事件冒泡相關)。
- `.stop`：阻止事件冒泡 (事件冒泡相關)。
- `.capture`：使用事件捕獲模式 (事件冒泡相關)。
- `.prevent`：呼叫 `event.preventDefault()` (阻止預設行為)。
- `.once`：事件只會觸發一次。

```html
<!-- 阻止表單提交的重整頁面行為 -->
<form @submit.prevent="onSubmit"></form>

<!-- 阻止點擊事件冒泡 -->
<a @click.stop="doThis"></a>
```

**按鍵修飾符 (Key Modifiers)：**

監聽鍵盤事件時，可以過濾特定的按鍵。常見的按鍵別名包括：

- `.enter`
- `.tab`
- `.delete` (捕獲 "Delete" 和 "Backspace" 鍵)
- `.esc`
- `.space`
- `.up` / `.down` / `.left` / `.right`

```html
<!-- 只有按下 Enter 時才會觸發 submit -->
<input @keyup.enter="submit" />

<!-- 按下 Enter 提交，按下 Esc 清除 -->
<input @keyup.enter="submit" @keyup.esc="clear" />
```

**系統按鍵修飾符 (System Modifier Keys)：**

僅在按下相應按鍵時才觸發滑鼠或鍵盤事件監聽器。

- `.ctrl`
- `.alt`
- `.shift`
- `.meta` (Mac 上是對應 Command 鍵，Windows 上是對應 Windows 鍵)

```html
<!-- Alt + Enter -->
<input @keyup.alt.enter="clear" />

<!-- Ctrl + Click -->
<div @click.ctrl="doSomething">Do something</div>
```

---

## <a id="CH2-5"></a>[2-5 v-model 雙向綁定](#目錄)

`v-model` 是 Vue 中用於處理表單輸入的強大指令，它可以在「資料」與「視圖」之間建立雙向綁定。這意味著：當資料改變時，輸入框的內容會更新；當使用者在輸入框輸入內容時，資料也會隨之更新。

### <a id="CH2-5-1"></a>[1. 基本概念](#目錄)

`v-model` 其實是一個語法糖 (Syntactic Sugar)。它背後原本是結合了 `v-bind` (綁定 value) 與 `v-on` (監聽 input 事件) 的操作。

```html
<!-- v-model 的完整寫法 (原理) -->
<input :value="text" @input="text = $event.target.value" />

<!-- 使用 v-model 簡寫 -->
<input v-model="text" />
```

### <a id="CH2-5-2"></a>[2. 表單元素應用](#目錄)

`v-model` 會根據不同的表單元素自動使用正確的屬性與事件：

**文本 (Text & Textarea)**

```html
<!-- 單行文本 -->
<input v-model="message" />
<p>Message is: {{ message }}</p>

<!-- 多行文本 -->
<textarea v-model="multilineMessage"></textarea>
```

**複選框 (Checkbox)**

- **單一勾選框**：綁定布林值 (Boolean)。

```html
<input type="checkbox" id="checkbox" v-model="checked" />
<label for="checkbox">{{ checked }}</label>
```

- **多個勾選框**：綁定到同一個陣列 (Array)。

```html
<div>Checked names: {{ checkedNames }}</div>

<input type="checkbox" id="jack" value="Jack" v-model="checkedNames" />
<label for="jack">Jack</label>

<input type="checkbox" id="john" value="John" v-model="checkedNames" />
<label for="john">John</label>

<input type="checkbox" id="mike" value="Mike" v-model="checkedNames" />
<label for="mike">Mike</label>
```

**單選框 (Radio)**

綁定選中的值。

```html
<div>Picked: {{ picked }}</div>

<input type="radio" id="one" value="One" v-model="picked" />
<label for="one">One</label>

<input type="radio" id="two" value="Two" v-model="picked" />
<label for="two">Two</label>
```

**選擇器 (Select)**

```html
<div>Selected: {{ selected }}</div>

<!-- 未設定 Option Value -->
<select v-model="selected">
  <option disabled value="">請選擇</option>
  <option>A</option>
  <option>B</option>
  <option>C</option>
</select>

<!-- 自定義 Option Value -->
<div>Selected: {{ country }}</div>

<select v-model="country">
  <option disabled value="">請選擇國家</option>
  <option value="tw">台灣</option>
  <option value="cn">中國</option>
  <option value="jp">日本</option>
  <option value="kr">韓國</option>
  <option value="us">美國</option>
</select>
```

### <a id="CH2-5-3"></a>[3. 修飾符 (Modifiers)](#目錄)

Vue 提供了修飾符來處理常見的輸入問題：

- `.lazy`：從預設的 `input` 事件 (輸入時即時更新) 改為 `change` 事件 (離開焦點或按 Enter 後更新)。
- `.number`：自動將使用者的輸入值透過 `parseFloat()` 轉為數字型別。若無法轉換則回傳原值。
- `.trim`：自動去除使用者輸入內容的首尾空白。

```html
<!-- lazy: 失去焦點後才更新 msg -->
<input v-model.lazy="msg" />

<!-- number: 輸入 "123" 會是 number 型別而非 string -->
<input v-model.number="age" type="number" />

<!-- trim: 自動去除首尾空白 -->
<input v-model.trim="msg" />
```

---

## <a id="CH2-6"></a>[2-6 v-if 與 v-show 條件渲染](#目錄)

這兩個指令用於控制元素的顯示與隱藏。

### <a id="CH2-6-1"></a>[1. v-if](#目錄)

- 若條件為 `false`，元素會被**完全從 DOM 中移除**。
- 支援 `v-else` 與 `v-else-if`。

### <a id="CH2-6-2"></a>[2. v-show](#目錄)

- 若條件為 `false`，僅透過 CSS `display: none` 隱藏元素。
- 不支援 `<template>` 標籤，也不支援 `v-else`。

> **效能考量**：  
> `v-if` 涉及 DOM 的銷毀與重建，故有較高的切換消耗；而 `v-show` 則有較高的初始渲染消耗，因為不管條件為何都會渲染。如果需要頻繁切換，例如 Dropdown 選單，請用 `v-show`；如果條件很少改變，例如使用者權限檢查，請用 `v-if`。

---

## <a id="CH2-7"></a>[2-7 v-for 列表渲染與 key](#目錄)

`v-for` 指令用於基於一個陣列或物件來渲染一個列表。它的語法類似於 JavaScript 的 `for...of` 迴圈。

### <a id="CH2-7-1"></a>[1. 遍歷陣列 (Array)](#目錄)

基本的陣列遍歷寫法。

```html
<ul>
  <!-- item 是陣列中的元素 -->
  <li v-for="item in items">{{ item.message }}</li>
</ul>

<ul>
  <!-- 關鍵字使用 in 或 of 效果相同 -->
  <li v-for="item of items">{{ item.message }}</li>
</ul>

<ul>
  <!-- item 是陣列中的元素，index 是索引 (可選) -->
  <li v-for="(item, index) in items">{{ index }} - {{ item.message }}</li>
</ul>
```

### <a id="CH2-7-2"></a>[2. 遍歷物件 (Object)](#目錄)

`v-for` 也能用來遍歷物件的屬性。參數順序為 `(value, key, index)`。

```html
<ul>
  <li v-for="(value, key, index) in myObject">
    {{ index }}. {{ key }}: {{ value }}
  </li>
</ul>
```

### <a id="CH2-7-3"></a>[3. 遍歷範圍 (Range)](#目錄)

`v-for` 可以直接接受一個整數，用來重複渲染特定次數。

```html
<!-- 渲染結果為 1 到 10 -->
<span v-for="n in 10">{{ n }}</span>
```

### <a id="CH2-7-4"></a>[4. 狀態維持 (key 的重要性)](#目錄)

Vue 在處理列表更新時，為了效能考量，預設採用「就地更新」策略。簡單來說，當資料順序改變時，Vue **不會**真的去搬移網頁上的 DOM 元素位置，而是直接修改原本位置上的內容。

**什麼時候需要 key？**

- **靜態列表**：如果你的列表資料渲染後就不會再變動，那麼不加 `key` 是沒問題的。
- **動態列表**：如果列表會**重新排序**、**新增/刪除**，或者項目中包含**輸入框**等狀態，就**必須**綁定唯一的 `key`（通常是 `id`）。這樣 Vue 才能識別每個項目的身份，正確地移動它們，而不是只換內容。

```html
<div v-for="item in items" :key="item.id">
  <!-- 內容 -->
</div>
```

> **注意：** 請盡量避免使用 `index` (陣列索引) 作為 `key`，因為當資料插入或刪除時，索引值會改變，導致 Vue 對應錯誤，失去使用 key 的意義。

### <a id="CH2-7-5"></a>[5. v-for 與 v-if](#目錄)

**不可以**在同一元素上同時使用 `v-for` 和 `v-if`。  
因為 `v-if` 優先級高於 `v-for`，這意味著 `v-if` 的條件將無法訪問到 `v-for` 範圍內的變數。

**解決方案：**

- 若要過濾列表項目：請使用 **computed** 計算屬性先過濾好資料。
- 若要控制整個列表顯示：將 `v-if` 移動到容器元素（如 `<ul>`）上。

```html
<!-- 錯誤寫法 (v-if 無法讀取 item) -->
<!-- <li v-for="item in items" v-if="!item.isHidden">{{ item.name }}</li> -->

<!-- 正確寫法：使用 template 標籤包裹 -->
<template v-for="item in items" :key="item.id">
  <li v-if="!item.isHidden">{{ item.name }}</li>
</template>

<!-- 最佳實踐：使用 computed 過濾後的資料進行渲染 -->
<div id="app">
  <ul>
    <li v-for="item in filteredItems" :key="item.id">{{ item.name }}</li>
  </ul>
</div>

<script>
  import {
    createApp,
    ref,
    computed,
  } from "https://cdnjs.cloudflare.com/ajax/libs/vue/3.5.22/vue.esm-browser.prod.min.js";

  const app = createApp({
    setup() {
      const items = ref([
        { id: "A1", name: "Item 1", isHidden: false },
        { id: "A2", name: "Item 2", isHidden: true },
        { id: "A3", name: "Item 3", isHidden: false },
      ]);
      const filteredItems = computed(() => {
        return items.value.filter((item) => !item.isHidden);
      });

      return { filteredItems };
    },
  }).mount("#app");
</script>
```

---

## <a id="CH2-8"></a>[2-8 computed 與 watch](#目錄)

這兩個 API 都是用來處理資料變更後的響應邏輯，但適用場景不同。

### <a id="CH2-8-1"></a>[1. computed (計算屬性)](#目錄)

`computed` 用於宣告「依賴其他響應式資料」所計算出來的新值。

- **具備快取機制 (Caching)**：`computed` 只有在它依賴的資料變更時，才會重新計算。如果依賴不變，多次存取會直接回傳快取值（效能較好）。
- **唯讀與可寫**：預設是唯讀的，但也可透過 Getter/Setter 定義可寫的計算屬性。

**基本用法 (唯讀)：**

```javascript
// 當 count 變更時，doubleCount 會自動重新計算
const count = ref(1);
const doubleCount = computed(() => count.value * 2);

console.log(doubleCount.value); // 2
```

**可寫的計算屬性 (Writable)：**

```javascript
const firstName = ref("John");
const lastName = ref("Doe");

const fullName = computed({
  // getter
  get() {
    return firstName.value + " " + lastName.value;
  },
  // setter
  set(newValue) {
    const names = newValue.split(" ");
    firstName.value = names[0];
    lastName.value = names[1];
  },
});

fullName.value = "David Smith"; // 可寫的計算屬性
console.log(firstName.value); // 'David'
```

### <a id="CH2-8-2"></a>[2. watch (監聽器)](#目錄)

`watch` 用於監聽特定的資料來源，並在變更時執行「副作用 (Side Effects)」，例如發送 API 請求、操作 DOM、或控制非同步邏輯。

**基本用法：**

```javascript
const message = ref("Hello");

watch(message, (newValue, oldValue) => {
  console.log(`訊息已變更：${oldValue} -> ${newValue}`);
});
```

**選項參數：**

- **deep**: 強制開啟深度監聽（監聽物件內部所有層級變更）。
- **immediate**: 在監聽器建立時立即觸發一次回調。

```javascript
watch(source, callback, { deep: true, immediate: true });
```

**監聽多種來源：**

`watch` 第一個參數可以是：

1. 一個 ref
2. 一個 reactive 物件
3. 一個 getter 函式：當你需要監聽「Reactive 物件的特定屬性」或「計算後的數值」時使用。

   ```javascript
   // 範例 A: 監聽物件的特定屬性

   /* 
   【注意】
   不能直接寫 watch(state.count)，這樣傳入的是「已經被取出的數字值」，Vue 無法對它建立依賴，因此不具備響應性。
   
   必須改用 getter 箭頭函式 watch(() => state.count)，才能讓 Vue 正確追蹤該屬性的變化。
   */
   const state = reactive({ count: 0 });

   watch(
     () => state.count,
     (newCount) => {
       console.log(`Count changed to: ${newCount}`);
     }
   );

   // 範例 B: 監聽運算後的結果
   const x = ref(1);
   const y = ref(2);

   watch(
     () => x.value + y.value,
     (sum) => {
       console.log(`x + y = ${sum}`);
     }
   );
   ```

---

## <a id="CH2-9"></a>[2-9 watchEffect](#目錄)

`watchEffect` 是一個立即執行且自動追蹤依賴的監聽器，非常適合用來處理「執行邏輯依賴於多個變數」或「不需知道新舊值變化」的副作用。

簡單來說，**`watchEffect` = 自動偵測所有依賴 + 自動觸發副作用函式**

`watch` = 精準監控  
`watchEffect` = 全自動副作用引擎

### <a id="CH2-9-1"></a>[1. 核心特性](#目錄)

1.  **立即執行**：在 `watchEffect` 建立時，會立即執行一次傳入的函式。
2.  **自動追蹤依賴**：不需要像 `watch` 一樣手動列出監聽來源。Vue 會自動偵測函式內部讀取了哪些響應式變數 (Ref/Reactive)，並將其加入依賴。
3.  **無法存取舊值**：它只關心當前的狀態，無法像 `watch` 一樣取得 `oldValue`。
4.  **應注意效能問題**：`watchEffect` 會自動追蹤函式內所有讀取到的響應式變數，應避免讀取非必要的資料，以免觸發無謂的重新執行。

**基本範例：**

```javascript
const count = ref(0);

watchEffect(() => {
  // 建立時立即執行，且當 count 變更時也會執行
  console.log(`Current count is: ${count.value}`);
});
```

### <a id="CH2-9-2"></a>[2. 常見用法](#目錄)

- **自動同步 DOM**：例如根據狀態自動更新 `document.title`。

  ```javascript
  watchEffect(() => {
    document.title = `Current Count: ${count.value}`;
  });
  ```

- **監控多狀態**：當除錯或需要記錄多個狀態歷程時。

  ```javascript
  watchEffect(() => {
    console.log(`Login Status: ${isLogin.value}, User: ${userInfo.value.name}`);
  });
  ```

- **自動驅動 AJAX**：當 ID 改變時自動重新抓取資料。

  ```javascript
  watchEffect(async () => {
    // 當 id 改變時自動觸發 fetch
    const res = await fetch(`/api/user/${userId.value}`);
    userData.value = await res.json();
  });
  ```

- **Computed-like (含副作用)**：類似 `computed` 自動追蹤依賴，但不是為了產出新值，而是為了執行某些動作（如儲存 localStorage）。

  ```javascript
  watchEffect(() => {
    localStorage.setItem("vue-todo", JSON.stringify(todos.value));
  });
  ```

### <a id="CH2-9-3"></a>[3. 清除副作用](#目錄)

當 `watchEffect` 重新執行（依賴變更）或元件卸載時，我們可能需要清除上一次執行所產生的副作用（例如：取消 API 請求、清除計時器）。我們可以透過傳入的 `onCleanup` 函式來達成。

```javascript
/*
  onCleanup 用於註冊清理回調，它會在「副作用重新執行前」或「元件卸載時」觸發，確保舊的副作用（如計時器）被正確清除。

  以下程式碼會啟動一個計時器，每一秒使 count.value +1。
  當依賴的 title.value 變更時，會重新執行 watchEffect，並清除上一次的計時器。
   
  Q. 為什麼 count.value 變更時不會重新執行 watchEffect？
  A. 因為 count.value 不是 watchEffect 的依賴，只有在 watchEffect 第一次執行時，才會對函式內所有使用到的「響應式」變數建立依賴；第一次執行時，只建立「title.value」、「timer」兩個依賴，之後 timer 對 count.value 的修改不會觸發重新執行。
*/

watchEffect((onCleanup) => {
  console.log("title", title.value);
  const timer = setInterval(() => {
    count.value++;
  }, 1000);

  // 當依賴變更導致重新執行，或元件卸載時觸發，onCleanup 有保護機制，內部修改不會重新調用 watchEffect。
  onCleanup(() => {
    clearInterval(timer);
    console.log("Cleanup timer");
  });
});
```

### <a id="CH2-9-4"></a>[4. 停止追蹤](#目錄)

`watchEffect` 會回傳一個停止函式 (Stop Handle)，呼叫它即可停止監聽。

```javascript
const stop = watchEffect(() => {
  /* ... */
});

// 當不再需要時，呼叫 stop() 停止監聽
stop();
```

### <a id="CH2-9-5"></a>[5. watch vs watchEffect 比較](#目錄)

| 特性         | watch                                             | watchEffect                                        |
| :----------- | :------------------------------------------------ | :------------------------------------------------- |
| **依賴來源** | 需手動明確指定                                    | 自動偵測函式內用到的變數                           |
| **執行時機** | 預設懶執行 (資料變更才跑)，可設 `immediate: true` | 建立時**立即執行**                                 |
| **新舊值**   | 可取得 `newValue` 與 `oldValue`                   | 無法取得，僅能讀取當前值                           |
| **適用場景** | 需要新舊值比對、或副作用執行時機需精確控制時      | 邏輯依賴多個變數、或只需要執行副作用而不需比對值時 |

---

[上一章：章節 1 ｜ ES6 必備基礎](./Chapter1_ES6.md) | [下一章：章節 3 ｜ Vite + 元件化 + Plugins](./Chapter3_ViteComponents.md)
