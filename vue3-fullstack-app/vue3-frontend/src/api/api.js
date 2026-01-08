import axios from "axios";
import { createToastInterface } from "vue-toastification";

// 建立 toast 介面 (用於非組件環境)
const toast = createToastInterface();

// 建立 axios 實體
const instance = axios.create({
  baseURL: import.meta.env.VITE_API_URL || "/api", // 預設 API 路徑
  timeout: 10000, // 請求超時時間 (10秒)
  headers: {
    "Content-Type": "application/json",
  },
});

// Request Interceptor (請求攔截器)
instance.interceptors.request.use(
  (config) => {
    // 在發送請求前做些什麼，例如加入 Token
    const token = localStorage.getItem("token"); // 假設 Token 存在 localStorage
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    // 對請求錯誤做些什麼
    return Promise.reject(error);
  }
);

// Response Interceptor (回應攔截器)
instance.interceptors.response.use(
  (response) => {
    // 對回應資料做點什麼，例如只回傳 data
    return response;
  },
  (error) => {
    // 對回應錯誤做點什麼
    if (!error.response) {
      toast.error("網路錯誤或伺服器無回應");
      return Promise.reject(error);
    }

    switch (error.response.status) {
      case 401:
        toast.error("未授權，請重新登入");
        break;
      case 403:
        toast.error("權限不足");
        break;
      case 404:
        toast.error("找不到資源");
        break;
      case 500:
        toast.error("伺服器錯誤");
        break;
      default:
        toast.error("發生錯誤: " + error.message);
    }

    return Promise.reject(error);
  }
);

export default instance;
