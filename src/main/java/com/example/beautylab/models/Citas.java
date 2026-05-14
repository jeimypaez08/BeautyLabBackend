package com.example.beautylab.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "citas")
@Data
@Builder
public class Citas {
    @Id
    private String id;

    //relaciones
    private String clienteId; //relacion con cliente
    private String servicioId; //relacion con servicio
    private String empleadoId; //relacion con empleado

    //datos de la cita
    private LocalDateTime fechaHora;
    private String duracionCita; //se toma de servicio
    private Double precioFinal;// se toma de servicio

    //estado de la cita
    private EstadoCita estado; //pendiente, confirmada, cancelada, completada
    private String observaciones; //notas adicionales que el cliente o el admin quieran agregar

}   
