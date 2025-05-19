import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

export const sendChatMessage = (message) => {
  return api.post('/chat', { text: message });
};

export const generateLanding = (eventData) => {
  return api.post('/generate', eventData);
};