package com.example.beautylab.security;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.beautylab.models.UsuarioAuth;
import com.example.beautylab.repositories.UsuarioAuthRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UsuarioAuthRepository authRepo;
    private final PasswordEncoder passwordEncoder;
    
    public AdminInitializer(UsuarioAuthRepository authRepo, PasswordEncoder passwordEncoder) {
        this.authRepo = authRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(authRepo.findByCorreo("jeimypaez163@gmail.com").isEmpty()){
            UsuarioAuth admin = new UsuarioAuth();
            admin.setCorreo("jeimypaez163@gmail.com");
            admin.setPassword(passwordEncoder.encode("Admin123987*"));
            // Asignar el rol de ADMIN al usuario admin
            admin.setRoles(List.of("ADMIN"));
            admin.setCuentaActiva(true); // cuenta activa del admin
            authRepo.save(admin);
            System.out.println("Usuario admin creado y activado correctamente");
        }
    }
}
