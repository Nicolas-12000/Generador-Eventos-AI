package com.tuevento.generador.infraestructure.web;
import java.time.Instant;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tuevento.generador.application.dto.error.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleNotFound(NoSuchElementException ex) {
        log.warn("Recurso no encontrado", ex);
        return ErrorResponseDTO.builder()
            .code("NOT_FOUND")
            .message(ex.getMessage())
            .timestamp(Instant.now())
            .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleBadRequest(IllegalArgumentException ex) {
        log.warn("Solicitud incorrecta", ex);
        return ErrorResponseDTO.builder()
            .code("BAD_REQUEST")
            .message(ex.getMessage())
            .timestamp(Instant.now())
            .build();
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDTO handleForbidden(SecurityException ex) {
        log.warn("Acceso denegado", ex);
        return ErrorResponseDTO.builder()
            .code("FORBIDDEN")
            .message(ex.getMessage())
            .timestamp(Instant.now())
            .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleAll(Exception ex) {
        log.error("Error inesperado", ex);
        return ErrorResponseDTO.builder()
            .code("INTERNAL_SERVER_ERROR")
            .message("Ha ocurrido un error interno. Intente más tarde.")
            .timestamp(Instant.now())
            .build();
    }
}