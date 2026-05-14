package com.example.beautylab.dtos;

import java.time.LocalDateTime;

import com.example.beautylab.models.EstadoCita;

import lombok.Data;

@Data
public class CitaDto {
    private String id;
    private String clienteId;
    private String servicioId;
    private String empleadoId;
    private LocalDateTime fechaHora;
    private EstadoCita estado;
    private String observaciones;

    // Getters and Setters
}   
