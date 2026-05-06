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
        .cuentaActiva(Dto.getCuentaActiva())
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
        .cuentaActiva(usuarioAuth.getCuentaActiva())
        .build();
}

    @Override
    public void updatePerfil(UsuarioRegistroDto Dto, Usuario usuario) {
        if(Dto == null || usuario == null)
            return;

        if(usuario.getNombre() !=null) usuario.setNombre(Dto.getNombre());
        if(usuario.getApellido() !=null) usuario.setApellido(Dto.getApellido());
        if(usuario.getDocumento() !=null) usuario.setDocumento(Dto.getDocumento());
        if(usuario.getCorreo() !=null) usuario.setCorreo(Dto.getCorreo());
        if(usuario.getTelefono() !=null) usuario.setTelefono(Dto.getTelefono());
        if(usuario.getDireccion() !=null) usuario.setDireccion(Dto.getDireccion());
    }

    @Override
    public void updateAuth(UsuarioRegistroDto Dto, UsuarioAuth usuarioAuth) {
        if(Dto == null || usuarioAuth == null)
            return;

        if(usuarioAuth.getCorreo() !=null) usuarioAuth.setCorreo(Dto.getCorreo());
        if(usuarioAuth.getPassword() !=null) usuarioAuth.setPassword(Dto.getPassword()); // Se encriptará en el Service
        if(usuarioAuth.getRoles() !=null) usuarioAuth.setRoles(Dto.getRoles());
        usuarioAuth.setCuentaActiva(Dto.getCuentaActiva());
    }

    @Override
    public List<UsuarioRegistroDto> toDtoList(List<Usuario> usuarios) {
        if(usuarios == null)
            return null;

        return usuarios.stream()
        .map(this::toUsuario)
        .toList();
    }
}
