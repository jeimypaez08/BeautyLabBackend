package com.example.beautylab.services;

import java.util.List;

import com.example.beautylab.dtos.CambioContraDto;
import com.example.beautylab.dtos.LoginDto;
import com.example.beautylab.dtos.UsuarioRegistroDto;

public interface UsuarioService {

    // Métodos para registro y login
    UsuarioRegistroDto registrarUsuario(UsuarioRegistroDto Dto);
    String login(LoginDto loginDto);

    // listar todos los usuarios (administrador)
    List<UsuarioRegistroDto> listarUsuarios();

    //Buscar por id
    UsuarioRegistroDto obtenerPorId(String id);

    //Buscar por correo
    UsuarioRegistroDto obtenerPorCorreo(String correo);

    //Actualizar perfil (usuario)
    void actualizarUsuario(String id, UsuarioRegistroDto Dto);

    //Eliminar usuario (administrador)
    void eliminarUsuario(String id);

    //Cambiar contraseña (usuario)
    void cambiarContraseña(String id, CambioContraDto contraseñaDto);

    

}
