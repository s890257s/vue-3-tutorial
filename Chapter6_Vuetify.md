# 章節 6 ｜ UI 元件庫 & Vuetify

## <a id="目錄"></a>目錄

- [6-1 什麼是 UI 元件庫？](#CH6-1)
  - [1. 為什麼我們需要元件庫？](#CH6-1-1)
  - [2. 自刻 CSS vs 使用元件庫](#CH6-1-2)
- [6-2 Vuetify 簡介與安裝](#CH6-2)
  - [1. 認識 Vuetify](#CH6-2-1)
  - [2. 專案安裝](#CH6-2-2)
- [6-3 核心觀念 (Layout & Spacing)](#CH6-3)
  - [1. 響應式格線系統 (Grid System)](#CH6-3-1)
  - [2. 強大的輔助類別 (Helpers)](#CH6-3-2)
- [6-4 常用元件實戰](#CH6-4)
  - [1. 按鈕與卡片 (Button & Card)](#CH6-4-1)
  - [2. 表單控制項 (Form Inputs)](#CH6-4-2)

### 序

在前面的章節中，我們學會了 Vue 的**邏輯層 (JS)** 與 **結構層 (HTML)**，但要做出一個讓使用者覺得「好用」且「好看」的現代化網站，**樣式層 (CSS)** 是絕對不能被忽略的。

但是，手寫 CSS 是一件非常耗時且痛苦的事情：要處理瀏覽器相容性、要做 RWD 響應式排版、要設計美觀的按鈕跟陰影...。

這就是 **UI 元件庫** 登場的時候了！

---

## <a id="CH6-1"></a>[6-1 什麼是 UI 元件庫？](#目錄)

### <a id="CH6-1-1"></a>[1. 為什麼我們需要元件庫？](#目錄)

試想一下，如果你想自己組裝一台電腦，你會選擇：

1. **從零開始**：自己去挖礦、提煉金屬、設計電路板、焊接晶片...。
2. **購買現成零件**：買 CPU、記憶體、主機板，然後把它們組裝起來。

**UI 元件庫 (UI Component Library)** 就好比是【選項二】。它是一套已經由專業設計師與工程師開發好、測試過、且風格統一的「介面零件包」。

常見的元件庫通常包含：

- **基礎元件**：按鈕 (Button)、輸入框 (Input)、單選/複選框 (Radio/Checkbox)
- **導航元件**：選單 (Menu)、側邊欄 (Drawer)、分頁 (Pagination)
- **回饋元件**：對話框 (Dialog)、提示訊息 (Snackbar/Toast)
- **排版系統**：格線 (Grid)、容器 (Container)

### <a id="CH6-1-2"></a>[2. 自刻 CSS vs 使用元件庫](#目錄)

| 特性         | 手寫 CSS (Vanilla / Tailwind)     | UI 元件庫 (Vuetify / Element Plus)           |
| :----------- | :-------------------------------- | :------------------------------------------- |
| **開發速度** | 慢，需一行行刻                    | **極快**，直接複製貼上標籤即可               |
| **自由度**   | 100%，想長怎樣都可以              | 受限於框架設計，客製化需覆蓋樣式             |
| **一致性**   | 需靠開發者自律維持                | **天生一致**，整體視覺風格統一               |
| **互動性**   | 需自己寫 JS (如 Toggle, Dropdown) | **內建互動邏輯**，開箱即用                   |
| **適用場景** | 高度客製化的形象官網              | **後台管理系統**、功能型 App、快速原型的 MVP |

簡單來說：**如果你要快速開發一個功能完善的後台系統，UI 元件庫是你的最佳戰友。**

---

## <a id="CH6-2"></a>[6-2 Vuetify 簡介與安裝](#目錄)

### <a id="CH6-2-1"></a>[1. 認識 Vuetify](#目錄)

**Vuetify** 是 Vue 生態系中最老牌、功能最完整的 UI 框架之一。它嚴格遵循 Google 的 **Material Design** 設計規範（就是 Android 手機常見的那種風格）。

**它的特點：**

- **元件極多**：幾乎你想得到的元件它都有（超過 80+ 個）。
- **文件詳細**：官方文件非常豐富，每個屬性都有範例。
- **社群龐大**：遇到問題很容易找到解答。

### <a id="CH6-2-2"></a>[2. 專案安裝](#目錄)

對於既有的專案，我們可以遵循[官方文件](https://vuetifyjs.com/en/getting-started/installation/#existing-projects)的指示進行安裝。

在 Terminal 輸入：

```bash
npm i vuetify
```

接著修改 `src/main.js` 進行全域註冊：

```javascript
// 建立 App 實例
import { createApp } from "vue";
import App from "./App.vue";
const app = createApp(App);

// 安裝 Vuetify
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
const vuetify = createVuetify({
  components,
  directives,
});
app.use(vuetify); // 使用 Vuetify Plugin

// 掛載 App 實例到 #app 元素
app.mount("#app");
```

以上便完成 Vuetify 的安裝，但為了讓 icon 能正常顯示，接下來要安裝 Vuetify 預設的 Google Material Icon，詳情可參考[官方文件](https://vuetifyjs.com/en/features/icon-fonts/#mdi-css)。  
在 terminal 輸入：

```bash
npm i @mdi/font -D
```

接著在 main.js 引用 icon 樣式

```javascript
import "@mdi/font/css/materialdesignicons.css";

// 💡 提示：
// Vuetify 3 預設已配置 mdi 為圖標集，因此只需引入 CSS 即可正常顯示。
// 若日後需切換至其他圖標集 (如 FontAwesome)，可參考以下設定：
/*
createVuetify({
  icons: {
    defaultSet: "mdi", // 可改為 'fa', 'md' 等
  },
});
*/
```

可以使用以下的程式碼確認是否安裝 vuetify 成功

```html
<template>
  <div class="v-container">
    <div class="v-row">
      <div class="v-col">
        <v-carousel>
          <v-carousel-item
            src="https://shorturl.at/BtWce"
            cover
          ></v-carousel-item>

          <v-carousel-item
            src="https://shorturl.at/Ai0fO"
            cover
          ></v-carousel-item>

          <v-carousel-item
            src="https://shorturl.at/EZ1fZ"
            cover
          ></v-carousel-item>
        </v-carousel>
      </div>
    </div>

    <div class="v-row">
      <div class="v-col">
        <h1>Material Design Icons</h1>
        <h3>
          <a href="https://pictogrammers.com/library/mdi/">官方參考文件</a>
        </h3>
        <hr />
        <h3>使用者: <v-icon icon="mdi-account" /></h3>
        <h3>綠色笑鼠: <v-icon icon="mdi-emoticon-lol" color="success" /></h3>
        <h3>紅色警告: <v-icon color="error" icon="mdi-alert"></v-icon></h3>
        <h3>
          Hex color(十六進位色碼) 雲朵:
          <v-icon color="#5cc1f2" icon="mdi-cloud"></v-icon>
        </h3>
      </div>
    </div>
  </div>
</template>
```

---

## <a id="CH6-3"></a>[6-3 核心觀念 (Layout & Spacing)](#目錄)

### <a id="CH6-3-1"></a>[1. 響應式格線系統 (Grid System)](#目錄)

Vuetify 的排版系統核心是 **12 欄位格線 (12-point grid)**，由三個主要元件組成：

1. `v-container`: 最外層容器，負責提供適當的邊距 (RWD)。
2. `v-row`: 列，負責包住 `v-col`。
3. `v-col`: 欄，決定內容要佔多寬 (1~12)。

**範例：**

```html
<v-container>
  <v-row>
    <!-- 視窗較小時佔滿 (12)，視窗較寬時佔一半 (6) -->
    <v-col cols="12" md="6">
      <div style="background: lightblue">左邊內容</div>
    </v-col>

    <v-col cols="12" md="6">
      <div style="background: lightpink">右邊內容</div>
    </v-col>
  </v-row>
</v-container>
```

### <a id="CH6-3-2"></a>[2. 強大的輔助類別 (Helpers)](#目錄)

不想寫 CSS 來調 margin 跟 padding 嗎？Vuetify 提供了超好用的 class 縮寫：

格式：`{property}{direction}-{size}`

- **Property**: `m` (margin), `p` (padding)
- **Direction**: `t` (top), `b` (bottom), `l` (left), `r` (right), `x` (左右), `y` (上下), `a` (all)
- **Size**: 0 ~ 16 (每單位通常是 4px)

**範例：**

- `class="mt-4"`: Margin Top 16px (4 \* 4)
- `class="pa-2"`: Padding All 8px (2 \* 4)
- `class="my-auto"`: Margin Vertical Auto (垂直置中用)
- `class="text-center"`: 文字置中

**更多實用類別：**

- **排版 (Flex)**: `d-flex`, `justify-center`, `align-center`, `flex-column`, `ga-4` (gap)
- **文字 (Typography)**: `text-h4` (大標題), `font-weight-bold` (粗體), `text-decoration-none`
- **色彩 (Colors)**: `text-primary` (主色文字), `bg-surface` (背景色), `text-error`
- **尺寸 (Sizing)**: `w-100` (寬度 100%), `h-screen` (高度 100vh)

---

## <a id="CH6-4"></a>[6-4 常用元件實戰](#目錄)

Vuetify 的元件通常以 `v-` 開頭。

### <a id="CH6-4-1"></a>[1. 按鈕與卡片 (Button & Card)](#目錄)

`v-card` 是最常用的容器，適合用來包裝內容區塊。

```html
<v-card class="mx-auto" max-width="400" elevation="8">
  <v-card-title>這是一張卡片</v-card-title>
  <v-card-text> 內容區域。這裡可以放任何文字或圖片。 </v-card-text>
  <v-card-actions>
    <!-- variant 可以改變按鈕風格：text, tonal, flat, elevated -->
    <v-btn color="primary" variant="text">取消</v-btn>
    <v-btn color="primary" variant="elevated">確定</v-btn>
  </v-card-actions>
</v-card>
```

### <a id="CH6-4-2"></a>[2. 表單控制項 (Form Inputs)](#目錄)

Material Design 的表單以其動態的 Label 特效聞名。

```html
<v-form>
  <v-text-field
    label="電子信箱"
    prepend-inner-icon="mdi-email"
    variant="outlined"
  ></v-text-field>

  <v-select
    label="選擇性別"
    :items="['男', '女', '不公開']"
    variant="outlined"
  ></v-select>

  <v-checkbox label="我同意服務條款"></v-checkbox>
</v-form>
```

---

### 總結

透過 UI 元件庫，我們不需要寫任何一行複雜的 CSS，就能做出具有陰影、圓角、RWD 與互動特效的介面。這就是現代前端開發高效率的秘密武器！

至此，Vue 3 的基礎教學旅程告一段落。希望這些工具能成為你開發路上的得力助手！

---

[上一章：章節 5 ｜ API + Pinia](./Chapter5_ApiPinia.md) | [回首頁](./README.md)
