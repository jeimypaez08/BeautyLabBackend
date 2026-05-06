package com.example.beautylab.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CorreoExistenteException.class)
    public ResponseEntity<String> manejoCorreoExistente(CorreoExistenteException ex) {
        System.err.println("¡Intento de registro fallido! Error: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
