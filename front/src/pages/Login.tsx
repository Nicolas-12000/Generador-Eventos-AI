import { useState } from "react";
import axios from "axios";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [token, setToken] = useState("");

  const handleLogin = async () => {
    try {
      const res = await axios.post("http://localhost:8084/api/auth/login", {
        email,
        password,
      });
      const jwt = res.data.token;
      setToken(jwt);
      localStorage.setItem("token", jwt);
      alert("Login exitoso");
    } catch (err) {
      console.error(err);
      alert("Error al iniciar sesión");
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
      <input type="password" placeholder="Contraseña" value={password} onChange={(e) => setPassword(e.target.value)} />
      <button onClick={handleLogin}>Iniciar sesión</button>
      {token && <p>Token guardado ✅</p>}
    </div>
  );
}
