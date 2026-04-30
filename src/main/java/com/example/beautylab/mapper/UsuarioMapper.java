package com.example.beautylab.mapper;

import java.util.List;

import com.example.beautylab.dtos.UsuarioRegistroDto;
import com.example.beautylab.models.Usuario;
import com.example.beautylab.models.UsuarioAuth;

public interface UsuarioMapper {

    //metodo para crear modulo de perfil
    Usuario toPerfilEntity(UsuarioRegistroDto Dto); 
    //metodo para crear el modulo de autenticacion
    UsuarioAuth toAuthEntity(UsuarioRegistroDto Dto);

    //metodo para crear el dto de perfil
    UsuarioRegistroDto toUsuario(Usuario usuario);
    //metodo para crear el dto de autenticacion
    UsuarioRegistroDto toUsuarioAuth(UsuarioAuth usuarioAuth);

    //metodo para actualizar el perfil
    void updatePerfil(UsuarioRegistroDto Dto, Usuario usuario);
    //metodo para actualizar la autenticacion
    void updateAuth(UsuarioRegistroDto Dto, UsuarioAuth usuarioAuth);

    //metodo para listar usuarios
    List<UsuarioRegistroDto> toDtoList(List<Usuario> usuarios);


}
