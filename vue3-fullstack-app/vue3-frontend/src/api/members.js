import http from './http';

export const getMembers = (page, size) => {
  return http.get('/members', {
    params: {
      page,
      size,
    },
  });
};
