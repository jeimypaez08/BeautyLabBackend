package com.example.beautylab.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.beautylab.models.UsuarioAuth;

@Repository
public interface UsuarioAuthRepository extends MongoRepository<UsuarioAuth, String> {

    // Este método es vital para el login
    Optional<UsuarioAuth> findByCorreo(String correo);

}
