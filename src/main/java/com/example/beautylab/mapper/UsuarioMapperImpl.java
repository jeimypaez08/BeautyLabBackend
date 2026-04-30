package com.example.beautylab.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.beautylab.dtos.UsuarioRegistroDto;
import com.example.beautylab.models.Usuario;
import com.example.beautylab.models.UsuarioAuth;

@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioAuth toAuthEntity(UsuarioRegistroDto Dto){
        if(Dto == null)
            return null;

        return UsuarioAuth.builder()
        .correo(Dto.getCorreo())
        .password(Dto.getPassword()) // Se encriptará en el Service
        .roles(Dto.getRoles())
        .cuentaActiva(true)
        .build();
    }

    @Override
    public Usuario toPerfilEntity(UsuarioRegistroDto Dto){
        if(Dto == null)
            return null;

        return Usuario.builder()
        .documento(Dto.getDocumento())
        .nombre(Dto.getNombre())
        .apellido(Dto.getApellido())
        .correo(Dto.getCorreo())
        .telefono(Dto.getTelefono())
        .direccion(Dto.getDireccion())
        .build();
    }

    @Override
    public UsuarioRegistroDto toUsuario(Usuario usuario){
        if(usuario == null)
            return null;
        return UsuarioRegistroDto.builder()
        .documento(usuario.getDocumento())
        .nombre(usuario.getNombre())
        .apellido(usuario.getApellido())
        .correo(usuario.getCorreo())
        .telefono(usuario.getTelefono())
        .direccion(usuario.getDireccion())
        .build();
    }

    @Override
    public UsuarioRegistroDto toUsuarioAuth(UsuarioAuth usuarioAuth){
        if(usuarioAuth == null)
            return null;
        return UsuarioRegistroDto.builder()
        .correo(usuarioAuth.getCorreo())
        .password(usuarioAuth.getPassword())
        .roles(usuarioAuth.getRoles())
        .cuentaActiva(true)
        .build();
}

    @Override
    public void updatePerfil(UsuarioRegistroDto Dto, Usuario usuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePerfil'");
    }

    @Override
    public void updateAuth(UsuarioRegistroDto Dto, UsuarioAuth usuarioAuth) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAuth'");
    }

    @Override
    public List<UsuarioRegistroDto> toDtoList(List<Usuario> usuarios) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDtoList'");
    }
}
