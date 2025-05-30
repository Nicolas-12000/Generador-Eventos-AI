import React from 'react';
import CardCreator from './CardCreator/CardCreator';
import InvitationHistorySidebar from './InvitationHistorySidebar'; // Nuevo
import { CardState } from '../types';

interface DashboardPageProps {
  savedInvitations: CardState[];
  activeCard: CardState; // La tarjeta activa completa
  activeCardId: string | null;
  onSaveCard: (cardData: CardState) => void;
  onLoadCard: (cardId: string) => void;
  onNewCard: () => void;
  onDeleteCard: (cardId: string) => void;
  isHistorySidebarOpen: boolean;
}

const DashboardPage: React.FC<DashboardPageProps> = ({
  savedInvitations,
  activeCard,
  activeCardId,
  onSaveCard,
  onLoadCard,
  onNewCard,
  onDeleteCard,
  isHistorySidebarOpen,
}) => {
  return (
    <div className="flex h-[calc(100vh-150px)]"> {/* Ajustar altura para que no exceda la ventana con header/footer */}
      {isHistorySidebarOpen && (
        <aside className="w-64 lg:w-72 bg-slate-800 text-white p-4 overflow-y-auto shadow-lg transition-all duration-300 ease-in-out">
          <InvitationHistorySidebar
            savedInvitations={savedInvitations}
            activeCardId={activeCardId}
            onLoadCard={onLoadCard}
            onNewCard={onNewCard}
            onDeleteCard={onDeleteCard}
          />
        </aside>
      )}
      <div className="flex-grow p-0 sm:p-2 md:p-4 overflow-y-auto"> {/* Quitamos padding global, CardCreator lo manejará */}
        <div className="bg-white p-3 sm:p-4 md:p-6 rounded-xl shadow-2xl h-full"> {/* CardCreator debe ocupar toda la altura */}
          {/* Título y descripción se mueven dentro de CardCreator o se omiten para más espacio */}
          {activeCard && activeCardId && ( // Asegurarse de que activeCard y activeCardId existan
            <CardCreator
              key={activeCardId} // Crucial para re-montar y reiniciar estado interno
              initialCard={activeCard}
              onSaveCard={onSaveCard}
            />
          )}
          {!activeCardId && (
             <div className="flex justify-center items-center h-full">
                <p className="text-slate-500 text-lg">Selecciona o crea una invitación para comenzar.</p>
             </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;