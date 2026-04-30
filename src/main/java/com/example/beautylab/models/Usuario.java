package com.example.beautylab.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "usuariosPerfil")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    @Id
    private String id; // ID único para cada usuario el mismo del autentificación
    @NotBlank  
    private String nombre;
    @NotBlank
    private String apellido;
    @NotNull
    @Valid
    private Documento documento; // documento de identidad del usuario objeto con tipo y número
    private Direccion direccion; // dirección del usuario objeto con calle, numero, ciudad, y código postal
    // private List<Direccion> dire; // lista de direcciones
    @NotBlank
    private String correo; // correo electrónico del usuario
    @NotBlank
    private String telefono; // número de teléfono del usuario

}
