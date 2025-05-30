import { useEffect, useState } from 'react';
import { obtenerEventos, eliminarEvento } from '../services/eventoService';

export function ListaEventos() {
  const [eventos, setEventos] = useState<any[]>([]);

  useEffect(() => {
    cargarEventos();
  }, []);

  const cargarEventos = async () => {
    const data = await obtenerEventos();
    setEventos(data);
  };

  const handleEliminar = async (id: number) => {
    await eliminarEvento(id);
    cargarEventos();
  };

  return (
    <div>
      <h2 className="text-xl font-bold mb-2">Eventos registrados</h2>
      <ul className="space-y-2">
        {eventos.map(e => (
          <li key={e.id} className="border p-2 rounded shadow">
            <p><strong>{e.titulo}</strong></p>
            <p>{e.descripcion}</p>
            <p>{e.fecha} – {e.lugar}</p>
            <button
              onClick={() => handleEliminar(e.id)}
              className="mt-2 text-red-500 hover:underline"
            >
              Eliminar
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}
