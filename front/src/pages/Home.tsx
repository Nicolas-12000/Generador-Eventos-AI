// front/src/pages/Home.tsx
import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div style={{ padding: "2rem" }}>
      <h1>Generador de Eventos AI</h1>
      <p>Bienvenido al sistema. Prueba iniciar sesión o ver tu perfil:</p>

      <ul>
        <li><Link to="/login">Iniciar sesión</Link></li>
        <li><Link to="/register">Registrarse</Link></li>
        <li><Link to="/me">Ver perfil (requiere login)</Link></li>
      </ul>
    </div>
  );
}
