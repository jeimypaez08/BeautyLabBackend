package com.example.beautylab.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "servicios")
@Data
@Builder
public class Servicio {
    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String duracion;
    private List<Especialidades> categoria;
    private String imagenUrl;
    private Boolean disponible;

}
