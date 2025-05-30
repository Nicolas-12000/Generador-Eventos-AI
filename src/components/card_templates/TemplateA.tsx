
import React from 'react';
import { EventDetails } from '../../types';

interface TemplateProps {
  details: EventDetails;
}

const TemplateA: React.FC<TemplateProps> = ({ details }) => {
  const formatDate = (dateString: string) => {
    if (!dateString) return 'Fecha no especificada';
    try {
      return new Date(dateString + 'T00:00:00').toLocaleDateString('es-ES', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' });
    } catch (e) { return dateString; }
  };
  
  return (
    <div className="w-full max-w-md bg-gradient-to-br from-slate-800 to-slate-900 text-white rounded-xl shadow-2xl p-8 font-sans transform transition-all duration-300 hover:scale-105">
      <div className="text-center mb-6">
        <h2 className="text-4xl font-bold text-sky-400 tracking-tight">{details.eventName || "Nombre del Evento"}</h2>
        <p className="text-sky-300 text-lg mt-1">Una invitación especial para ti</p>
      </div>
      
      <div className="space-y-4 mb-6 text-slate-200">
        <div className="flex items-center">
          <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 text-sky-400 mr-3" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" /></svg>
          <p><span className="font-semibold">Fecha:</span> {formatDate(details.date)}</p>
        </div>
        <div className="flex items-center">
          <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 text-sky-400 mr-3" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
          <p><span className="font-semibold">Hora:</span> {details.time || "Hora no especificada"}</p>
        </div>
        <div className="flex items-center">
          <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 text-sky-400 mr-3" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" /><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" /></svg>
          <p><span className="font-semibold">Lugar:</span> {details.location || "Lugar por confirmar"}</p>
        </div>
        <div className="flex items-center">
          <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 text-sky-400 mr-3" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" /></svg>
          <p><span className="font-semibold">Anfitrión:</span> {details.host || "Anfitrión por confirmar"}</p>
        </div>
      </div>

      {details.description && (
        <div className="bg-slate-700 p-4 rounded-lg mb-6">
          <p className="text-sm text-slate-300 italic">{details.description}</p>
        </div>
      )}
      
      <div className="text-center mt-8">
        <p className="text-sm text-sky-500">¡Esperamos verte allí!</p>
      </div>
    </div>
  );
};

export default TemplateA;
