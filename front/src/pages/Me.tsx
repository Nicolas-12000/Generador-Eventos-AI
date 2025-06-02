// front/src/pages/Me.tsx
import { useEffect, useState } from "react";
import api from "../services/api";

const Me = () => {
  const [user, setUser] = useState<any>(null);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const res = await api.get("/user/me");
        setUser(res.data);
      } catch (err) {
        console.error("Error al obtener usuario:", err);
        alert("No autenticado. Redirigiendo al login...");
        window.location.href = "/login";
      }
    };

    fetchUser();
  }, []);

  if (!user) return <p>Cargando...</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Bienvenido, {user.firstName} {user.lastName}</h2>
      <p>Email: {user.email}</p>
      <p>Usuario: {user.username}</p>
    </div>
  );
};

export default Me;
