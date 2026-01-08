import axios from './index';

export const loginAPI = (data) => {
  return axios.post('/auth/login', data);
};
