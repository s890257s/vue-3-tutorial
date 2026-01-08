import { defineStore } from 'pinia';
import { loginAPI } from '@/api/auth';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    user: JSON.parse(localStorage.getItem('user')) || null,
  }),
  getters: {
    isAuthenticated: (state) => !!state.token,
    isAdmin: (state) => state.user?.isAdmin || false,
  },
  actions: {
    async login(loginData) {
      try {
        const response = await loginAPI(loginData);
        // 假設後端回傳 LoginResponse 包含 token 與使用者資訊
        const { token, ...userData } = response.data;

        this.token = token;
        this.user = userData;

        // 持久化
        localStorage.setItem('token', token);
        localStorage.setItem('user', JSON.stringify(userData));
        
        return response;
      } catch (error) {
        throw error;
      }
    },
    logout() {
      this.token = '';
      this.user = null;
      localStorage.removeItem('token');
      localStorage.removeItem('user');
    }
  }
});
