import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:4120',
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token') || localStorage.getItem('admin_token');
    // Chỉ ép Bearer token nếu request chưa tự định nghĩa header Authorization (ví dụ lúc gọi /api/token)
    if (token && !config.headers.Authorization) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
