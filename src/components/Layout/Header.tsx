import React from 'react';
import { User } from '../../types';

interface HeaderProps {
  currentUser: User | null;
  onLogout: () => void;
  onToggleHistorySidebar: () => void;
  isHistorySidebarOpen: boolean;
}

const Header: React.FC<HeaderProps> = ({ currentUser, onLogout, onToggleHistorySidebar, isHistorySidebarOpen }) => {
  return (
    <header className="bg-gradient-to-r from-sky-600 to-cyan-500 text-white p-4 shadow-lg sticky top-0 z-50">
      <div className="container mx-auto flex justify-between items-center">
        <div className="flex items-center space-x-3">
          {currentUser && (
            <button
              onClick={onToggleHistorySidebar}
              className="p-2 rounded-md hover:bg-sky-700 focus:outline-none focus:ring-2 focus:ring-sky-400"
              aria-label={isHistorySidebarOpen ? "Ocultar historial de invitaciones" : "Mostrar historial de invitaciones"}
              title={isHistorySidebarOpen ? "Ocultar historial" : "Mostrar historial"}
            >
              {isHistorySidebarOpen ? (
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
                </svg>
              ) : (
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
                </svg>
              )}
            </button>
          )}
          <h1 className="text-xl sm:text-3xl font-bold tracking-tight">Generador de Eventos AI</h1>
        </div>
        {currentUser && (
          <div className="flex items-center space-x-3">
            <span className="text-sm hidden sm:inline">Hola, {currentUser.email}</span>
            <button
              onClick={onLogout}
              className="bg-red-500 hover:bg-red-600 text-white font-semibold py-2 px-4 rounded-lg shadow-md transition duration-150 ease-in-out transform hover:scale-105"
            >
              Cerrar Sesión
            </button>
          </div>
        )}
      </div>
    </header>
  );
};

export default Header;