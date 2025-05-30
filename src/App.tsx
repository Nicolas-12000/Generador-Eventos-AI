import React, { useState, useCallback, useEffect } from 'react';
import { HashRouter, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './components/LoginPage';
import DashboardPage from './components/DashboardPage';
import Header from './components/Layout/Header';
import { User, CardState } from './types';
import { generateNewCardState } from './constants';

const App: React.FC = () => {
  const [currentUser, setCurrentUser] = useState<User | null>(null);
  const [savedInvitations, setSavedInvitations] = useState<CardState[]>([]);
  const [activeCardId, setActiveCardId] = useState<string | null>(null);
  const [isHistorySidebarOpen, setIsHistorySidebarOpen] = useState<boolean>(false);

  // Cargar invitaciones guardadas desde localStorage al inicio
  useEffect(() => {
    const storedInvitations = localStorage.getItem('savedInvitations');
    if (storedInvitations) {
      try {
        const parsedInvitations = JSON.parse(storedInvitations);
        if (Array.isArray(parsedInvitations)) {
          setSavedInvitations(parsedInvitations);
        }
      } catch (error) {
        console.error("Error al parsear invitaciones de localStorage:", error);
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
    setActiveCardId(prevActiveCardId => {
      if (!prevActiveCardId && savedInvitations.length > 0) {
        return savedInvitations[0].id;
      } else if (!prevActiveCardId) {
        const newCard = generateNewCardState();
        setSavedInvitations(prev => [newCard, ...prev]);
        return newCard.id;
      }
      return prevActiveCardId;
    });
  }, [savedInvitations]);

  const handleLogout = useCallback(() => {
    setCurrentUser(null);
    // setActiveCardId(null); // Si quieres limpiar la tarjeta activa al cerrar sesión, descomenta esto
  }, []);

  const toggleHistorySidebar = useCallback(() => {
    setIsHistorySidebarOpen(prev => !prev);
  }, []);

  const handleSaveCard = useCallback((cardDataToSave: CardState) => {
    setSavedInvitations(prev => {
      const existingIndex = prev.findIndex(card => card.id === cardDataToSave.id);
      const updatedCard = { ...cardDataToSave, lastModified: new Date().toISOString() };
      if (existingIndex > -1) {
        const updatedList = [...prev];
        updatedList[existingIndex] = updatedCard;
        return updatedList.sort((a, b) => new Date(b.lastModified).getTime() - new Date(a.lastModified).getTime());
      }
      return [updatedCard, ...prev].sort((a, b) => new Date(b.lastModified).getTime() - new Date(a.lastModified).getTime());
    });
    setActiveCardId(cardDataToSave.id);
  }, []);

  const handleLoadCard = useCallback((cardId: string) => {
    setActiveCardId(cardId);
    setIsHistorySidebarOpen(false);
  }, []);

  const handleNewCard = useCallback(() => {
    const newCard = generateNewCardState();
    setSavedInvitations(prev => [newCard, ...prev].sort((a, b) => new Date(b.lastModified).getTime() - new Date(a.lastModified).getTime()));
    setActiveCardId(newCard.id);
    setIsHistorySidebarOpen(false);
  }, []);

  const handleDeleteCard = useCallback((cardIdToDelete: string) => {
    setSavedInvitations(prev => {
      const updated = prev.filter(card => card.id !== cardIdToDelete);
      if (activeCardId === cardIdToDelete) {
        if (updated.length > 0) {
          setActiveCardId(updated.sort((a, b) => new Date(b.lastModified).getTime() - new Date(a.lastModified).getTime())[0].id);
        } else {
          const newCard = generateNewCardState();
          setSavedInvitations([newCard]);
          setActiveCardId(newCard.id);
        }
      }
      return updated;
    });
  }, [activeCardId]);

  const activeCard = savedInvitations.find(card => card.id === activeCardId) ||
    (savedInvitations.length > 0 ? savedInvitations[0] : null) ||
    generateNewCardState();

  // Efecto para asegurar que siempre haya una tarjeta activa si el usuario está logueado
  useEffect(() => {
    if (currentUser && !activeCardId && savedInvitations.length === 0) {
      const newCard = generateNewCardState();
      setSavedInvitations([newCard]);
      setActiveCardId(newCard.id);
    } else if (currentUser && !activeCardId && savedInvitations.length > 0) {
      setActiveCardId(savedInvitations.sort((a, b) => new Date(b.lastModified).getTime() - new Date(a.lastModified).getTime())[0].id);
    }
  }, [currentUser, activeCardId, savedInvitations]);

  return (
    <HashRouter>
      <div className="min-h-screen flex flex-col">
        <Header
          currentUser={currentUser}
          onLogout={handleLogout}
          onToggleHistorySidebar={toggleHistorySidebar}
          isHistorySidebarOpen={isHistorySidebarOpen}
        />
        <main className="flex-grow container mx-auto p-4 sm:p-6 lg:p-8">
          <Routes>
            <Route
              path="/login"
              element={currentUser ? <Navigate to="/dashboard" /> : <LoginPage onLogin={handleLogin} />}
            />
            <Route
              path="/dashboard"
              element={
                currentUser ? (
                  <DashboardPage
                    savedInvitations={savedInvitations}
                    activeCard={activeCard}
                    activeCardId={activeCardId}
                    onSaveCard={handleSaveCard}
                    onLoadCard={handleLoadCard}
                    onNewCard={handleNewCard}
                    onDeleteCard={handleDeleteCard}
                    isHistorySidebarOpen={isHistorySidebarOpen}
                  />
                ) : (
                  <Navigate to="/login" />
                )
              }
            />
            <Route path="*" element={<Navigate to={currentUser ? "/dashboard" : "/login"} />} />
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