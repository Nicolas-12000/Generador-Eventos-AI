import React, { useState, useCallback, useEffect } from 'react';
import { CardState, DesignId, EventDetails, ActionLogEntry, ActionLogFn } from '../../types';
import ChatInterface from './ChatInterface';
import CardPreview from './CardPreview';
import DesignSelector from './DesignSelector';
import InvitationModal from '../InvitationModal';
import Button from '../ui/Button';
import useUndoableState from '../../hooks/useUndoableState';
// import { DEFAULT_EVENT_DETAILS } from '../../constants'; // initialCard viene de props
import ActionHistoryDisplay from './ActionHistoryDisplay'; 

interface CardCreatorProps {
  initialCard: CardState;
  onSaveCard: (cardData: CardState) => void;
  // key prop es implícito y manejado por React
}

const CardCreator: React.FC<CardCreatorProps> = ({ initialCard, onSaveCard }) => {
  const { 
    current: cardState, 
    set: setCardState, 
    undo: performUndo, 
    redo: performRedo, 
    canUndo, 
    canRedo,
    reset: resetUndoableState
  } = useUndoableState<CardState>(initialCard);

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [actionHistory, setActionHistory] = useState<ActionLogEntry[]>([]);
  // const [chatKey, setChatKey] = useState<string>(initialCard.id); // chatKey ahora es el key del CardCreator
  const [isActionHistoryVisible, setIsActionHistoryVisible] = useState(true); // Estado para colapsar

  const logAction = useCallback<ActionLogFn>((message: string) => {
    setActionHistory(prev => {
      const newHistory = [...prev, { id: Date.now().toString(), timestamp: new Date(), message }];
      // Mantener solo los últimos N mensajes si se desea
      // const MAX_HISTORY_LENGTH = 50;
      // return newHistory.slice(-MAX_HISTORY_LENGTH);
      return newHistory;
    });
  }, []);

  // Efecto para reiniciar el historial de acciones y loguear carga de tarjeta
  useEffect(() => {
    setActionHistory([]); // Limpiar historial al cargar nueva tarjeta (por cambio de key)
    logAction(`Invitación '${initialCard.details.eventName}' (ID: ${initialCard.id.substring(0,5)}) cargada.`);
    // setChatKey(initialCard.id); // Actualizar chatKey si es necesario
  }, [initialCard, logAction]); // Solo depende de initialCard (que cambia con el key)

  const handleDetailsChange = useCallback((newDetails: EventDetails) => {
    const oldDetails = cardState.details;
    let changedDetailMessage = "Detalles del evento actualizados.";
    
    const changedKeys = Object.keys(newDetails).filter(
        (key) => newDetails[key as keyof EventDetails] !== oldDetails[key as keyof EventDetails]
    );

    if (changedKeys.length > 0) {
        changedDetailMessage = `Detalle(s) '${changedKeys.join(', ')}' actualizado(s).`;
    }
    // Para un log más específico (opcional)
    // if (changedKeys.length === 1) {
    //   changedDetailMessage = `Detalle '${changedKeys[0]}' actualizado a: "${newDetails[changedKeys[0] as keyof EventDetails]}"`;
    // }

    logAction(changedDetailMessage);
    setCardState({ ...cardState, details: newDetails });
  }, [cardState, setCardState, logAction]);

  const handleDesignChange = useCallback((newDesign: DesignId) => {
    const designName = newDesign.charAt(0).toUpperCase() + newDesign.slice(1);
    logAction(`Diseño cambiado a: ${designName}`);
    setCardState({ ...cardState, design: newDesign });
  }, [cardState, setCardState, logAction]);
  
  const handleSave = () => {
    logAction(`Invitación "${cardState.details.eventName}" guardada.`);
    onSaveCard(cardState);
  };

  const undo = () => {
    if (canUndo) {
      logAction("Acción 'Deshacer' ejecutada.");
      performUndo();
    }
  };

  const redo = () => {
    if (canRedo) {
      logAction("Acción 'Rehacer' ejecutada.");
      performRedo();
    }
  };
  
  const toggleActionHistoryVisibility = () => {
    setIsActionHistoryVisible(prev => !prev);
    logAction(isActionHistoryVisible ? "Historial de acciones ocultado." : "Historial de acciones mostrado.");
  };

  return (
    <div className="grid grid-cols-1 lg:grid-cols-3 gap-4 h-full"> {/* gap-4, h-full */}
      {/* Columna de Controles y Chat */}
      <div className="lg:col-span-1 flex flex-col space-y-4 h-full"> {/* space-y-4 */}
        <div className="bg-slate-50 p-3 sm:p-4 rounded-lg shadow-md"> {/* shadow-md */}
            <div className="flex justify-between items-center mb-2">
                <h3 className="text-md font-semibold text-slate-700">Historial de Acciones</h3>
                <button onClick={toggleActionHistoryVisibility} className="text-slate-500 hover:text-sky-600 p-1" title={isActionHistoryVisible ? "Ocultar historial" : "Mostrar historial"}>
                    {isActionHistoryVisible ? (
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5"><path strokeLinecap="round" strokeLinejoin="round" d="M2.036 12.322a1.012 1.012 0 010-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178z" /><path strokeLinecap="round" strokeLinejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" /></svg>
                    ) : (
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5"><path strokeLinecap="round" strokeLinejoin="round" d="M3.98 8.223A10.477 10.477 0 001.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.45 10.45 0 0112 4.5c4.756 0 8.773 3.162 10.065 7.498a10.523 10.523 0 01-4.293 5.774M6.228 6.228L3 3m3.228 3.228l3.65 3.65m7.894 7.894L21 21m-3.228-3.228l-3.65-3.65m0 0a3 3 0 10-4.243-4.243m4.242 4.242L9.88 9.88" /></svg>
                    )}
                </button>
            </div>
            {isActionHistoryVisible && <ActionHistoryDisplay history={actionHistory} />}
        </div>
        <div className="bg-slate-50 p-3 sm:p-4 rounded-lg shadow-md flex-grow flex flex-col min-h-[400px] lg:min-h-0"> {/* shadow-md */}
            <div className="flex justify-between items-center mb-2">
                <h3 className="text-lg font-semibold text-slate-700">Asistente de Creación</h3>
                <div className="flex space-x-1.5">
                    <Button onClick={undo} disabled={!canUndo} variant="outline" size="sm" 
                      icon={<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-4 h-4"><path strokeLinecap="round" strokeLinejoin="round" d="M9 15L3 9m0 0l6-6M3 9h12a6 6 0 010 12h-3" /></svg>}
                    >
                        <span className="hidden sm:inline">Deshacer</span>
                    </Button>
                    <Button onClick={redo} disabled={!canRedo} variant="outline" size="sm"
                      icon={<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-4 h-4"><path strokeLinecap="round" strokeLinejoin="round" d="M15 15l6-6m0 0l-6-6m6 6H9a6 6 0 000 12h3" /></svg>}
                    >
                        <span className="hidden sm:inline">Rehacer</span>
                    </Button>
                </div>
            </div>
            
            <div className="flex-grow"> {/* min-h-0 for lg ensures it doesn't overflow parent */}
                <ChatInterface 
                    // key es manejado por el padre CardCreator
                    currentEventDetails={cardState.details} 
                    onDetailsChange={handleDetailsChange}
                    logAction={logAction}
                />
            </div>
        </div>
      </div>

      {/* Columna de Selección de Diseño y Vista Previa */}
      <div className="lg:col-span-2 flex flex-col space-y-4 h-full"> {/* space-y-4 */}
         <div className="bg-slate-50 p-3 sm:p-4 rounded-lg shadow-md"> {/* shadow-md */}
            <DesignSelector selectedDesign={cardState.design} onDesignChange={handleDesignChange} />
        </div>

        {/* Sticky container for preview, might need height adjustments depending on overall layout */}
        <div className="flex-grow relative"> 
          <div className="sticky top-4 lg:top-6"> {/* Adjust top for sticky positioning */}
            <CardPreview cardState={cardState} />
          </div>
        </div>
        
        <div className="bg-slate-50 p-3 sm:p-4 rounded-lg shadow-md mt-auto"> {/* shadow-md */}
            <div className="pt-2 border-t border-slate-200 flex flex-col sm:flex-row space-y-2 sm:space-y-0 sm:space-x-2">
              <Button onClick={() => setIsModalOpen(true)} variant="secondary" size="md" className="flex-1"
                icon={<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5"><path strokeLinecap="round" strokeLinejoin="round" d="M7.5 3.75H6A2.25 2.25 0 003.75 6v1.5M16.5 3.75H18A2.25 2.25 0 0120.25 6v1.5m0 9V18A2.25 2.25 0 0118 20.25h-1.5m-9 0H6A2.25 2.25 0 013.75 18v-1.5M15 12a3 3 0 11-6 0 3 3 0 016 0z" /></svg>}
              >
                Ver Invitación y QR
              </Button>
               <Button onClick={handleSave} variant="primary" size="md" className="flex-1"
                icon={<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5"><path strokeLinecap="round" strokeLinejoin="round" d="M9 3.75H6.912a2.25 2.25 0 00-2.15 1.588L2.35 13.177a2.25 2.25 0 00-.1.661V18a2.25 2.25 0 002.25 2.25h15A2.25 2.25 0 0021.75 18v-4.162c0-.224-.034-.447-.1-.661L19.24 5.338a2.25 2.25 0 00-2.15-1.588H15M2.25 13.5h3.86a2.25 2.25 0 012.012 1.244l.256.512a2.25 2.25 0 002.013 1.244h3.218a2.25 2.25 0 002.013-1.244l.256-.512a2.25 2.25 0 012.012-1.244h3.86M4.5 18h15M12 15V9m0 0l-3 3m3-3l3 3" /></svg>}
              >
                Guardar Invitación
              </Button>
            </div>
        </div>
      </div>

      {isModalOpen && (
        <InvitationModal 
          isOpen={isModalOpen} 
          onClose={() => setIsModalOpen(false)} 
          eventDetails={cardState.details} 
        />
      )}
    </div>
  );
};

export default CardCreator;