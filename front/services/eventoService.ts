const API_URL = 'http://localhost:8080/api/eventos';

export async function obtenerEventos() {
  const res = await fetch(API_URL);
  return await res.json();
}

export async function crearEvento(evento: {
  titulo: string;
  descripcion: string;
  fecha: string;
  lugar: string;
}) {
  const res = await fetch(API_URL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(evento)
  });
  return await res.json();
}

export async function eliminarEvento(id: number) {
  await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
}
