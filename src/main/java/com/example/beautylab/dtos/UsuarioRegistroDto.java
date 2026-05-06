package com.example.beautylab.dtos;

import java.util.List;

import com.example.beautylab.models.Direccion;
import com.example.beautylab.models.Documento;
import com.example.beautylab.models.Rol;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioRegistroDto {

    // Datos de la cuenta(auth)
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Ingresar una direccion de correo valida")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe contener minimo 8 caracteres")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //al momento de hacer el response, no muestra la contraseña
    private String password;

    @Builder.Default
    private Boolean cuentaActiva = true;

    @NotEmpty(message = "Debe asignar 1 rol")
    private List<Rol> roles;

    //datos del perfil
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Valid // Para que valide lo que hay dentro de Documento
    private Documento documento;
    @Valid // Para que valide lo que hay dentro de Direccion
    private Direccion direccion;

    @NotBlank
    private String telefono;
}
