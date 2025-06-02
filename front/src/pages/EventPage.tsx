import { useState, useEffect } from "react";

interface EventData {
  id: string;
  title: string;
  description: string;
  image: string;
  date: string;
  location: string;
  speakers: string;
  agenda: string;
  category: string;
}

export default function EventPage() {
  // Simular el ID del evento - en tu app real usarías useParams()
  const id = "demo-event-123";
  const [event, setEvent] = useState<EventData | null>(null);
  const [loading, setLoading] = useState(true);
  const [showQR, setShowQR] = useState(false);
  const [registered, setRegistered] = useState(false);

  useEffect(() => {
    // Simular carga de datos del evento
    // En una implementación real, aquí harías fetch al backend
    setTimeout(() => {
      const mockEvent: EventData = {
        id: id || 'demo',
        title: "Conferencia de Inteligencia Artificial 2025",
        description: "Una experiencia inmersiva donde exploraremos las últimas tendencias en IA, machine learning y tecnologías emergentes. Conecta con expertos de la industria y descubre cómo la IA está transformando el mundo.",
        image: `https://source.unsplash.com/1200x600/?artificial,intelligence,technology`,
        date: new Date().toLocaleDateString('es-ES', { 
          weekday: 'long', 
          year: 'numeric', 
          month: 'long', 
          day: 'numeric' 
        }),
        location: "Centro de Convenciones TechHub - Auditorio Principal",
        speakers: "Dr. Ana García (Google AI), Carlos Rodríguez (Microsoft), Sofia Chen (OpenAI)",
        agenda: "9:00 - Registro y bienvenida\n10:00 - Keynote: El futuro de la IA\n11:30 - Coffee break\n12:00 - Panel: IA en la industria\n13:30 - Almuerzo y networking\n15:00 - Talleres prácticos\n16:30 - Demos innovadoras\n17:30 - Cierre y networking",
        category: "technology"
      };
      setEvent(mockEvent);
      setLoading(false);
    }, 1500);
  }, [id]);

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-purple-900 via-blue-900 to-indigo-900 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-16 w-16 border-4 border-purple-400 border-t-transparent mb-4 mx-auto"></div>
          <p className="text-white text-xl">Cargando evento...</p>
        </div>
      </div>
    );
  }

  if (!event) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-purple-900 via-blue-900 to-indigo-900 flex items-center justify-center">
        <div className="text-center text-white">
          <div className="text-6xl mb-4">😕</div>
          <h1 className="text-3xl font-bold mb-4">Evento no encontrado</h1>
          <p className="text-gray-300">El evento que buscas no existe o ha sido eliminado.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-900 via-blue-900 to-indigo-900 relative overflow-hidden">
      {/* Animated background elements */}
      <div className="absolute inset-0 overflow-hidden">
        <div className="absolute top-20 left-20 w-72 h-72 bg-purple-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse"></div>
        <div className="absolute top-40 right-20 w-96 h-96 bg-blue-500 rounded-full mix-blend-multiply filter blur-xl opacity-20" style={{animationDelay: '1s'}}></div>
        <div className="absolute bottom-20 left-40 w-80 h-80 bg-indigo-500 rounded-full mix-blend-multiply filter blur-xl opacity-20" style={{animationDelay: '2s'}}></div>
      </div>

      <div className="relative z-10 p-4 md:p-8">
        <div className="max-w-5xl mx-auto">
          {/* Back button */}
          <div className="mb-8">
            <button 
              onClick={() => window.history.back()}
              className="inline-flex items-center gap-2 text-white/80 hover:text-white transition-colors duration-300"
            >
              <span className="text-xl">←</span>
              Volver al generador
            </button>
          </div>

          {/* Hero Section */}
          <div className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-3xl overflow-hidden shadow-2xl mb-8">
            <div className="relative">
              <img
                src={event.image}
                alt={event.title}
                className="w-full h-80 md:h-96 object-cover"
                onError={(e) => {
                  const target = e.target as HTMLImageElement;
                  target.src = 'https://via.placeholder.com/1200x600/6366f1/ffffff?text=🎉+Evento+Increíble';
                }}
              />
              <div className="absolute inset-0 bg-gradient-to-t from-black/60 via-black/20 to-transparent"></div>
              <div className="absolute top-6 right-6">
                <span className="bg-purple-500/90 text-white px-4 py-2 rounded-full text-sm font-semibold backdrop-blur-sm">
                  ✨ Generado por IA
                </span>
              </div>
              <div className="absolute bottom-6 left-6 right-6">
                <h1 className="text-4xl md:text-5xl font-bold text-white mb-4 leading-tight">
                  {event.title}
                </h1>
                <p className="text-xl text-gray-200 leading-relaxed">
                  {event.description}
                </p>
              </div>
            </div>
          </div>

          <div className="grid lg:grid-cols-3 gap-8">
            {/* Event Details */}
            <div className="lg:col-span-2 space-y-8">
              {/* Info Cards */}
              <div className="grid md:grid-cols-2 gap-6">
                <div className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-3xl p-6 shadow-xl">
                  <div className="flex items-center gap-4 mb-4">
                    <div className="flex items-center justify-center w-12 h-12 bg-blue-500/20 rounded-full">
                      <span className="text-2xl">📅</span>
                    </div>
                    <div>
                      <h3 className="text-white font-bold text-lg">Fecha</h3>
                      <p className="text-gray-300">{event.date}</p>
                    </div>
                  </div>
                </div>

                <div className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-3xl p-6 shadow-xl">
                  <div className="flex items-center gap-4 mb-4">
                    <div className="flex items-center justify-center w-12 h-12 bg-green-500/20 rounded-full">
                      <span className="text-2xl">📍</span>
                    </div>
                    <div>
                      <h3 className="text-white font-bold text-lg">Ubicación</h3>
                      <p className="text-gray-300">{event.location}</p>
                    </div>
                  </div>
                </div>
              </div>

              {/* Speakers */}
              <div className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-3xl p-8 shadow-xl">
                <div className="flex items-center gap-4 mb-6">
                  <div className="flex items-center justify-center w-12 h-12 bg-yellow-500/20 rounded-full">
                    <span className="text-2xl">🎙️</span>
                  </div>
                  <h3 className="text-white font-bold text-2xl">Ponentes</h3>
                </div>
                <p className="text-gray-300 text-lg leading-relaxed">{event.speakers}</p>
              </div>

              {/* Agenda */}
              <div className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-3xl p-8 shadow-xl">
                <div className="flex items-center gap-4 mb-6">
                  <div className="flex items-center justify-center w-12 h-12 bg-purple-500/20 rounded-full">
                    <span className="text-2xl">🗓️</span>
                  </div>
                  <h3 className="text-white font-bold text-2xl">Programa del día</h3>
                </div>
                <div className="bg-white/5 rounded-2xl p-6 border border-white/10">
                  <pre className="text-gray-300 whitespace-pre-wrap font-mono leading-relaxed">
                    {event.agenda}
                  </pre>
                </div>
              </div>
            </div>

            {/* Registration Panel */}
            <div className="space-y-6">
              <div className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-3xl p-8 shadow-xl sticky top-8">
                <div className="text-center mb-8">
                  <div className="inline-flex items-center justify-center w-16 h-16 bg-gradient-to-r from-green-400 to-blue-500 rounded-full mb-4">
                    <span className="text-3xl">🎫</span>
                  </div>
                  <h3 className="text-2xl font-bold text-white mb-2">
                    {registered ? '¡Ya estás registrado!' : 'Únete al evento'}
                  </h3>
                  <p className="text-gray-300">
                    {registered ? 'Te esperamos en el evento' : 'Asegura tu lugar ahora'}
                  </p>
                </div>

                {!registered ? (
                  <div className="space-y-4">
                    <button 
                      onClick={() => setRegistered(true)}
                      className="w-full bg-gradient-to-r from-green-500 to-blue-500 hover:from-green-600 hover:to-blue-600 text-white font-semibold py-4 px-6 rounded-2xl transition-all duration-300 transform hover:scale-105 shadow-xl"
                    >
                      <div className="flex items-center justify-center">
                        <span className="mr-2">🚀</span>
                        Registrarse Gratis
                      </div>
                    </button>
                    
                    <button 
                      onClick={() => setShowQR(!showQR)}
                      className="w-full bg-white/10 hover:bg-white/20 border border-white/20 text-white font-semibold py-4 px-6 rounded-2xl transition-all duration-300 backdrop-blur-sm"
                    >
                      📱 Mostrar código QR
                    </button>
                  </div>
                ) : (
                  <div className="space-y-4">
                    <div className="bg-green-500/20 border border-green-500/30 rounded-xl p-4 text-center">
                      <div className="text-4xl mb-2">✅</div>
                      <p className="text-green-200 font-semibold">¡Registro exitoso!</p>
                      <p className="text-green-300 text-sm mt-2">
                        Recibirás un email con todos los detalles
                      </p>
                    </div>
                    
                    <button 
                      onClick={() => setShowQR(!showQR)}
                      className="w-full bg-white/10 hover:bg-white/20 border border-white/20 text-white font-semibold py-4 px-6 rounded-2xl transition-all duration-300 backdrop-blur-sm"
                    >
                      📱 {showQR ? 'Ocultar' : 'Mostrar'} código QR
                    </button>
                  </div>
                )}

                {showQR && (
                  <div className="mt-6 bg-white rounded-2xl p-6 text-center transform animate-fadeIn">
                    <h5 className="font-bold text-gray-800 mb-4">
                      📱 Código QR del Evento
                    </h5>
                    <div className="flex justify-center mb-4">
                      <div className="w-40 h-40 bg-gray-200 rounded-lg flex items-center justify-center border-4 border-gray-300">
                        <div className="text-center">
                          <div className="text-3xl mb-2">📱</div>
                          <div className="text-xs text-gray-600 font-mono">
                            QR CODE
                          </div>
                        </div>
                      </div>
                    </div>
                    <p className="text-gray-600 text-sm mb-3">
                      Escanea para acceder rápidamente
                    </p>
                    <div className="text-xs text-gray-500 font-mono bg-gray-100 p-2 rounded">
                      ID: {event.id}
                    </div>
                  </div>
                )}

                <div className="mt-8 pt-6 border-t border-white/20">
                  <div className="bg-blue-500/20 border border-blue-500/30 rounded-xl p-4">
                    <h4 className="font-semibold text-blue-200 mb-3 flex items-center">
                      <span className="mr-2">ℹ️</span>
                      Información importante
                    </h4>
                    <ul className="text-sm text-blue-100 space-y-2">
                      <li className="flex items-start">
                        <span className="mr-2 text-blue-300">•</span>
                        Evento gratuito con registro previo
                      </li>
                      <li className="flex items-start">
                        <span className="mr-2 text-blue-300">•</span>
                        Certificado de participación incluido
                      </li>
                      <li className="flex items-start">
                        <span className="mr-2 text-blue-300">•</span>
                        Coffee break y almuerzo incluidos
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <style dangerouslySetInnerHTML={{
        __html: `
          @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
          }
          
          .animate-fadeIn {
            animation: fadeIn 0.3s ease-out forwards;
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