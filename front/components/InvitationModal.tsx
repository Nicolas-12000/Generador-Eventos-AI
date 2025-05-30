import React from 'react';
// Fix: Changed import from default 'QRCode' to named 'QRCodeCanvas' from 'qrcode.react' and updated usage.
import { QRCodeCanvas } from 'qrcode.react';
import { EventDetails } from '../types';
import Modal from './ui/Modal';
import Button from './ui/Button';
import { QR_CODE_DEFAULT_SIZE } from '../constants';

interface InvitationModalProps {
  isOpen: boolean;
  onClose: () => void;
  eventDetails: EventDetails;
}

const InvitationModal: React.FC<InvitationModalProps> = ({ isOpen, onClose, eventDetails }) => {
  const { eventName, date, time, location } = eventDetails;

  // Construct Google Maps URL. For a robust solution, consider URL encoding location.
  // This is a simplified example. A more robust solution would use Geocoding API to get lat/lon first.
  const googleMapsUrl = `https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(location)}`;

  const handleDownloadQR = () => {
    const canvas = document.getElementById('event-qr-code') as HTMLCanvasElement;
    if (canvas) {
      const pngUrl = canvas
        .toDataURL("image/png")
        .replace("image/png", "image/octet-stream");
      let downloadLink = document.createElement("a");
      downloadLink.href = pngUrl;
      downloadLink.download = `${eventName}_QR_Code.png`;
      document.body.appendChild(downloadLink);
      downloadLink.click();
      document.body.removeChild(downloadLink);
    }
  };
  
  return (
    <Modal isOpen={isOpen} onClose={onClose} title={`Invitación: ${eventName}`} size="lg">
      <div className="space-y-4 text-slate-700">
        <p><strong>Evento:</strong> {eventName}</p>
        <p><strong>Fecha:</strong> {new Date(date + 'T00:00:00').toLocaleDateString('es-ES', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}</p>
        <p><strong>Hora:</strong> {time}</p>
        <p><strong>Ubicación:</strong> {location}</p>
        
        <div className="mt-6 text-center">
          <h4 className="text-lg font-semibold mb-2 text-slate-800">Escanea para la Ubicación:</h4>
          <div className="flex justify-center items-center p-4 bg-slate-100 rounded-lg">
            {/* Fix: Used QRCodeCanvas component instead of QRCode */}
            {/* Fix: Removed incomplete imageSettings prop. If a logo is desired in the QR code, this prop can be re-added with valid 'src', 'height', 'width', and 'excavate' properties. */}
            <QRCodeCanvas 
                id="event-qr-code"
                value={googleMapsUrl} 
                size={QR_CODE_DEFAULT_SIZE} 
                level="H" 
                includeMargin={true}
            />
          </div>
          <p className="text-xs text-slate-500 mt-2">
            Abre este código QR con tu móvil para ver la ubicación en Google Maps.
          </p>
          <Button onClick={handleDownloadQR} variant="outline" size="sm" className="mt-4">
            Descargar QR
          </Button>
        </div>

        <div className="mt-8 flex justify-end">
          <Button onClick={onClose} variant="primary">
            Cerrar
          </Button>
        </div>
      </div>
    </Modal>
  );
};

export default InvitationModal;