// src/App.jsx
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ChatPage from './pages/ChatPage';
import PreviewPage from './pages/PreviewPage';
import './App.css';

// Añade export default aquí
export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<ChatPage />} />
        <Route path="/preview/:id" element={<PreviewPage />} />
      </Routes>
    </BrowserRouter>
  );
}