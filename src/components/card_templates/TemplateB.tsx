
import React from 'react';
import { EventDetails } from '../../types';

interface TemplateProps {
  details: EventDetails;
}

const TemplateB: React.FC<TemplateProps> = ({ details }) => {
  const formatDate = (dateString: string) => {
    if (!dateString) return 'Fecha no especificada';
     try {
      return new Date(dateString + 'T00:00:00').toLocaleDateString('es-ES', { year: 'numeric', month: 'long', day: 'numeric' });
    } catch (e) { return dateString; }
  };

  return (
    <div className="w-full max-w-md bg-amber-50 border border-amber-300 text-amber-900 rounded-lg shadow-xl p-8 font-serif transform transition-all duration-300 hover:shadow-2xl">
      <div className="border-b-2 border-amber-400 pb-4 mb-6 text-center">
        <p className="text-xl text-amber-700 tracking-wider">Estás Cordialmente Invitado a</p>
        <h2 className="text-5xl font-bold text-amber-800 mt-2">{details.eventName || "Nombre del Evento"}</h2>
      </div>
      
      <div className="space-y-3 text-lg mb-6">
        <p><strong className="text-amber-700">Fecha:</strong> {formatDate(details.date)}</p>
        <p><strong className="text-amber-700">Hora:</strong> {details.time || "Hora no especificada"}</p>
        <p><strong className="text-amber-700">Lugar:</strong> {details.location || "Lugar por confirmar"}</p>
        <p><strong className="text-amber-700">Presentado por:</strong> {details.host || "Anfitrión por confirmar"}</p>
      </div>

      {details.description && (
        <div className="border-t border-amber-300 pt-4 mt-6">
          <p className="text-md text-amber-800 leading-relaxed">{details.description}</p>
        </div>
      )}
      
      <div className="text-center mt-8">
        <p className="text-amber-600 italic text-lg">RSVP</p>
      </div>
    </div>
  );
};

export default TemplateB;
