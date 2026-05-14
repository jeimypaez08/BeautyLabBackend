package com.example.beautylab.services;

import java.util.List;

import com.example.beautylab.dtos.CitaDto;
import com.example.beautylab.models.EstadoCita;

public interface CitaService {

    CitaDto crearCita(CitaDto citaDto);
    List<CitaDto> citasPorCliente(String clienteId);
    List<CitaDto> citasPorEmpleado(String empleadoId);
    CitaDto obtenerCitaPorId(String id);
    void cambiarEstado(String id, EstadoCita nuevoEstado);
    void actualizarCita(String id, CitaDto citaDto);
    void eliminarCita(String id);
}
