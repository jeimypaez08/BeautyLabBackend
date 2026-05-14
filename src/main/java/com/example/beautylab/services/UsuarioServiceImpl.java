package com.example.beautylab.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.beautylab.dtos.CambioContraDto;
import com.example.beautylab.dtos.LoginDto;
import com.example.beautylab.dtos.UsuarioRegistroDto;
import com.example.beautylab.exceptions.CorreoExistenteException;
import com.example.beautylab.mapper.UsuarioMapper;
import com.example.beautylab.models.Usuario;
import com.example.beautylab.models.UsuarioAuth;
import com.example.beautylab.repositories.UsuarioAuthRepository;
import com.example.beautylab.repositories.UsuarioRepository;
import com.example.beautylab.security.JwtService;




@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository userRepo;

    private final UsuarioAuthRepository authRepo;

    private final UsuarioMapper usermapper;
  
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository userRepo, 
                              UsuarioAuthRepository authRepo, 
                              UsuarioMapper usermapper, 
                              PasswordEncoder passwordEncoder,
                            JwtService jwtService) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
        this.usermapper = usermapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public UsuarioRegistroDto registrarUsuario(UsuarioRegistroDto dto){
        if (authRepo.findByCorreo(dto.getCorreo()).isPresent()) {
        throw new CorreoExistenteException("El correo " + dto.getCorreo() + " ya está registrado, intenta con otro.");
    }
     // Si el registro NO trae roles, se asigna CLIENTE por defecto
    if(dto.getRoles() == null || dto.getRoles().isEmpty()){
        dto.setRoles(List.of("CLIENTE"));
    }

        if(dto.getRoles().contains("CLIENTE")){
            dto.setEspecialidades(null); // Si el rol es CLIENTE, aseguramos que especialidades sea null
        }

    //mapeo a entidades
        UsuarioAuth auth= usermapper.toAuthEntity(dto);
        Usuario perfil= usermapper.toPerfilEntity(dto);
        
   

    // Si el rol es ADMIN o EMPLEADO, la cuenta se activa automáticamente, de lo contrario (CLIENTE) queda inactiva para validar correo
    if(dto.getRoles().contains("ADMIN") || dto.getRoles().contains("EMPLEADO")){
        auth.setCuentaActiva(true);
    }else{
        auth.setCuentaActiva(false); // Clientes empiezan en false para validar correo  
    }

        //encriptar contraseña
        auth.setPassword(passwordEncoder.encode(auth.getPassword()));

        //guardar en BD
        UsuarioAuth savedAuth = authRepo.save(auth); //Guardar primero la autenticación para obtener el ID
        perfil.setId(savedAuth.getId()); // Asegura que el perfil tenga el mismo id que la autenticación
        userRepo.save(perfil);

        //Respuesta: Mapear de vuelta a DTO desde lo que se guardó en BD
       // Esto asegura que el JSON de respuesta muestre el ID y el cuentaActiva real
       UsuarioRegistroDto respuesta = usermapper.toUsuario(perfil); // Trae datos del perfil
       respuesta.setRoles(savedAuth.getRoles());
       respuesta.setCuentaActiva(savedAuth.getCuentaActiva()); // Aquí ya no será null

        return respuesta;
    }

    private final JwtService jwtService;

    @Override
    //login
    public String login(LoginDto loginDto){
        //buscar usuario por correo
        UsuarioAuth usuarioAuth = authRepo.findByCorreo(loginDto.getCorreo())
            .orElseThrow(() -> new RuntimeException("Correo no registrado"));

        //verficar si la cuenta está activa
        if(!Boolean.TRUE.equals(usuarioAuth.getCuentaActiva())){
            throw new RuntimeException("La cuenta no está activa, por favor verifica tu correo");
        }

        //verificar contraseña
        if (!passwordEncoder.matches(loginDto.getPassword(), usuarioAuth.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        //generar token JWT, se incluye el rol del usuario para que pueda ser usado en la autorizacion de rutas protegidas
        String userRole = usuarioAuth.getRoles().get(0).toString();

        return jwtService.generarToken(usuarioAuth.getCorreo(), userRole);
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
    public void actualizarUsuario(String id, UsuarioRegistroDto dto){
        Usuario usuario = userRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        UsuarioAuth auth = authRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Cuenta no encontrado"));

        usermapper.updatePerfil(dto, usuario);
        usermapper.updateAuth(dto, auth);

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
