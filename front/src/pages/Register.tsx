// front/src/pages/Register.tsx
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Register() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    username: "",
    password: "",
    email: "",
    firstName: "",
    lastName: ""
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8084/api/auth/register", form);
      alert("¡Registrado con éxito!");
      navigate("/login");
    } catch (err) {
      alert("Error en el registro");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Registro</h2>
      <form onSubmit={handleSubmit}>
        {["username", "password", "email", "firstName", "lastName"].map((key) => (
          <div key={key} style={{ marginBottom: "1rem" }}>
            <input
              type={key === "password" ? "password" : "text"}
              name={key}
              placeholder={key}
              value={(form as any)[key]}
              onChange={handleChange}
              required
            />
          </div>
        ))}
        <button type="submit">Registrarse</button>
      </form>
    </div>
  );
}
