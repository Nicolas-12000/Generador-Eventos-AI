import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api', // Asegúrate de que coincida con tu backend
  headers: {
    'Content-Type': 'application/json',
  },
});

// Enviar mensaje al chatbot
export const sendChatMessage = (message) => {
  return api.post('/chat', { 
    text: message,
    model: "gpt-4", // Modelo de Azure OpenAI a usar
    temperature: 0.7, // Creatividad de las respuestas
  });
};

// Generar la landing page final
export const generateLandingPage = (eventData) => {
  return api.post('/generate-landing', eventData);
};