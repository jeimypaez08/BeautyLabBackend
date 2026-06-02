package com.example.beautylab.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejoRunTimeException(RuntimeException ex){
        //mostrara el mensaje en la consola de vs
        System.err.println("Error de ejecucion:" + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // 2. Este captura el error de correo duplicado en el registro
    @ExceptionHandler(CorreoExistenteException.class)
    public ResponseEntity<String> manejoCorreoExistente(CorreoExistenteException ex) {
        System.err.println("¡Intento de registro fallido! Error: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
