package com.example.beautylab.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CambioContraDto {

    @NotBlank(message = "La contraseña actual es obligatoria")
    private String contraseñaActual;

    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(min = 8, message = "La nueva contraseña debe contener mínimo 8 caracteres")
    private String nuevaContraseña;
}
