package com.tuevento.generador.service;

public interface ChatService {
    /**
     * Envía un prompt al modelo Gemini y devuelve la respuesta en crudo.
     * @param prompt El texto que describe lo que queremos generar.
     * @return La respuesta en texto (idealmente JSON).
     */
    String ask(String prompt);
}
