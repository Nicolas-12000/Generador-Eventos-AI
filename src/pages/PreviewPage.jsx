// src/pages/PreviewPage.jsx
export default function PreviewPage() {
  // Aquí obtendríamos los datos reales del backend
  const eventData = {
    title: "Evento de Tecnología 2023",
    date: "15 de Noviembre",
    location: "Centro de Convenciones",
    description: "Un evento sobre las últimas tendencias en tecnología...",
    colors: ['#3B82F6', '#10B981']
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-4xl mx-auto bg-white rounded-lg shadow-md overflow-hidden">
        {/* Header */}
        <div 
          className="h-48 bg-gradient-to-r from-blue-500 to-green-500 flex items-center justify-center"
          style={{
            background: `linear-gradient(to right, ${eventData.colors[0]}, ${eventData.colors[1]})`
          }}
        >
          <h1 className="text-4xl font-bold text-white text-center">
            {eventData.title}
          </h1>
        </div>
        
        {/* Detalles */}
        <div className="p-6">
          <div className="flex flex-wrap gap-4 mb-6">
            <div className="flex items-center">
              <CalendarIcon className="h-5 w-5 mr-2 text-gray-500" />
              <span>{eventData.date}</span>
            </div>
            <div className="flex items-center">
              <LocationMarkerIcon className="h-5 w-5 mr-2 text-gray-500" />
              <span>{eventData.location}</span>
            </div>
          </div>
          
          <p className="text-gray-700 mb-6">{eventData.description}</p>
          
          <button className="bg-blue-500 hover:bg-blue-600 text-white px-6 py-3 rounded-lg">
            ¡Regístrate Ahora!
          </button>
        </div>
      </div>
    </div>
  );
}

// Iconos simples (o reemplazar por librería como @heroicons/react)
function CalendarIcon() {
  return (
    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
    </svg>
  );
}

function LocationMarkerIcon() {
  return (
    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
    </svg>
  );
}