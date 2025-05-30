
import React from 'react';
import { DesignId, DesignOption } from '../../types';
import { DESIGN_OPTIONS } from '../../constants';

interface DesignSelectorProps {
  selectedDesign: DesignId;
  onDesignChange: (designId: DesignId) => void;
}

const DesignSelector: React.FC<DesignSelectorProps> = ({ selectedDesign, onDesignChange }) => {
  return (
    <div className="my-6">
      <h3 className="text-lg font-semibold text-slate-700 mb-3">Elige un Diseño de Tarjeta:</h3>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {DESIGN_OPTIONS.map((option: DesignOption) => (
          <button
            key={option.id}
            onClick={() => onDesignChange(option.id)}
            className={`p-4 border-2 rounded-lg transition-all duration-200 ease-in-out
                        ${selectedDesign === option.id ? 'border-sky-500 ring-2 ring-sky-500 shadow-xl' : 'border-slate-300 hover:border-sky-400 hover:shadow-lg'}
                        focus:outline-none focus:ring-2 focus:ring-sky-500 focus:ring-opacity-50`}
          >
            {option.previewImageUrl && (
                 <img 
                    src={option.previewImageUrl} 
                    alt={`Vista previa ${option.name}`} 
                    className="w-full h-20 object-cover rounded-md mb-2 pointer-events-none" 
                />
            )}
            <p className={`font-medium text-center ${selectedDesign === option.id ? 'text-sky-600' : 'text-slate-700'}`}>
              {option.name}
            </p>
          </button>
        ))}
      </div>
    </div>
  );
};

export default DesignSelector;
