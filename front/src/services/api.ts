// front/src/services/api.ts
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8084/api",
});

// Interceptor para adjuntar el token a cada request
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
