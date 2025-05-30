
import React from 'react';
import { CardState } from '../../types';
import TemplateA from '../card_templates/TemplateA';
import TemplateB from '../card_templates/TemplateB';
import TemplateC from '../card_templates/TemplateC';
import { DESIGN_OPTIONS } from '../../constants'; // For getting design name

interface CardPreviewProps {
  cardState: CardState;
}

const CardPreview: React.FC<CardPreviewProps> = ({ cardState }) => {
  const { details, design } = cardState;

  const renderTemplate = () => {
    switch (design) {
      case 'moderno':
        return <TemplateA details={details} />;
      case 'clasico':
        return <TemplateB details={details} />;
      case 'festivo':
        return <TemplateC details={details} />;
      default:
        return <TemplateA details={details} />; // Default to Modern
    }
  };

  const selectedDesignName = DESIGN_OPTIONS.find(d => d.id === design)?.name || 'Diseño';

  return (
    <div className="mt-8 p-6 bg-slate-200 rounded-xl shadow-inner">
      <h3 className="text-xl font-semibold text-slate-700 mb-4">Vista Previa ({selectedDesignName})</h3>
      <div className="flex justify-center items-center min-h-[300px]">
        {renderTemplate()}
      </div>
    </div>
  );
};

export default CardPreview;
