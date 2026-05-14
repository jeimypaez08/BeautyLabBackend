package com.example.beautylab.dtos;

import java.time.LocalDateTime;

import com.example.beautylab.models.EstadoCita;

import lombok.Data;

@Data
public class CitaDto {
    private String id;
    private String clienteId;
    private String nombreCliente;
    private String servicioId;
    private String nombreServicio;
    private String empleadoId;
    private String nombreEmpleado;
    private LocalDateTime fechaHora;
    private String duracionCita;
    private Double precioFinal;
    private EstadoCita estado;
    private String observaciones;

    // Getters and Setters
}   
