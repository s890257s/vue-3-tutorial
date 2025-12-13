# <a id="VSCode插件"></a> 上課使用的 VS Code 插件

1. **Markdown Preview Enhanced**：提供 Markdown 文件的預覽功能，方便查看格式化後的內容。
2. **Live Server**：提供本地開發伺服器，具備即時重新整理功能。
3. **Prettier - Code formatter**：程式碼格式化工具，保持程式碼風格一致。

---

# 章節 1 ｜ ES6 必備基礎

## <a id="目錄"></a>目錄

- [1-1 ECMAScript 發展歷史](#CH1-1)
- [1-2 變數、箭頭函式、基本語法](#CH1-2)
  - [1. 變數宣告：let 與 const](#CH1-2-1)
  - [2. 箭頭函式 (Arrow Function)](#CH1-2-2)
- [1-3 Template 模板字串](#CH1-3)
  - [1. 變數嵌入](#CH1-3-1)
  - [2. 多行字串](#CH1-3-2)
- [1-4 解構賦值與展開運算子](#CH1-4)
  - [1. 解構賦值 (Destructuring)](#CH1-4-1)
  - [2. 展開運算子 (Spread Operator) ...](#CH1-4-2)
- [1-5 常用陣列方法](#CH1-5)
  - [1. map (映射)](#CH1-5-1)
  - [2. filter (過濾)](#CH1-5-2)
  - [3. find (搜尋)](#CH1-5-3)
  - [4. some (部分符合)](#CH1-5-4)
  - [5. every (完全符合)](#CH1-5-5)
  - [6. includes (包含)](#CH1-5-6)
  - [7. slice (切片)](#CH1-5-7)
  - [8. reduce (累加/歸納)](#CH1-5-8)
- [1-6 非同步處理（Promise / async / await）](#CH1-6)
  - [1. 非同步概念 (Asynchronous)](#CH1-6-1)
  - [2. Callback Hell (回呼地獄)](#CH1-6-2)
  - [3. Promise (承諾)](#CH1-6-3)
  - [4. async / await (建議使用)](#CH1-6-4)
- [1-7 模組化 (Export & Import)](#CH1-7)
  - [1. 匯出 (Export)](#CH1-7-1)
  - [2. 匯入 (Import)](#CH1-7-2)

---

### 序

嗨！歡迎來到 ES6 的世界！

ES6 (ECMAScript 2015) 是 JavaScript 發展史上的一個超級大更新，它就像是給 JavaScript 換了一套全新的裝備，提供了更簡潔、更強大的語法，讓開發者寫起程式碼來更順手、更優雅。

為什麼我們要學 ES6 呢？因為 Vue 3 的核心就是建立在 ES6 及更高版本的語法之上。如果不先熟悉這些新特性，直接跳進 Vue 3 的世界可能會覺得有點卡卡的。所以，在這章節裡，我們會帶你快速掃過一遍開發 Vue 3 時最常用到的 ES6 必備技能。

準備好了嗎？讓我們一起輕鬆搞定這些基礎吧！

---

## <a id="CH1-1"></a>[1-1 ECMAScript 發展歷史](#目錄)

| 時間         | 事件                                                                                                                                                                                                                                    |
| :----------- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1995-05-01   | Brendan Eich 在網景公司(Netscape)僅用 10 天，開發出一款用於瀏覽器的程式語言原型，並命名為 Mocha。                                                                                                                                       |
| 1995-08-01   | 微軟(Microsoft)發布 Internet Explorer 1，標誌著第一次瀏覽器大戰正式展開。                                                                                                                                                               |
| 1995-09-01   | Mocha 在內部測試中更名為 LiveScript。                                                                                                                                                                                                   |
| 1995-12-01   | LiveScript 再次更名為 JavaScript，這是為了搭上當時極為火熱的「Java」熱潮所採取的行銷策略。                                                                                                                                              |
| 1996-08-01   | 微軟發布 Internet Explorer 3，透過對 Netscape Navigator 的逆向工程，開發出名為 JScript 的語言。雖然 JScript 與 JavaScript 類似，但雙方各自有不同擴充，缺乏統一標準，導致網頁常出現「最佳瀏覽於 Netscape」或「建議使用 IE 瀏覽」的標語。 |
| 1997-06-01   | 在 Netscape 於 1996 年 11 月提交標準化申請後，ECMA(歐洲電腦製造商協會) 以 JavaScript 為基礎制定了 ECMAScript 標準(ECMA-262)。自此，JavaScript 成為 ECMAScript 最具代表性的實作。                                                        |
| 2000 初      | 隨著微軟在第一次瀏覽器大戰中取得主導地位，開始減少參與 ECMAScript 的標準化工作，轉而專注於自家的 JScript，也使 JScript 逐漸與主流 JavaScript 社群脫節。                                                                                 |
| 2004-11-01   | 源自 Netscape 開源程式碼的 Mozilla 基金會 發布 Firefox 1.0，宣告第二次瀏覽器大戰開打。                                                                                                                                                  |
| 2008-09-01   | Google Chrome 登場。憑藉簡潔的介面、強大的 V8 JavaScript 引擎 及穩定的效能，迅速成為市場新寵，最終贏得第二次瀏覽器大戰。                                                                                                                |
| 2011-03-01   | 面對激烈競爭，微軟推出 Internet Explorer 9，這是 JScript 的最後一次重大更新。                                                                                                                                                           |
| 2015-01-01   | 微軟推出 Edge 瀏覽器，改用 JavaScript 引擎，正式放棄 JScript。自此，除了 JavaScript 外，ECMAScript 不再有其他主流實作。                                                                                                                 |
| 2015-06-01   | ECMAScript 推出了 ES6/ES2015，這是 JavaScript 發展的分水嶺，確立了現代 JavaScript 的語法基礎。                                                                                                                                          |
| 截至 2025-09 | Google Chrome 以 71% 的市佔率穩居市場龍頭，成為第二次瀏覽器大戰的最終勝利者。                                                                                                                                                           |

## <a id="CH1-2"></a>[1-2 變數、箭頭函式、基本語法](#目錄)

### <a id="CH1-2-1"></a>[1. 變數宣告：let 與 const](#目錄)

在現代 JavaScript 開發中，建議避免使用 `var`，以減少變數提升 (Hoisting) 帶來的副作用。請依據變數是否需要重新賦值來選擇 `let` 或 `const`。

- **`let`**：用於宣告「值會改變」的變數。具有區塊作用域 (Block Scope)，僅在 `{}` 內有效。
- **`const`**：用於宣告「值不會改變」的常數。同樣具有區塊作用域。

> **進階觀念**：
> `const` 宣告的物件或陣列，其「內容」是可以被修改的（例如 `push` 新元素或修改屬性值），但不能重新賦值（不能寫 `user = {}`）。這是因為 `const` 鎖定的是記憶體位址的參照 (Reference)。

```javascript
const user = { name: "Alice" };
user.name = "Bob"; // 合法：修改屬性
// user = { name: "Bob" }; // 錯誤：重新賦值
```

### <a id="CH1-2-2"></a>[2. 箭頭函式 (Arrow Function)](#目錄)

箭頭函式提供了更簡潔的函式宣告語法，並在 `this` 的綁定行為上與傳統函式有所不同。

**語法比較：**

```javascript
// 傳統函式
function add(a, b) {
  return a + b;
}

// 箭頭函式
const add = (a, b) => {
  return a + b;
};

// 簡寫模式：若函式本體僅有一行回傳語句，可省略大括號與 return
const addSimple = (a, b) => a + b;
```

> **進階觀念**：
> 箭頭函式沒有自己的 `this`，它會繼承外層作用域的 `this`。這在 Vue 的選項式 API (Options API) 中可能會導致問題，但在 Vue 3 組合式 API (Composition API) 中，由於我們主要在 `setup` 函式內工作，`this` 的使用頻率已大幅降低，箭頭函式反而更為適用。

```javascript
// 箭頭函式沒有自己的 this，會使用外部的 this
const person = {
  name: "Alice",
  sayHi: function () {
    console.log("Hi, I am", this);
  },

  sayHiByArrow: () => {
    console.log("Hi, I am", this);
  },
};
person.sayHi(); // 印出 person 物件
person.sayHiByArrow(); // 印出 window 物件
```

---

## <a id="CH1-3"></a>[1-3 Template 模板字串](#目錄)

模板字串 (Template Literals) 是強大的字串處理語法，允許嵌入變數與運算式，並支援多行字串。

### <a id="CH1-3-1"></a>[1. 變數嵌入](#目錄)

使用反引號 (backtick) `來包覆字串，並用`${}` 嵌入變數。

```javascript
const name = "Alice";
const message = `Hello, ${name}!`;
// 等同於 "Hello, " + name + "!"
```

### <a id="CH1-3-2"></a>[2. 多行字串](#目錄)

不再需要使用 `\n`，直接換行即可。

```javascript
const MultiLine = `
  第一行
  第二行
`;
```

---

## <a id="CH1-4"></a>[1-4 解構賦值與展開運算子](#目錄)

解構賦值與展開運算子能有效簡化程式碼，提高開發效率，特別是在處理複雜的物件資料結構時。

### <a id="CH1-4-1"></a>[1. 解構賦值 (Destructuring)](#目錄)

解構賦值允許我們從陣列或物件中提取資料，並將其賦值給變數。

```javascript
const user = {
  name: "Alice",
  age: 25,
  address: {
    city: "台北市",
    district: "信義區",
  },
};

// 基本解構
const { name, age } = user;

// 進階：多層解構 (直接提取 city)
const {
  address: { city },
} = user;

console.log(name); // Alice
console.log(city); // 台北市
```

### <a id="CH1-4-2"></a>[2. 展開運算子 (Spread Operator) ...](#目錄)

展開運算子可用於展開陣列元素或物件屬性，常用於合併或複製資料。

**物件淺拷貝 (Shallow Copy) 與屬性覆蓋：**
在 Vue 的狀態管理中，我們常需要「不可變 (Immutable)」的操作，這時展開運算子非常有用。

```javascript
const user = { name: "Alice", age: 25 };
// 建立一個新物件，複製 user 的屬性，並覆蓋 age
const updatedUser = { ...user, age: 26 };

console.log(user === updatedUser); // false (記憶體位址不同)
```

**陣列的操作應用：**

展開運算子同樣適用於陣列，常用於合併陣列或在不改變原陣列(Immutability)的情況下新增元素。

```javascript
const groupA = ["Father", "Mother"];
const groupB = ["Son", "Daughter"];

// 1. 合併陣列
const family = [...groupA, ...groupB];
// 結果：["Father", "Mother", "Son", "Daughter"]

// 2. 複製並加入新元素 (不可變操作)
const newGroup = ["Grandpa", ...groupA];
// 結果：["Grandpa", "Father", "Mother"]
```

**函式參數傳遞：**

可以將陣列展開為函式的個別參數，這在處理需要多個參數的函式時非常方便。

```javascript
const numbers = [10, 20, 99, 5];
// Math.max 接受多個數值參數，這時可用展開運算子傳入
const max = Math.max(...numbers);
console.log(max); // 99
```

**注意：展開運算子僅為「淺拷貝 (Shallow Copy)」**
若物件內包含巢狀物件（例如 `address`），展開運算子只會複製參照。修改新物件的 nest 屬性會影響原物件。

若需**深層拷貝 (Deep Copy)**，最簡單的方式是使用 `JSON` 方法：

```javascript
const user = { name: "Alice", address: { city: "台北市" } };
const deepCopiedUser = JSON.parse(JSON.stringify(user));

deepCopiedUser.address.city = "台中市";
console.log(user.address.city); // 台北市 (不受影響)
```

---

## <a id="CH1-5"></a>[1-5 常用陣列方法](#目錄)

在 Vue 應用程式中，處理列表資料是常見的需求。以下介紹幾種常用的陣列操作方法。

### <a id="CH1-5-1"></a>[1. map (映射)](#目錄)

建立一個新陣列，其內容為原陣列的每一個元素經由回呼函式運算後的結果。**注意：它不會改變原陣列。**

```javascript
const numbers = [1, 2, 3];
const doubled = numbers.map((num) => num * 2);
```

### <a id="CH1-5-2"></a>[2. filter (過濾)](#目錄)

建立一個新陣列，其中包含通過測試（回呼函式回傳 true）的所有元素。

```javascript
const scores = [50, 80, 90, 40];
const passed = scores.filter((score) => score >= 60);
```

### <a id="CH1-5-3"></a>[3. find (搜尋)](#目錄)

回傳陣列中第一個滿足測試條件的元素。**注意：找到就會停止搜尋，且只回傳單一元素，非陣列。**

```javascript
const users = [
  { id: 1, name: "A" },
  { id: 2, name: "B" },
];
const target = users.find((user) => user.id === 2);
```

### <a id="CH1-5-4"></a>[4. some (部分符合)](#目錄)

回傳布林值。只要陣列中至少有一個元素滿足條件，就回傳 `true`，否則回傳 `false`。

```javascript
const nums = [1, 3, 5, 7, 8];
const hasEven = nums.some((n) => n % 2 === 0); // true (因為有 8)
```

### <a id="CH1-5-5"></a>[5. every (完全符合)](#目錄)

回傳布林值。陣列中「所有」元素都滿足條件才回傳 `true`，否則回傳 `false`。

```javascript
const nums = [2, 4, 6, 8];
const allEven = nums.every((n) => n % 2 === 0); // true
```

### <a id="CH1-5-6"></a>[6. includes (包含)](#目錄)

回傳布林值。判斷陣列是否包含特定「值」（主要用於純值陣列，不適合物件陣列搜尋）。

```javascript
const fruits = ["Apple", "Banana"];
const hasApple = fruits.includes("Apple"); // true
```

### <a id="CH1-5-7"></a>[7. slice (切片)](#目錄)

回傳一個新陣列，包含從開始索引到結束索引（不含）的淺拷貝。**不改變原陣列。**

```javascript
const arr = ["a", "b", "c", "d"];
const sliced = arr.slice(1, 3); // ["b", "c"]
```

### <a id="CH1-5-8"></a>[8. reduce (累加/歸納)](#目錄)

功能最強大的陣列方法(稍微複雜)，能將陣列元素經由回呼函式運算，最終化為「單一值」（可能是數字、物件或陣列）。

**語法結構：**
`array.reduce((acc, curr) => { ... }, initialValue)`

- `acc` (accumulator): 累加值，即上一次回呼函式的回傳值。
- `curr` (current): 當前處理的元素。
- `initialValue`: 累加值的初始設定（選填，但建議加上）。

```javascript
const numbers = [1, 2, 3, 4];
const sum = numbers.reduce((acc, curr) => {
  return acc + curr;
}, 0);
// 初始值 0，依序加 1, 2, 3, 4 => 結果 10
```

---

## <a id="CH1-6"></a>[1-6 非同步處理（Promise / async / await）](#目錄)

現代網頁應用程式常需與後端 API 進行資料交換，此過程屬於非同步操作。

### <a id="CH1-6-1"></a>[1. 非同步概念 (Asynchronous)](#目錄)

JavaScript 是單執行緒 (Single Thread) 的語言，一次只能做一件事。

- **同步 (Synchronous)**：程式碼依序執行，若遇到耗時任務（如請求後端資料）會導致整個網頁「卡住」等待，使用者體驗極差。
- **非同步 (Asynchronous)**：將耗時任務交給瀏覽器在背景處理，程式繼續往下執行。待任務完成後，再透過回呼機制處理結果。

### <a id="CH1-6-2"></a>[2. Callback Hell (回呼地獄)](#目錄)

早期處理非同步主要依賴 **Callback (回呼函式)**。當有多個任務需依序執行時，會產生深層巢狀結構，導致程式碼難以維護與除錯。

```javascript
// 典型的 Callback Hell (波動拳)
doA(function (resultA) {
  doB(resultA, function (resultB) {
    doC(resultB, function (resultC) {
      console.log("終於完成: " + resultC);
    });
  });
});
```

### <a id="CH1-6-3"></a>[3. Promise (承諾)](#目錄)

ES6 引入 `Promise` 物件來優化非同步操作。它代表一個「未來才會知道結果」的事件，具備三種狀態：

1. **Pending (擱置中)**：初始狀態。
2. **Fulfilled (已實現)**：操作成功，呼叫 `resolve`。
3. **Rejected (已拒絕)**：操作失敗，呼叫 `reject`。

它採用鏈式調用 (.then) 解決了巢狀縮排的問題。

```javascript
const myPromise = (score) => {
  return new Promise((resolve, reject) => {
    if (score >= 60) {
      resolve("及格！");
    } else {
      reject("不及格...");
    }
  });
};

myPromise(80)
  .then((msg) => console.log(msg)) // 成功時執行
  .catch((err) => console.error(err)); // 失敗時執行
```

### <a id="CH1-6-4"></a>[4. async / await (建議使用)](#目錄)

`async / await` 是 Promise 的語法糖，能以更接近同步程式碼的方式撰寫非同步邏輯，大幅提高可讀性。

**錯誤處理 (Error Handling)：**
建議搭配 `try...catch` 區塊來捕捉錯誤。

```javascript
const getData = async () => {
  try {
    const data = await fetchData(); // 等待 Promise 解析
    console.log("資料取得成功", data);
  } catch (error) {
    // 當 Promise 被 reject 時會執行這裡
    console.error("發生錯誤", error);
  } finally {
    console.log("無論成功失敗都會執行，適合用來關閉 Loading 狀態");
  }
};
```

---

## <a id="CH1-7"></a>[1-7 模組化 (Export & Import)](#目錄)

ES6 引入了模組化機制，讓我們能將程式碼拆分成獨立的檔案，並透過 `export` 與 `import` 互相引用。這在 Vue 專案中是管理元件與函式的核心方式。

### <a id="CH1-7-1"></a>[1. 匯出 (Export)](#目錄)

**具名匯出 (Named Export)：**
可以匯出多個變數或函式。需使用 `{}` 接收。

```javascript
// math.js
export const add = (a, b) => a + b;
export const pi = 3.14;
```

**預設匯出 (Default Export)：**
每個檔案只能有一個預設匯出，通常用於匯出元件或主要功能。不需 `{}` 接收。

```javascript
// user.js
const user = { name: "Alice" };
export default user;
```

### <a id="CH1-7-2"></a>[2. 匯入 (Import)](#目錄)

**匯入具名成員：**
需使用 `{}` 且名稱需對應（或使用 `as` 重新命名）。

```javascript
import { add, pi } from "./math.js";
console.log(add(1, 2)); // 3
```

**匯入預設成員：**
不需 `{}`，且可以自訂名稱。

```javascript
import currentUser from "./user.js";
```

---

[下一章：章節 2 ｜ Vue 基礎（CDN 版本）](./Chapter2_VueBasics.md)
