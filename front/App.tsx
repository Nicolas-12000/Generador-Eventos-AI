import React, { useState, useCallback, useEffect } from 'react';
import { HashRouter, Routes, Route, Navigate, Link, useNavigate } from 'react-router-dom';
import LoginPage from './components/LoginPage';
import DashboardPage from './components/DashboardPage';
import Header from './components/Layout/Header';
import { User, CardState } from './types';
import { generateNewCardState } from './constants';
import { CrearEvento } from './components/CrearEvento';
import { ListaEventos } from './components/ListaEventos';

const App: React.FC = () => {
  const [currentUser, setCurrentUser] = useState<User | null>(null);
  const [savedInvitations, setSavedInvitations] = useState<CardState[]>([]);
  const [activeCardId, setActiveCardId] = useState<string | null>(null);
  const [isHistorySidebarOpen, setIsHistorySidebarOpen] = useState<boolean>(false);

  // Cargar invitaciones guardadas desde localStorage al inicio
  useEffect(() => {
    const stored = localStorage.getItem('savedInvitations');
    if (stored) {
      try {
        const parsed = JSON.parse(stored);
        if (Array.isArray(parsed)) {
          setSavedInvitations(parsed);
          if (parsed.length > 0) setActiveCardId(parsed[0].id);
        }
      } catch {
        localStorage.removeItem('savedInvitations');
      }
    }
  }, []);

  // Guardar invitaciones en localStorage cuando cambien
  useEffect(() => {
    localStorage.setItem('savedInvitations', JSON.stringify(savedInvitations));
  }, [savedInvitations]);

  const handleLogin = useCallback((user: User) => {
    setCurrentUser(user);
    if (!activeCardId) {
      const newCard = generateNewCardState();
      setSavedInvitations(prev => [newCard, ...prev]);
      setActiveCardId(newCard.id);
    }
  }, [activeCardId]);

  const handleLogout = useCallback(() => {
    setCurrentUser(null);
  }, []);

  const toggleHistorySidebar = useCallback(() => {
    setIsHistorySidebarOpen(v => !v);
  }, []);

  const handleSaveCard = useCallback((card: CardState) => {
    setSavedInvitations(prev => {
      const idx = prev.findIndex(c => c.id === card.id);
      const updated = { ...card, lastModified: new Date().toISOString() };
      if (idx > -1) {
        prev[idx] = updated;
        return [...prev];
      }
      return [updated, ...prev];
    });
    setActiveCardId(card.id);
  }, []);

  const handleLoadCard = useCallback((id: string) => {
    setActiveCardId(id);
    setIsHistorySidebarOpen(false);
  }, []);

  const handleNewCard = useCallback(() => {
    const newCard = generateNewCardState();
    setSavedInvitations(prev => [newCard, ...prev]);
    setActiveCardId(newCard.id);
    setIsHistorySidebarOpen(false);
  }, []);

  const handleDeleteCard = useCallback((id: string) => {
    setSavedInvitations(prev => prev.filter(c => c.id !== id));
    if (activeCardId === id) {
      const remaining = savedInvitations.filter(c => c.id !== id);
      if (remaining.length) setActiveCardId(remaining[0].id);
      else handleNewCard();
    }
  }, [activeCardId, savedInvitations, handleNewCard]);

  const activeCard = savedInvitations.find(c => c.id === activeCardId) 
    || generateNewCardState();

  return (
    <HashRouter>
      <div className="min-h-screen flex flex-col">
        <Header 
          currentUser={currentUser} 
          onLogout={handleLogout} 
          onToggleHistorySidebar={toggleHistorySidebar}
          isHistorySidebarOpen={isHistorySidebarOpen}
        />

        {currentUser && (
          <nav className="bg-gray-100 p-2 space-x-4 text-sm">
            <Link to="/dashboard" className="hover:underline">Dashboard</Link>
            <Link to="/eventos" className="hover:underline">Ver Eventos</Link>
            <Link to="/crear-evento" className="hover:underline">Crear Evento</Link>
          </nav>
        )}

        <main className="flex-grow container mx-auto p-4 sm:p-6 lg:p-8">
          <Routes>
            <Route 
              path="/login" 
              element={currentUser 
                ? <Navigate to="/dashboard" replace /> 
                : <LoginPage onLogin={handleLogin} />
              } 
            />

            <Route 
              path="/dashboard" 
              element={currentUser 
                ? <DashboardPage
                    savedInvitations={savedInvitations}
                    activeCard={activeCard}
                    activeCardId={activeCardId}
                    onSaveCard={handleSaveCard}
                    onLoadCard={handleLoadCard}
                    onNewCard={handleNewCard}
                    onDeleteCard={handleDeleteCard}
                    isHistorySidebarOpen={isHistorySidebarOpen}
                  />
                : <Navigate to="/login" replace />
              } 
            />

            <Route 
              path="/eventos" 
              element={currentUser 
                ? <ListaEventos /> 
                : <Navigate to="/login" replace />
              } 
            />

            <Route 
              path="/crear-evento" 
              element={currentUser 
                ? <CrearEvento onEventoCreado={() => window.location.hash = '#/eventos'} /> 
                : <Navigate to="/login" replace />
              } 
            />

            <Route 
              path="*" 
              element={<Navigate to={currentUser ? "/dashboard" : "/login"} replace />} 
            />
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
