package com.example.beautylab.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.beautylab.dtos.UsuarioRegistroDto;
import com.example.beautylab.services.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioRegistroDto> registrarUsuario(@RequestBody UsuarioRegistroDto Dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(usuarioService.registrarUsuario(Dto));
    }
    

}
