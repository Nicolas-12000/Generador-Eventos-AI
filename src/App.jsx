import React from "react";
import { HashRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./components/LoginPage";
import DashboardPage from "./components/DashboardPage";
import Header from "./components/Layout/Header";

const App = () => {
  // Aquí deberías manejar el estado de usuario, invitaciones, etc.
  // Para un ejemplo simple, solo mostramos las rutas.
  return (
    <HashRouter>
      <div className="min-h-screen flex flex-col">
        <Header />
        <main className="flex-grow container mx-auto p-4 sm:p-6 lg:p-8">
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/dashboard" element={<DashboardPage />} />
            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </main>
        <footer className="bg-slate-800 text-white text-center p-4 shadow-md mt-auto">
          <p>&copy; {new Date().getFullYear()} Generador de Eventos AI. Todos los derechos reservados.</p>
        </footer>
      </div>
    </HashRouter>
  );
};

export default App;