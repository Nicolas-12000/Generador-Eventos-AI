export interface User {
  email: string;
  // Agrega otros campos de usuario si es necesario
}

export interface EventDetails {
  eventName: string;
  date: string;
  time: string;
  location: string;
  host: string;
  description: string;
  [key: string]: string; // Para permitir campos adicionales dinámicamente
}

export enum DesignId {
  Moderno = 'moderno',
  Clasico = 'clasico',
  Festivo = 'festivo',
}

export interface DesignOption {
  id: DesignId;
  name: string;
  previewImageUrl?: string; // Opcional para miniaturas de vista previa
}

export interface CardState {
  id: string; // Identificador único para cada invitación guardada
  details: EventDetails;
  design: DesignId;
  lastModified: string; // ISO string para saber cuándo se guardó por última vez
}

export interface GroundingChunk {
  web?: {
    uri: string;
    title: string;
  };
  // Add other types of chunks if necessary
}

export interface ActionLogEntry {
  id: string;
  timestamp: Date;
  message: string;
}

export type ActionLogFn = (message: string) => void;