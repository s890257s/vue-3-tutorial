import http from './http';

export const getMembers = (page, size) => {
  return http.get('/members', {
    params: {
      page,
      size,
    },
  });
};

export const createMember = (data) => {
  return http.post('/members', data);
};

export const updateMember = (id, data) => {
  return http.put(`/members/${id}`, data);
};

export const deleteMember = (id) => {
  return http.delete(`/members/${id}`);
};
