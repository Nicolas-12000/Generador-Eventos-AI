// src/pages/ChatPage.jsx
import { useState } from 'react';
import ChatBubble from '../components/ChatBubble';
import { apiService } from '../services/api';

export default function ChatPage() {
  const [state, setState] = useState({
    messages: [
      { text: "¡Hola! Soy tu asistente para crear landing pages. Describe tu evento.", isUser: false }
    ],
    input: '',
    isLoading: false,
    eventData: {} // Almacena datos del evento para la generación
  });

  const handleSend = async () => {
    if (!state.input.trim() || state.isLoading) return;

    // 1. Actualizar UI con mensaje del usuario
    setState(prev => ({
      ...prev,
      messages: [...prev.messages, { text: prev.input, isUser: true }],
      input: '',
      isLoading: true
    }));

    // 2. Enviar mensaje al backend/mock
    const response = await apiService.sendMessage(state.input);

    // 3. Procesar respuesta
    setState(prev => ({
      ...prev,
      messages: [...prev.messages, {
        text: response.message,
        isUser: false,
        action: response.action // 'generate' o null
      }],
      isLoading: false,
      // Actualizar datos del evento si es relevante
      eventData: response.metadata ? { ...prev.eventData, ...response.metadata } : prev.eventData
    }));
  };

  const handleGenerate = async () => {
    setState(prev => ({ ...prev, isLoading: true }));
    
    // Aquí implementaríamos apiService.generateLanding()
    // Por ahora simulamos la generación
    await new Promise(resolve => setTimeout(resolve, 1500));
    
    setState(prev => ({
      ...prev,
      messages: [...prev.messages, {
        text: "Landing page generada con éxito!",
        isUser: false,
        isLink: true,
        link: "/preview/123" // Ruta para previsualización
      }],
      isLoading: false
    }));
  };

  return (
    <div className="flex flex-col h-screen bg-gray-50">
      {/* Cabecera */}
      <header className="bg-blue-600 text-white p-4 shadow-md">
        <h1 className="text-xl font-bold">Asistente para Eventos</h1>
      </header>

      {/* Área de mensajes */}
      <div className="flex-1 p-4 overflow-y-auto">
        {state.messages.map((msg, i) => (
          <div key={i} className="mb-4">
            <ChatBubble 
              text={msg.text} 
              isUser={msg.isUser} 
              isLoading={msg.isLoading}
            />
            {msg.action === 'generate' && (
              <button
                onClick={handleGenerate}
                className="mt-2 bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600"
                disabled={state.isLoading}
              >
                Generar Landing Page
              </button>
            )}
            {msg.isLink && (
              <a 
                href={msg.link} 
                className="mt-2 inline-block bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
              >
                Ver Previsualización
              </a>
            )}
          </div>
        ))}
        {state.isLoading && !state.messages.some(m => m.isLoading) && (
          <ChatBubble 
            text="Procesando..."
            isUser={false}
            isLoading={true}
          />
        )}
      </div>

      {/* Input de mensaje */}
      <div className="p-4 border-t bg-white">
        <div className="flex gap-2">
          <input
            type="text"
            value={state.input}
            onChange={(e) => setState(prev => ({ ...prev, input: e.target.value }))}
            onKeyPress={(e) => e.key === 'Enter' && handleSend()}
            className="flex-1 border p-2 rounded focus:ring-2 focus:ring-blue-500"
            placeholder="Describe tu evento..."
            disabled={state.isLoading}
          />
          <button
            onClick={handleSend}
            disabled={state.isLoading}
            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 disabled:opacity-50"
          >
            {state.isLoading ? '...' : 'Enviar'}
          </button>
        </div>
      </div>
    </div>
  );
}