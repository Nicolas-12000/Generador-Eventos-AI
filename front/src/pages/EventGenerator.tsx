import { useState } from "react";

interface EventData {
  id: string;
  title: string;
  description: string;
  image: string;
  url: string;
  date: string;
  location: string;
  speakers: string;
  agenda: string;
  category: string;
}

export default function EventGenerator() {
  const [prompt, setPrompt] = useState("");
  const [events, setEvents] = useState<EventData[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [showQR, setShowQR] = useState<string | null>(null);

  const BACKEND_URL = "http://localhost:8084";

  const generateEventWithAI = async (userPrompt: string) => {
    setLoading(true);
    setError(null);

    try {
      const response = await fetch(`${BACKEND_URL}/api/generate-event`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ 
          prompt: userPrompt 
        })
      });

      if (!response.ok) {
        throw new Error(`Error ${response.status}: ${response.statusText}`);
      }

      const aiGeneratedEvent = await response.json();
      
      const id = crypto.randomUUID();
      const imageKeywords = aiGeneratedEvent.category || userPrompt;
      const image = `https://source.unsplash.com/800x400/?${encodeURIComponent(imageKeywords)},event`;
      const url = `${window.location.origin}/evento/${id}`;
      const newEvent: EventData = {
        id,
        title: aiGeneratedEvent.title || `Evento: ${userPrompt}`,
        description: aiGeneratedEvent.description || "Evento generado por IA",
        image,
        url,
        date: aiGeneratedEvent.date || new Date().toLocaleDateString('es-ES'),
        location: aiGeneratedEvent.location || "Ubicación por definir",
        speakers: aiGeneratedEvent.speakers || "Ponentes por confirmar",
        agenda: aiGeneratedEvent.agenda || "Agenda por definir",
        category: aiGeneratedEvent.category || "general"
      };

      setEvents([newEvent, ...events]);
      setPrompt("");

    } catch (error) {
      console.error('Error generando evento:', error);
      setError(error instanceof Error ? error.message : 'Error desconocido');
      
      createFallbackEvent(userPrompt);
    } finally {
      setLoading(false);
    }
  };

  const createFallbackEvent = (userPrompt: string) => {
    const id = crypto.randomUUID();
    const title = `Evento: ${userPrompt}`;
    const image = `https://source.unsplash.com/800x400/?event,${encodeURIComponent(userPrompt)}`;
    const url = `${window.location.origin}/evento/${id}`;

    const fallbackEvent: EventData = {
      id,
      title,
      description: `Evento generado sobre: ${userPrompt}`,
      image,
      url,
      date: new Date().toLocaleDateString('es-ES'),
      location: "Auditorio Central - Ciudad Universitaria",
      speakers: "Especialistas en el tema",
      agenda: "9:00 - Apertura\n10:00 - Conferencias principales\n13:00 - Almuerzo\n15:00 - Talleres\n17:00 - Cierre",
      category: "general"
    };

    setEvents([fallbackEvent, ...events]);
    setPrompt("");
  };

  const handleGenerate = () => {
    if (!prompt.trim()) {
      setError("Por favor ingresa una descripción para el evento");
      return;
    }

    generateEventWithAI(prompt);
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && !loading) {
      handleGenerate();
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-900 via-blue-900 to-indigo-900 relative overflow-hidden">
      {/* Animated background elements */}
      <div className="absolute inset-0 overflow-hidden">
        <div className="absolute top-20 left-20 w-72 h-72 bg-purple-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse"></div>
        <div className="absolute top-40 right-20 w-96 h-96 bg-blue-500 rounded-full mix-blend-multiply filter blur-xl opacity-20" style={{animationDelay: '1s'}}></div>
        <div className="absolute bottom-20 left-40 w-80 h-80 bg-indigo-500 rounded-full mix-blend-multiply filter blur-xl opacity-20" style={{animationDelay: '2s'}}></div>
      </div>

      <div className="relative z-10 p-4 md:p-8">
        <div className="max-w-7xl mx-auto">
          {/* Header */}
          <div className="text-center mb-12">
            <div className="inline-flex items-center justify-center w-20 h-20 bg-gradient-to-r from-purple-400 to-pink-400 rounded-full mb-6 shadow-2xl">
              <span className="text-3xl">✨</span>
            </div>
            <h1 className="text-5xl md:text-6xl font-bold text-white mb-4 bg-gradient-to-r from-purple-400 via-pink-400 to-blue-400 bg-clip-text text-transparent">
              Generador de Eventos AI
            </h1>
            <p className="text-xl text-gray-300 max-w-2xl mx-auto leading-relaxed">
              Transforma tus ideas en eventos extraordinarios con el poder de la inteligencia artificial
            </p>
          </div>

          <div className="grid lg:grid-cols-2 gap-8">
            {/* Input Panel */}
            <div className="space-y-6">
              <div className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-3xl p-8 shadow-2xl">
                <div className="space-y-6">
                  <div className="text-center">
                    <div className="inline-flex items-center justify-center w-12 h-12 bg-gradient-to-r from-green-400 to-blue-500 rounded-full mb-4">
                      <span className="text-xl">🤖</span>
                    </div>
                    <h2 className="text-2xl font-bold text-white mb-2">
                      Describe tu evento ideal
                    </h2>
                    <p className="text-gray-300">
                      Deja que Gemini AI genere todos los detalles automáticamente
                    </p>
                  </div>
                  
                  {error && (
                    <div className="bg-red-500/20 border border-red-500/30 rounded-xl p-4 backdrop-blur-sm">
                      <p className="text-red-200 text-sm">{error}</p>
                    </div>
                  )}

                  <div className="space-y-4">
                    <div className="relative">
                      <textarea
                        placeholder="Ej: Conferencia de inteligencia artificial para estudiantes de ingeniería con talleres prácticos y networking..."
                        value={prompt}
                        onChange={(e) => {
                          setPrompt(e.target.value);
                          setError(null);
                        }}
                        onKeyPress={handleKeyPress}
                        className="w-full px-6 py-4 bg-white/5 border border-white/20 rounded-2xl text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-purple-400 focus:border-transparent resize-none backdrop-blur-sm"
                        rows={4}
                        disabled={loading}
                      />
                    </div>
                    <button 
                      onClick={handleGenerate} 
                      className="w-full bg-gradient-to-r from-purple-500 to-pink-500 hover:from-purple-600 hover:to-pink-600 text-white font-semibold py-4 px-8 rounded-2xl transition-all duration-300 transform hover:scale-105 shadow-xl disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none"
                      disabled={!prompt.trim() || loading}
                    >
                      {loading ? (
                        <div className="flex items-center justify-center">
                          <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-white mr-3"></div>
                          Generando con IA...
                        </div>
                      ) : (
                        <div className="flex items-center justify-center">
                          <span className="mr-2">🚀</span>
                          Generar evento con IA
                        </div>
                      )}
                    </button>
                  </div>

                  <div className="bg-blue-500/20 border border-blue-500/30 rounded-xl p-4 backdrop-blur-sm">
                    <h4 className="font-semibold text-blue-200 mb-3 flex items-center">
                      <span className="mr-2">💡</span>
                      Consejos para mejores resultados
                    </h4>
                    <ul className="text-sm text-blue-100 space-y-2">
                      <li className="flex items-start">
                        <span className="mr-2 text-blue-300">•</span>
                        Se específico: "Taller de React para principiantes"
                      </li>
                      <li className="flex items-start">
                        <span className="mr-2 text-blue-300">•</span>
                        Menciona el público: "para estudiantes", "para profesionales"
                      </li>
                      <li className="flex items-start">
                        <span className="mr-2 text-blue-300">•</span>
                        Incluye el contexto: "en universidad", "empresarial"
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>

            {/* Events Panel */}
            <div className="space-y-6">
              <div className="flex items-center justify-between">
                <h3 className="text-3xl font-bold text-white flex items-center">
                  <span className="mr-3">🎪</span>
                  Eventos generados ({events.length})
                </h3>
              </div>
              
              <div className="space-y-6 max-h-[80vh] overflow-y-auto" style={{
                scrollbarWidth: 'thin',
                scrollbarColor: 'rgba(255, 255, 255, 0.3) rgba(255, 255, 255, 0.1)'
              }}>
                {events.length === 0 && !loading && (
                  <div className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-3xl p-12 text-center shadow-2xl">
                    <div className="text-8xl mb-6 animate-bounce">🌟</div>
                    <h4 className="text-2xl font-bold text-white mb-4">
                      ¡Tu próximo evento increíble te espera!
                    </h4>
                    <p className="text-gray-300 text-lg">
                      Describe tu evento ideal y deja que la IA haga la magia
                    </p>
                  </div>
                )}

                {events.map((event, index) => (
                  <div 
                    key={event.id} 
                    className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-3xl overflow-hidden shadow-2xl transform hover:scale-105 transition-all duration-300 opacity-0 animate-pulse"
                    style={{
                      animationDelay: `${index * 0.1}s`,
                      animation: `fadeInUp 0.6s ease-out ${index * 0.1}s forwards`
                    }}
                  >
                    <div className="relative">
                      <img
                        src={event.image}
                        alt={event.title}
                        className="w-full h-48 object-cover"
                        onError={(e) => {
                          const target = e.target as HTMLImageElement;
                          target.src = 'https://via.placeholder.com/800x400/6366f1/ffffff?text=🎉+Evento+Increíble';
                        }}
                      />
                      <div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent"></div>
                      <div className="absolute top-4 right-4">
                        <span className="bg-purple-500/80 text-white px-3 py-1 rounded-full text-sm font-semibold backdrop-blur-sm">
                          ✨ Generado por IA
                        </span>
                      </div>
                    </div>
                    
                    <div className="p-6 space-y-4">
                      <div>
                        <h4 className="font-bold text-2xl text-white mb-3 leading-tight">
                          {event.title}
                        </h4>
                        <p className="text-gray-300 leading-relaxed">
                          {event.description}
                        </p>
                      </div>
                      
                      <div className="grid grid-cols-1 gap-3">
                        <div className="flex items-center gap-3 text-gray-200">
                          <div className="flex items-center justify-center w-8 h-8 bg-blue-500/20 rounded-full">
                            <span>📅</span>
                          </div>
                          <span><strong>Fecha:</strong> {event.date}</span>
                        </div>
                        <div className="flex items-center gap-3 text-gray-200">
                          <div className="flex items-center justify-center w-8 h-8 bg-green-500/20 rounded-full">
                            <span>📍</span>
                          </div>
                          <span><strong>Lugar:</strong> {event.location}</span>
                        </div>
                        <div className="flex items-center gap-3 text-gray-200">
                          <div className="flex items-center justify-center w-8 h-8 bg-yellow-500/20 rounded-full">
                            <span>🎙️</span>
                          </div>
                          <span><strong>Ponentes:</strong> {event.speakers}</span>
                        </div>
                      </div>

                      <div className="bg-white/5 rounded-2xl p-4 border border-white/10">
                        <p className="font-semibold text-white mb-3 flex items-center">
                          <span className="mr-2">🗓️</span>
                          Programa del día
                        </p>
                        <pre className="text-sm text-gray-300 whitespace-pre-wrap font-mono leading-relaxed">
                          {event.agenda}
                        </pre>
                      </div>
                      
                      <div className="flex gap-3 pt-4">
                        <button className="flex-1 bg-gradient-to-r from-green-500 to-blue-500 hover:from-green-600 hover:to-blue-600 text-white font-semibold py-3 px-6 rounded-xl transition-all duration-300 transform hover:scale-105 shadow-lg">
                          🎫 Registrarse
                        </button>
                        <button 
                          onClick={() => setShowQR(showQR === event.id ? null : event.id)}
                          className="bg-white/10 hover:bg-white/20 border border-white/20 text-white font-semibold py-3 px-6 rounded-xl transition-all duration-300 backdrop-blur-sm"
                        >
                          📱 QR
                        </button>
                      </div>

                      {showQR === event.id && (
                        <div className="bg-white rounded-2xl p-6 text-center transform opacity-0" style={{animation: 'fadeIn 0.3s ease-out forwards'}}>
                          <h5 className="font-bold text-gray-800 mb-4">
                            📱 Código QR del Evento
                          </h5>
                          <div className="flex justify-center mb-4">
                            <div className="w-48 h-48 bg-gray-200 rounded-lg flex items-center justify-center">
                              <span className="text-4xl">📱</span>
                            </div>
                          </div>
                          <p className="text-gray-600 text-sm mb-3">
                            Escanea este código para acceder al evento
                          </p>
                          <a 
                            href={event.url} 
                            target="_blank" 
                            rel="noopener noreferrer" 
                            className="text-blue-600 hover:text-blue-800 underline font-semibold"
                          >
                            🌐 Ver página del evento
                          </a>
                        </div>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>

      <style dangerouslySetInnerHTML={{
        __html: `
          @keyframes fadeInUp {
            from {
              opacity: 0;
              transform: translateY(30px);
            }
            to {
              opacity: 1;
              transform: translateY(0);
            }
          }
          
          @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
          }
          
          .animate-pulse {
            animation: pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite;
          }
          
          @keyframes pulse {
            0%, 100% {
              opacity: 1;
            }
            50% {
              opacity: .5;
            }
          }
        `
      }} />
    </div>
  );
}