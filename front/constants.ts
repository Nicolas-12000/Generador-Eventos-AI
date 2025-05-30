import { DesignId, DesignOption, EventDetails, CardState } from './types';

export const DEFAULT_EVENT_DETAILS: EventDetails = {
  eventName: 'Mi Evento Especial',
  date: new Date().toISOString().split('T')[0],
  time: '18:00',
  location: 'Salón Principal, Calle Falsa 123',
  host: 'Anfitrión Anónimo',
  description: '¡Estás invitado a una celebración inolvidable llena de alegría y diversión!',
};

export const DESIGN_OPTIONS: DesignOption[] = [
  { id: DesignId.Moderno, name: 'Diseño Moderno', previewImageUrl: 'https://picsum.photos/seed/moderno/100/70' },
  { id: DesignId.Clasico, name: 'Diseño Clásico', previewImageUrl: 'https://picsum.photos/seed/clasico/100/70' },
  { id: DesignId.Festivo, name: 'Diseño Festivo', previewImageUrl: 'https://picsum.photos/seed/festivo/100/70' },
];

export const GEMINI_TEXT_MODEL = 'gemini-2.5-flash-preview-04-17';
export const GEMINI_IMAGE_MODEL = 'imagen-3.0-generate-002'; 

export const QR_CODE_DEFAULT_SIZE = 256;

export const generateNewCardState = (details?: Partial<EventDetails>, design?: DesignId): CardState => {
  const now = new Date().toISOString();
  return {
    id: Date.now().toString(), // ID simple basado en timestamp
    details: {
      ...DEFAULT_EVENT_DETAILS,
      ...(details || {}),
    },
    design: design || DesignId.Moderno,
    lastModified: now,
  };
};