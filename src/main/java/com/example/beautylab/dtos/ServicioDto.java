package com.example.beautylab.dtos;

import java.util.List;

import com.example.beautylab.models.Especialidades;

import lombok.Data;

@Data
public class ServicioDto {
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String duracion;
    private List<Especialidades> categoria;
    private String imagenUrl;
    private Boolean disponible;

}
