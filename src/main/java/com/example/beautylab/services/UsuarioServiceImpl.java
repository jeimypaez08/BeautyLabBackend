package com.example.beautylab.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.beautylab.dtos.CambioContraDto;
import com.example.beautylab.dtos.UsuarioRegistroDto;
import com.example.beautylab.mapper.UsuarioMapper;
import com.example.beautylab.models.Usuario;
import com.example.beautylab.models.UsuarioAuth;
import com.example.beautylab.repositories.UsuarioAuthRepository;
import com.example.beautylab.repositories.UsuarioRepository;



@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository userRepo;

    private final UsuarioAuthRepository authRepo;

    private final UsuarioMapper usermapper;
  
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository userRepo, UsuarioAuthRepository authRepo, 
                              UsuarioMapper usermapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
        this.usermapper = usermapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UsuarioRegistroDto registrarUsuario(UsuarioRegistroDto Dto){
        if (authRepo.findByCorreo(Dto.getCorreo()).isPresent()) {
        throw new RuntimeException("El correo ya está registrado, intenta con otro.");
    }

        //mapeo a entidades
        UsuarioAuth auth= usermapper.toAuthEntity(Dto);
        Usuario perfil= usermapper.toPerfilEntity(Dto);

        //encriptar contraseña
        auth.setPassword(passwordEncoder.encode(auth.getPassword()));

        //guardar en BD
        UsuarioAuth savedAuth = authRepo.save(auth);
        perfil.setId(savedAuth.getId()); // Asegura que el perfil tenga el mismo id que la autenticación
        userRepo.save(perfil);

        return Dto;
    }

    @Override
    //obtener usuarios
    public List<UsuarioRegistroDto> listarUsuarios(){
        return usermapper.toDtoList(userRepo.findAll());
    }

    @Override
    //obtener por id
    public UsuarioRegistroDto obtenerPorId(String id){
        Usuario usuario = userRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usermapper.toUsuario(usuario);
    }

    @Override
    //obtener por correo
    public UsuarioRegistroDto obtenerPorCorreo(String correo){
        Usuario usuario = userRepo.findByCorreo(correo)
        .orElseThrow(() -> new RuntimeException("Correo no registrado"));
        return usermapper.toUsuario(usuario);
    }

    @Override
    //actualizar usuario
    @Transactional
    public void actualizarUsuario(String id, UsuarioRegistroDto Dto){
        Usuario usuario = userRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        UsuarioAuth auth = authRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Cuenta no encontrado"));

        usermapper.updatePerfil(Dto, usuario);
        usermapper.updateAuth(Dto, auth);

        userRepo.save(usuario);
        authRepo.save(auth);
    }


    @Override
    //cambiar contraseña
    public void cambiarContraseña(String id, CambioContraDto contraseñaDto){
        UsuarioAuth auth = authRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(!passwordEncoder.matches(contraseñaDto.getContraseñaActual(), auth.getPassword())){
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        auth.setPassword(passwordEncoder.encode(contraseñaDto.getNuevaContraseña()));
        authRepo.save(auth);
    }

    @Override
    //eliminar usuario
    public void eliminarUsuario(String id){
        authRepo.deleteById(id);
        userRepo.deleteById(id);
    }
}
