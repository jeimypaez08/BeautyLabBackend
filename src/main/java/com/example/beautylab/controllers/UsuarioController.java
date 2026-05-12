package com.example.beautylab.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.beautylab.dtos.UsuarioRegistroDto;
import com.example.beautylab.services.UsuarioService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



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

    //registro empleados
    @PostMapping("/registrar-empleado")
    // Solo quien tenga el Token con el rol ADMIN puede pasar por aquí
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioRegistroDto> registrarEmpleado(@RequestBody UsuarioRegistroDto dto) {
        // Validar que los roles no sean vacíos
        if(dto.getRoles() == null || dto.getRoles().isEmpty()){
            throw new RuntimeException("Debes asignar al menos un cargo al empleado");
        }
        
        // Aquí el servicio guardará exactamente los roles que el Admin envió, sin forzar a que sea CLIENTE
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(usuarioService.registrarUsuario(dto));
    }
    

    @GetMapping("/listar-clientes")
    @PreAuthorize("hasAuthority('ADMIN')") // Solo el admin puede listar los clientes
    public ResponseEntity<List<UsuarioRegistroDto>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }



    
    

}
