import http from './http';

export const getProducts = (page, size) => {
  return http.get('/products', {
    params: {
      page,
      size,
    },
  });
};

export const createProduct = (data) => {
  return http.post('/products', data);
};

export const updateProduct = (id, data) => {
  return http.put(`/products/${id}`, data);
};

export const deleteProduct = (id) => {
  return http.delete(`/products/${id}`);
};
