package com.example.beautylab.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.beautylab.models.Documento;
import com.example.beautylab.models.Usuario;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByDocumento(Documento documento);
    Optional<Usuario> findByCorreo(String correo);
    
    // Busca dentro del objeto anidado 'doc' el campo 'numero'
    Optional<Usuario> findByDoc_Numero(String numero);

}
