// 引入 axios，這是最常用的 HTTP 請求套件
import axios from "axios";
// 引入 toast 介面，讓我們可以在 JS 檔案中使用通知功能
import { createToastInterface } from "vue-toastification";

// 建立 toast 實例 (因為這裡不是 Vue 組件，無法使用 useToast()，所以使用 createToastInterface)
const toast = createToastInterface();

// 建立 axios 實體 (instance)
// 這樣做的好處是可以設定這個實體的預設值，而不會影響到全域的 axios
const instance = axios.create({
  // 設定 API 的基礎路徑 (Base URL)
  // import.meta.env.VITE_API_URL 來自 .env 檔，如果沒設定則預設為 http://localhost:8080/api
  baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080/api",
  // 設定請求超時時間為 10 秒，超過時間會自動取消請求
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
});

// Request Interceptor (請求攔截器)
// 每當發送請求前，axios 會先執行這個函式
instance.interceptors.request.use(
  (config) => {
    // 我們可以利用攔截器，自動在每個請求的 Header 加入 JWT Token
    const token = localStorage.getItem("token"); // 從 localStorage 取得 Token
    if (token) {
      // 如果有 Token，就加入 Authorization Header
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    // 如果請求設定有誤，會執行這裡
    return Promise.reject(error);
  }
);

// Response Interceptor (回應攔截器)
// 每當收到後端回應後，axios 會先執行這個函式
instance.interceptors.response.use(
  (response) => {
    // 請求成功 (HTTP 狀態碼 2xx)
    // 我們可以直接回傳 response，或者只回傳 response.data 讓前端程式碼更簡潔
    return response;
  },
  (error) => {
    // 請求失敗 (HTTP 狀態碼非 2xx 或網路錯誤)
    
    // 如果 error.response 不存在，表示根本沒收到回應 (例如網路斷線或伺服器掛掉)
    if (!error.response) {
      toast.error("網路錯誤或伺服器無回應");
      return Promise.reject(error);
    }

    // 根據 HTTP 狀態碼顯示不同的錯誤訊息
    switch (error.response.status) {
      case 401:
        // 401 Unauthorized: 通常表示 Token 過期或無效
        toast.error("未授權，請重新登入");
        // 這裡可以考慮自動導向回登入頁面
        break;
      case 403:
        // 403 Forbidden: 有登入但權限不足
        toast.error("權限不足，您無法執行此操作");
        break;
      case 404:
        // 404 Not Found: 請求的資源不存在
        toast.error("找不到資源");
        break;
      case 500:
        // 500 Internal Server Error: 後端程式發生錯誤
        toast.error("伺服器發生錯誤");
        break;
      default:
        // 其他錯誤
        toast.error("發生錯誤: " + (error.response.data.message || error.message));
    }

    return Promise.reject(error);
  }
);

export default instance;
