import { useState, useEffect } from 'react';
import { sendChatMessage } from '../services/api';
import ChatBubble from '../components/ChatBubble';

export default function ChatPage() {
  const [messages, setMessages] = useState([
    { 
      text: "¡Hola! Soy tu asistente para crear landing pages de eventos. Por favor, descríbeme tu evento (nombre, fecha, lugar, etc.)", 
      isUser: false 
    }
  ]);
  const [input, setInput] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const handleSend = async () => {
    if (!input.trim() || isLoading) return;
    
    // Mensaje del usuario
    const userMessage = { text: input, isUser: true };
    setMessages(prev => [...prev, userMessage]);
    setInput('');
    setIsLoading(true);

    try {
      // Llamada al backend con Azure OpenAI
      const response = await sendChatMessage(input);
      
      // Respuesta de la IA
      setMessages(prev => [...prev, { 
        text: response.data.reply, // Ajusta según la estructura de tu backend
        isUser: false 
      }]);
      
      // Si la IA detecta que el evento está completo, sugerir generación
      if (response.data.isEventComplete) {
        setMessages(prev => [...prev, {
          text: "¿Quieres generar la landing page ahora?",
          isUser: false,
          isActionable: true // Flag para mostrar botón de acción
        }]);
      }
    } catch (error) {
      setMessages(prev => [...prev, { 
        text: "⚠️ Error al conectar con el servidor", 
        isUser: false 
      }]);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex flex-col h-screen bg-gray-50">
      {/* Cabecera */}
      <header className="bg-event-primary text-white p-4 shadow-md">
        <h1 className="text-xl font-bold">Asistente para Eventos</h1>
      </header>

      {/* Área de mensajes */}
      <div className="flex-1 p-4 overflow-y-auto">
        {messages.map((msg, i) => (
          <div key={i} className="mb-4">
            <ChatBubble 
              text={msg.text} 
              isUser={msg.isUser} 
            />
            {msg.isActionable && (
              <button 
                className="mt-2 bg-event-secondary text-white px-4 py-2 rounded hover:bg-green-600 transition-colors"
                onClick={() => console.log("Generar landing page")}
              >
                Generar Landing Page
              </button>
            )}
          </div>
        ))}
        {isLoading && (
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
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyPress={(e) => e.key === 'Enter' && handleSend()}
            className="flex-1 border p-2 rounded focus:outline-none focus:ring-2 focus:ring-event-primary"
            placeholder="Escribe aquí..."
            disabled={isLoading}
          />
          <button
            onClick={handleSend}
            disabled={isLoading}
            className="bg-event-primary text-white px-4 py-2 rounded hover:bg-indigo-600 disabled:opacity-50 transition-colors"
          >
            {isLoading ? 'Enviando...' : 'Enviar'}
          </button>
        </div>
      </div>
    </div>
  );
}