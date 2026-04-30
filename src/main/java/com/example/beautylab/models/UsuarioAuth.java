package com.example.beautylab.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "usuariosAuth")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsuarioAuth {

    @Id
    private String id; // ID único para cada usuario el mismo del perfil

    @NotBlank
    @Email
    @Indexed(unique = true)
    private String correo; // dato de usuario para autenticación

    @NotBlank
    private String password; // dato de contraseña para autenticación encriptada(bcrypt)

    @NotNull
    private List<Rol> roles; // lista de roles usando enum
    @Builder.Default
    private boolean cuentaActiva = true;


}
