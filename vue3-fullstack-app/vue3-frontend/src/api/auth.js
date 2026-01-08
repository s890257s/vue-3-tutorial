import http from './http';

export const loginAPI = (data) => {
  return http.post('/auth/login', data);
};
