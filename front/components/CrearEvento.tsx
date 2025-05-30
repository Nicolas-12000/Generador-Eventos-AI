import { useState } from 'react';
import { crearEvento } from '../services/eventoService';

export function CrearEvento({ onEventoCreado }: { onEventoCreado: () => void }) {
  const [evento, setEvento] = useState({
    titulo: '',
    descripcion: '',
    fecha: '',
    lugar: ''
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEvento({ ...evento, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await crearEvento(evento);
    onEventoCreado();
    setEvento({ titulo: '', descripcion: '', fecha: '', lugar: '' });
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-2 mb-6">
      <input name="titulo" value={evento.titulo} onChange={handleChange} placeholder="Título" className="border p-1 w-full" />
      <input name="descripcion" value={evento.descripcion} onChange={handleChange} placeholder="Descripción" className="border p-1 w-full" />
      <input name="fecha" value={evento.fecha} onChange={handleChange} type="date" className="border p-1 w-full" />
      <input name="lugar" value={evento.lugar} onChange={handleChange} placeholder="Lugar" className="border p-1 w-full" />
      <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded">Crear evento</button>
    </form>
  );
}
