import axios from "axios";

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
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 未授權，可能需要重新登入
          console.error("未授權，請重新登入");
          // 可以在這裡執行登出或導向登入頁的操作
          break;
        case 403:
          console.error("權限不足");
          break;
        case 404:
          console.error("找不到資源");
          break;
        case 500:
          console.error("伺服器錯誤");
          break;
        default:
          console.error("發生錯誤", error.message);
      }
    } else {
      console.error("網路錯誤或伺服器無回應", error.message);
    }
    return Promise.reject(error);
  }
);

export default instance;
