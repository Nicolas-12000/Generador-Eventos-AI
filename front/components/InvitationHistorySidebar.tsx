import React from 'react';
import { CardState } from '../types';
import Button from './ui/Button';

interface InvitationHistorySidebarProps {
  savedInvitations: CardState[];
  activeCardId: string | null;
  onLoadCard: (cardId: string) => void;
  onNewCard: () => void;
  onDeleteCard: (cardId: string) => void;
}

const InvitationHistorySidebar: React.FC<InvitationHistorySidebarProps> = ({
  savedInvitations,
  activeCardId,
  onLoadCard,
  onNewCard,
  onDeleteCard,
}) => {
  const formatDate = (isoString: string) => {
    try {
      return new Date(isoString).toLocaleDateString('es-ES', {
        day: '2-digit', month: 'short', year: 'numeric', hour: '2-digit', minute: '2-digit'
      });
    } catch (e) {
      return 'Fecha inválida';
    }
  };

  return (
    <div className="flex flex-col h-full">
      <div className="p-3 border-b border-slate-700">
        <Button onClick={onNewCard} variant="primary" size="sm" className="w-full"
          icon={<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-4 h-4"><path strokeLinecap="round" strokeLinejoin="round" d="M12 4.5v15m7.5-7.5h-15" /></svg>}
        >
          Nueva Invitación
        </Button>
      </div>
      <div className="flex-grow overflow-y-auto">
        {savedInvitations.length === 0 ? (
          <p className="p-4 text-sm text-slate-400 text-center">No hay invitaciones guardadas.</p>
        ) : (
          <ul className="space-y-1 p-2">
            {savedInvitations.map((card) => (
              <li key={card.id}>
                <button
                  onClick={() => onLoadCard(card.id)}
                  className={`w-full text-left p-2.5 rounded-md transition-colors duration-150 ease-in-out
                              ${activeCardId === card.id ? 'bg-sky-600 text-white shadow-md' : 'hover:bg-slate-700 text-slate-300 hover:text-white'}`}
                >
                  <div className="flex justify-between items-center">
                    <div className="flex-grow overflow-hidden">
                        <p className="text-sm font-medium truncate" title={card.details.eventName}>
                            {card.details.eventName || "Invitación sin título"}
                        </p>
                        <p className="text-xs text-slate-400 group-hover:text-slate-300">
                            Mod: {formatDate(card.lastModified)}
                        </p>
                    </div>
                    <button
                        onClick={(e) => {
                            e.stopPropagation(); // Evitar que se cargue la tarjeta al eliminar
                            if (window.confirm(`¿Seguro que quieres eliminar "${card.details.eventName || "esta invitación"}"?`)) {
                                onDeleteCard(card.id);
                            }
                        }}
                        className={`p-1 rounded-full transition-colors 
                                    ${activeCardId === card.id ? 'hover:bg-sky-500' : 'hover:bg-slate-600'} 
                                    text-slate-400 hover:text-red-400 opacity-70 hover:opacity-100`}
                        title="Eliminar invitación"
                        aria-label="Eliminar invitación"
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor" className="w-4 h-4">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12.576 0c1.132-.095 2.267-.164 3.478-.397m7.408 0a48.119 48.119 0 01-7.408 0M5.28 4.717A48.063 48.063 0 0112 4.5c2.495 0 4.89.393 7.064 1.085M5.28 4.717L4.5 3.25m0 0l-.78-1.467M19.72 4.717L20.5 3.25m0 0l.78-1.467M12 9v6m3-3H9" />
                        </svg>
                    </button>
                  </div>
                </button>
              </li>
            ))}
          </ul>
        )}
      </div>
      <div className="p-2 text-xs text-slate-500 border-t border-slate-700 text-center">
        {savedInvitations.length} {savedInvitations.length === 1 ? "invitación" : "invitaciones"}
      </div>
    </div>
  );
};

export default InvitationHistorySidebar;