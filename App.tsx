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
          if (parsedInvitations.length > 0) {
            // Opcional: cargar la más reciente o la primera
            // setActiveCardId(parsedInvitations[0].id); 
          }
        }
      } catch (error) {
        console.error("Error al parsear invitaciones de localStorage:", error);
        localStorage.removeItem('savedInvitations'); // Limpiar si está corrupto
      }
    }
  }, []);

  // Guardar invitaciones en localStorage cuando cambien
  useEffect(() => {
    localStorage.setItem('savedInvitations', JSON.stringify(savedInvitations));
  }, [savedInvitations]);

  const handleLogin = useCallback((user: User) => {
    setCurrentUser(user);
    // Si no hay tarjeta activa y hay invitaciones, cargar la primera. Sino, crear una nueva.
    if (!activeCardId && savedInvitations.length > 0) {
      setActiveCardId(savedInvitations[0].id);
    } else if (!activeCardId) {
      const newCard = generateNewCardState();
      setSavedInvitations(prev => [newCard, ...prev]);
      setActiveCardId(newCard.id);
    }
  }, [activeCardId, savedInvitations]);

  const handleLogout = useCallback(() => {
    setCurrentUser(null);
    // Opcional: limpiar activeCardId al cerrar sesión si se desea
    // setActiveCardId(null); 
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
        return updatedList.sort((a,b) => new Date(b.lastModified).getTime() - new Date(a.lastModified).getTime());
      }
      return [updatedCard, ...prev].sort((a,b) => new Date(b.lastModified).getTime() - new Date(a.lastModified).getTime());
    });
    if (activeCardId !== cardDataToSave.id) {
       setActiveCardId(cardDataToSave.id); // Asegurarse de que la tarjeta guardada sea la activa
    }
  }, [activeCardId]);

  const handleLoadCard = useCallback((cardId: string) => {
    setActiveCardId(cardId);
    setIsHistorySidebarOpen(false); // Opcional: cerrar sidebar al cargar
  }, []);

  const handleNewCard = useCallback(() => {
    const newCard = generateNewCardState();
    setSavedInvitations(prev => [newCard, ...prev].sort((a,b) => new Date(b.lastModified).getTime() - new Date(a.lastModified).getTime()));
    setActiveCardId(newCard.id);
    setIsHistorySidebarOpen(false); // Opcional: cerrar sidebar
  }, []);

  const handleDeleteCard = useCallback((cardIdToDelete: string) => {
    setSavedInvitations(prev => prev.filter(card => card.id !== cardIdToDelete));
    if (activeCardId === cardIdToDelete) {
      // Si se eliminó la tarjeta activa, cargar la siguiente o crear una nueva
      const remainingCards = savedInvitations.filter(card => card.id !== cardIdToDelete);
      if (remainingCards.length > 0) {
        setActiveCardId(remainingCards.sort((a,b) => new Date(b.lastModified).getTime() - new Date(a.lastModified).getTime())[0].id);
      } else {
        handleNewCard(); // Crea una nueva si no quedan más
      }
    }
  }, [activeCardId, savedInvitations, handleNewCard]);
  
  const activeCard = savedInvitations.find(card => card.id === activeCardId) || 
                     (savedInvitations.length > 0 ? savedInvitations[0] : null) || 
                     generateNewCardState(); // Fallback a una nueva si todo falla

  // Efecto para asegurar que siempre haya una tarjeta activa si el usuario está logueado
  useEffect(() => {
    if (currentUser && !activeCardId && savedInvitations.length === 0) {
      const newCard = generateNewCardState();
      setSavedInvitations([newCard]);
      setActiveCardId(newCard.id);
    } else if (currentUser && !activeCardId && savedInvitations.length > 0) {
      setActiveCardId(savedInvitations.sort((a,b) => new Date(b.lastModified).getTime() - new Date(a.lastModified).getTime())[0].id);
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
                    activeCard={activeCard} // Pasar la tarjeta activa completa
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