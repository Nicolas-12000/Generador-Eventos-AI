import React, { useRef, useEffect } from 'react';
import { ActionLogEntry } from '../../types';

interface ActionHistoryDisplayProps {
  history: ActionLogEntry[];
}

const ActionHistoryDisplay: React.FC<ActionHistoryDisplayProps> = ({ history }) => {
  const historyEndRef = useRef<HTMLDivElement>(null);

  const scrollToBottom = () => {
    historyEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(scrollToBottom, [history]);

  if (history.length === 0) {
    return (
      <div className="p-3 text-center text-sm text-slate-500">
        No hay acciones registradas todavía.
      </div>
    );
  }

  return (
    <div className="h-48 overflow-y-auto bg-slate-100 p-3 rounded-md border border-slate-200 space-y-2 text-xs">
      {history.map((entry) => (
        <div key={entry.id} className="text-slate-600">
          <span className="font-medium text-slate-400">
            [{entry.timestamp.toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit', second: '2-digit' })}]
          </span>: {entry.message}
        </div>
      ))}
      <div ref={historyEndRef} />
    </div>
  );
};

export default ActionHistoryDisplay;