import React, { useState, useEffect, useCallback, useRef, FormEvent } from 'react';
import { EventDetails, ActionLogFn } from '../../types';
import Button from '../ui/Button';
// import Input from '../ui/Input'; // Input no se usa directamente, se usa un <input> estándar
import { generateEventDescription, GeminiResponse } from '../../services/geminiService';
import { DEFAULT_EVENT_DETAILS } from '../../constants';

interface ChatMessage {
  id: string;
  text: string;
  sender: 'user' | 'bot';
  type?: 'text' | 'options'; // Para ofrecer opciones al usuario
  options?: Array<{ text: string, value: string }>;
}

interface ChatInterfaceProps {
  currentEventDetails: EventDetails;
  onDetailsChange: (newDetails: EventDetails) => void;
  logAction: ActionLogFn; // Nueva prop
}

type ConversationStage = 
  | 'initial'
  | 'awaiting_event_name'
  | 'awaiting_date'
  | 'awaiting_time'
  | 'awaiting_location'
  | 'awaiting_host'
  | 'awaiting_description_choice' 
  | 'awaiting_description_manual' 
  | 'ai_generating_description'
  | 'finalizing'
  | 'completed';

const fieldConfiguration: Array<{
  key: keyof EventDetails;
  question: string;
  nextStage: ConversationStage;
  validation?: (value: string) => string | null;
}> = [
  { key: 'eventName', question: '¡Hola! Estoy aquí para ayudarte a crear tu invitación. Primero, ¿cómo se llamará tu evento?', nextStage: 'awaiting_date' },
  { key: 'date', question: '¡Suena genial! ¿En qué fecha será el evento? (YYYY-MM-DD)', nextStage: 'awaiting_time' },
  { key: 'time', question: 'Perfecto. ¿A qué hora comienza? (HH:MM)', nextStage: 'awaiting_location' },
  { key: 'location', question: 'Entendido. ¿Dónde se llevará a cabo?', nextStage: 'awaiting_host' },
  { key: 'host', question: 'Casi terminamos con los detalles básicos. ¿Quién será el anfitrión u organizador?', nextStage: 'awaiting_description_choice' },
];


const ChatInterface: React.FC<ChatInterfaceProps> = ({ currentEventDetails, onDetailsChange, logAction }) => {
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [userInput, setUserInput] = useState('');
  const [currentStage, setCurrentStage] = useState<ConversationStage>('initial');
  const [isLoadingBotResponse, setIsLoadingBotResponse] = useState(false);
  const messagesEndRef = useRef<HTMLDivElement>(null);
  
  const [localEventDetails, setLocalEventDetails] = useState<EventDetails>({...currentEventDetails});

  const addMessage = (text: string, sender: 'user' | 'bot', type?: ChatMessage['type'], options?: ChatMessage['options']) => {
    setMessages(prev => [...prev, { id: Date.now().toString() + Math.random(), text, sender, type, options }]);
  };

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(scrollToBottom, [messages]);

  const processUserInput = useCallback(async (inputValue: string) => {
    addMessage(inputValue, 'user');
    logAction(`Usuario envió: "${inputValue}"`);
    setUserInput('');
    setIsLoadingBotResponse(true);

    let newDetails = { ...localEventDetails };
    let nextBotMessage = "No entendí bien. ¿Podrías repetirlo?";
    let nextStage = currentStage;

    const currentFieldConfig = fieldConfiguration.find(f => f.nextStage === currentStage); // Should be currentStage === f.nextStage logic to find the config for the *current question*

    if (currentFieldConfig) {
        newDetails = { ...newDetails, [currentFieldConfig.key]: inputValue };
        setLocalEventDetails(newDetails); 
        onDetailsChange(newDetails); 
        // logAction ya se hizo para la entrada del usuario, onDetailsChange en CardCreator hará log del cambio específico.
        
        const nextQuestionIndex = fieldConfiguration.indexOf(currentFieldConfig) + 1;
        if (nextQuestionIndex < fieldConfiguration.length) {
            nextBotMessage = fieldConfiguration[nextQuestionIndex].question;
            nextStage = fieldConfiguration[nextQuestionIndex].nextStage;
        } else {
            nextBotMessage = "¿Quieres escribir una descripción para el evento o prefieres que te sugiera una con IA?";
            nextStage = 'awaiting_description_choice';
        }
    } else if (currentStage === 'awaiting_description_choice') {
        if (inputValue.toLowerCase().includes('escribir') || inputValue.toLowerCase().includes('manual')) {
            nextBotMessage = "¡Claro! Por favor, escribe la descripción de tu evento.";
            nextStage = 'awaiting_description_manual';
        } else if (inputValue.toLowerCase().includes('sugerir') || inputValue.toLowerCase().includes('ia') || inputValue.toLowerCase().includes('ai')) {
            if (!newDetails.eventName) {
                 nextBotMessage = "Necesito el nombre del evento para generar una descripción. ¿Podrías dármelo primero?";
                 // No cambia de stage, espera que el usuario proporcione el nombre o se corrija.
            } else {
                nextBotMessage = `Ok, voy a generar una descripción para "${newDetails.eventName}"...`;
                nextStage = 'ai_generating_description';
                logAction(`Solicitando descripción con IA para: "${newDetails.eventName}"`);
                
                setTimeout(() => addMessage(nextBotMessage, 'bot'), 100);

                try {
                    const aiResponse: GeminiResponse = await generateEventDescription(newDetails.eventName);
                    newDetails = { ...newDetails, description: aiResponse.text };
                    setLocalEventDetails(newDetails);
                    onDetailsChange(newDetails); // Esto llamará a logAction en CardCreator
                    logAction(`Descripción IA generada: "${aiResponse.text}"`);
                    nextBotMessage = `Aquí tienes una sugerencia: "${aiResponse.text}". ¿Te gusta o quieres ajustarla? (Puedes editarla en la vista previa o decirme si quieres otra).`;
                    nextStage = 'finalizing';
                } catch (error) {
                    console.error("Error al sugerir descripción:", error);
                    logAction("Error al generar descripción con IA.");
                    nextBotMessage = "Lo siento, tuve un problema al generar la descripción. ¿Quieres intentarlo de nuevo o escribirla tú?";
                    nextStage = 'awaiting_description_choice';
                }
            }
        } else {
            nextBotMessage = "Por favor, dime si prefieres 'escribir' la descripción o que te la 'sugiera'.";
            nextStage = 'awaiting_description_choice';
        }
    } else if (currentStage === 'awaiting_description_manual') {
        newDetails = { ...newDetails, description: inputValue };
        setLocalEventDetails(newDetails);
        onDetailsChange(newDetails); // Esto llamará a logAction en CardCreator
        nextBotMessage = "¡Descripción guardada! Ya tienes todos los detalles. Puedes ver la vista previa y seleccionar un diseño.";
        nextStage = 'completed';
    } else if (currentStage === 'finalizing' || currentStage === 'completed') {
        nextBotMessage = "Ya hemos configurado los detalles básicos. Puedes ajustar el diseño o generar la invitación. Si quieres cambiar algo, puedes decírmelo o usar los botones de Deshacer/Rehacer.";
    }


    setTimeout(() => {
        logAction(`Bot respondió: "${nextBotMessage.substring(0, 50)}${nextBotMessage.length > 50 ? '...' : ''}"`);
        if (nextStage === 'awaiting_description_choice' && currentStage !== 'awaiting_description_choice' ) {
             addMessage(nextBotMessage, 'bot', 'options', [
                { text: 'Escribir descripción', value: 'escribir' },
                { text: 'Sugerir con IA', value: 'sugerir' }
            ]);
        } else {
            addMessage(nextBotMessage, 'bot');
        }
        setCurrentStage(nextStage);
        setIsLoadingBotResponse(false);
    }, 500 + Math.random() * 500); 

  }, [currentStage, localEventDetails, onDetailsChange, logAction]);


  useEffect(() => {
    if (currentStage === 'initial' && messages.length === 0) {
      setLocalEventDetails({...DEFAULT_EVENT_DETAILS}); 
      // onDetailsChange({...DEFAULT_EVENT_DETAILS}); // No llamar aquí para evitar log redundante al inicio
      setTimeout(() => {
        const firstQuestion = fieldConfiguration[0].question;
        addMessage(firstQuestion, 'bot');
        logAction(`Bot inició conversación: "${firstQuestion.substring(0,50)}..."`);
        setCurrentStage(fieldConfiguration[0].nextStage);
      }, 200);
    }
  }, [currentStage, messages.length, logAction]); // quitado onDetailsChange de dependencias
  
  useEffect(() => {
    const isDefault = JSON.stringify(currentEventDetails) === JSON.stringify(DEFAULT_EVENT_DETAILS);
    const hasMessages = messages.length > 0;

    if (isDefault && currentStage !== 'initial' && hasMessages) { // Reset forzado desde CardCreator
        logAction("Chat reiniciado debido a reseteo de tarjeta.");
        setMessages([]);
        setCurrentStage('initial'); 
    } else if (JSON.stringify(currentEventDetails) !== JSON.stringify(localEventDetails)) {
        // Sincronizar si CardCreator cambió los detalles (ej. undo/redo)
        logAction("Detalles del chat sincronizados desde estado global.");
        setLocalEventDetails({...currentEventDetails});
         // Podríamos necesitar re-evaluar el stage si los detalles cambiaron mucho.
         // Por ahora, solo sincroniza los datos. El usuario continuará desde el stage actual.
    }
  }, [currentEventDetails, logAction]); // quitado localEventDetails y messages.length


  const handleUserInputSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (userInput.trim() && !isLoadingBotResponse) {
      processUserInput(userInput.trim());
    }
  };

  const handleOptionClick = (value: string) => {
    if (!isLoadingBotResponse) {
        processUserInput(value);
    }
  };

  return (
    <div className="flex flex-col h-full bg-white rounded-lg shadow-inner overflow-hidden border border-slate-200">
      <div className="flex-grow p-3 space-y-3 overflow-y-auto bg-slate-50">
        {messages.map((msg) => (
          <div key={msg.id} className={`flex ${msg.sender === 'user' ? 'justify-end' : 'justify-start'}`}>
            <div
              className={`max-w-[80%] p-2.5 rounded-xl shadow-sm text-sm ${
                msg.sender === 'user'
                  ? 'bg-sky-600 text-white rounded-br-none'
                  : 'bg-slate-200 text-slate-800 rounded-bl-none'
              }`}
            >
              <p>{msg.text}</p>
              {msg.type === 'options' && msg.options && (
                <div className="mt-2 space-y-1.5">
                    {msg.options.map(opt => (
                        <button 
                            key={opt.value}
                            onClick={() => handleOptionClick(opt.value)}
                            className="block w-full text-left text-xs bg-white hover:bg-sky-100 text-sky-700 font-medium p-2 rounded-md border border-sky-200 transition-colors"
                            disabled={isLoadingBotResponse}
                        >
                            {opt.text}
                        </button>
                    ))}
                </div>
              )}
            </div>
          </div>
        ))}
        {isLoadingBotResponse && (
          <div className="flex justify-start">
            <div className="max-w-[80%] p-2.5 rounded-xl shadow-sm bg-slate-200 text-slate-800 rounded-bl-none">
              <p className="text-sm italic">Bot está escribiendo...</p>
            </div>
          </div>
        )}
        <div ref={messagesEndRef} />
      </div>
      <form onSubmit={handleUserInputSubmit} className="p-3 border-t border-slate-300 bg-slate-100">
        <div className="flex space-x-2">
          <input
            type="text"
            value={userInput}
            onChange={(e) => setUserInput(e.target.value)}
            placeholder={isLoadingBotResponse ? "Esperando respuesta del bot..." : "Escribe tu mensaje..."}
            className="flex-grow p-2.5 border border-slate-300 rounded-lg focus:ring-2 focus:ring-sky-500 focus:border-sky-500 outline-none transition-all text-sm"
            disabled={isLoadingBotResponse || currentStage === 'completed'}
            aria-label="Mensaje para el chatbot"
          />
          <Button 
            type="submit" 
            variant="primary" 
            disabled={isLoadingBotResponse || !userInput.trim() || currentStage === 'completed'}
            icon={<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5"><path strokeLinecap="round" strokeLinejoin="round" d="M6 12L3.269 3.126A59.768 59.768 0 0121.485 12 59.77 59.77 0 013.27 20.876L5.999 12zm0 0h7.5" /></svg>}
            aria-label="Enviar mensaje"
          >
            <span className="hidden sm:inline">Enviar</span>
          </Button>
        </div>
      </form>
    </div>
  );
};

export default ChatInterface;