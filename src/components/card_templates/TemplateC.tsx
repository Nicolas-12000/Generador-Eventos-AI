
import React from 'react';
import { EventDetails } from '../../types';

interface TemplateProps {
  details: EventDetails;
}

const TemplateC: React.FC<TemplateProps> = ({ details }) => {
    const formatDateResult = (dateString: string): { day: string; month: string; } | 'Fecha no especificada' => {
    if (!dateString) return 'Fecha no especificada';
     try {
      const date = new Date(dateString + 'T00:00:00');
      const day = date.toLocaleDateString('es-ES', { day: '2-digit' });
      const monthUpper = date.toLocaleDateString('es-ES', { month: 'short' }).replace('.', '');
      // Capitalize month e.g. "ene" -> "Ene"
      const month = monthUpper.charAt(0).toUpperCase() + monthUpper.slice(1);
      return { day, month };
    } catch (e) { 
      console.error("Error formatting date in TemplateC:", e);
      return { day: '??', month: '???'}; 
    }
  };
  
  // Fix: Handle string and object return types from formatDateResult
  const dateParts = formatDateResult(details.date);
  const day = typeof dateParts === 'object' ? dateParts.day : '??';
  const month = typeof dateParts === 'object' ? dateParts.month : '???';

  return (
    <div className="w-full max-w-md bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500 text-white rounded-xl shadow-2xl p-8 font-sans relative overflow-hidden transform transition-all duration-300 hover:rotate-1 hover:scale-105">
      {/* Decorative elements */}
      <div className="absolute -top-4 -left-4 w-16 h-16 bg-yellow-300 rounded-full opacity-50 animate-pulse"></div>
      <div className="absolute -bottom-4 -right-4 w-20 h-20 bg-pink-300 rounded-full opacity-50 animate-pulse delay-500"></div>

      <div className="text-center mb-6 z-10 relative">
        <p className="text-sm uppercase tracking-widest text-yellow-200">¡Prepárate para la Fiesta!</p>
        <h2 className="text-4xl font-extrabold mt-1 drop-shadow-lg">{details.eventName || "Gran Celebración"}</h2>
      </div>
      
      <div className="grid grid-cols-2 gap-4 mb-6 text-lg z-10 relative">
        <div className="bg-white bg-opacity-20 p-4 rounded-lg text-center backdrop-blur-sm">
          {/* Fix: Use the resolved day variable */}
          <p className="text-4xl font-bold text-yellow-300">{day}</p>
          {/* Fix: Use the resolved month variable */}
          <p className="text-sm uppercase tracking-wider">{month}</p>
        </div>
        <div className="bg-white bg-opacity-20 p-4 rounded-lg text-center backdrop-blur-sm">
          <p className="text-4xl font-bold text-yellow-300">{details.time ? details.time.replace(':','h') : "Hora"}</p>
          <p className="text-sm uppercase tracking-wider">Comienza</p>
        </div>
      </div>

      <div className="space-y-3 text-md mb-6 z-10 relative bg-black bg-opacity-10 p-4 rounded-lg backdrop-blur-sm">
        <p><strong className="text-yellow-200">En:</strong> {details.location || "Lugar por confirmar"}</p>
        <p><strong className="text-yellow-200">Anfitrión:</strong> {details.host || "Anfitrión por confirmar"}</p>
      </div>

      {details.description && (
        <div className="z-10 relative bg-black bg-opacity-20 p-4 rounded-lg backdrop-blur-sm">
          <p className="text-sm leading-relaxed">{details.description}</p>
        </div>
      )}
      
      <div className="text-center mt-8 z-10 relative">
        <p className="text-lg font-semibold text-yellow-200 animate-bounce">¡No te lo pierdas!</p>
      </div>
       <style>{`
        @keyframes pulse {
          0%, 100% { opacity: 0.5; transform: scale(1); }
          50% { opacity: 0.8; transform: scale(1.1); }
        }
        .animate-pulse { animation: pulse 2s infinite ease-in-out; }
        .delay-500 { animation-delay: 0.5s; }
        @keyframes bounce {
          0%, 20%, 50%, 80%, 100% {transform: translateY(0);}
          40% {transform: translateY(-10px);}
          60% {transform: translateY(-5px);}
        }
        .animate-bounce { animation: bounce 2s infinite; }
      `}</style>
    </div>
  );
};

export default TemplateC;
