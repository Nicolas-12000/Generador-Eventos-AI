// Configuración centralizada de la API
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:3001';

// Simulador de respuestas para desarrollo
const mockResponses = {
  "hola": "¡Hola! Cuéntame sobre tu evento (nombre, fecha, lugar).",
  "evento de tecnología": "Interesante. ¿Qué fecha tienes planeada?",
  "15 de noviembre": "Perfecto. ¿Dónde será el evento?"
};

export const apiService = {
  async sendMessage(message) {
    // Modo desarrollo: usar mocks
    if (!import.meta.env.VITE_API_BASE_URL) {
      await new Promise(resolve => setTimeout(resolve, 800)); // Simular delay
      const reply = mockResponses[message.toLowerCase()] || 
        "Entendido. ¿Algo más que quieras agregar sobre el evento?";
      
      return {
        message: reply,
        action: message.includes("tecnología") ? 'generate' : null
      };
    }

    // Modo producción: conectar con backend real
    try {
      const response = await fetch(`${API_BASE_URL}/chat`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ message })
      });
      return await response.json();
    } catch (error) {
      console.error('API Error:', error);
      return {
        message: "Error de conexión. Intenta nuevamente.",
        action: null
      };
    }
  },

  async generateLanding(eventData) {
    // Lógica similar para generación
  }
};