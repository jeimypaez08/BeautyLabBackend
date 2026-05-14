package com.example.beautylab.services;

import java.util.List;

import com.example.beautylab.dtos.ServicioDto;
import com.example.beautylab.models.Especialidades;

public interface ServicioService {
    ServicioDto crearServicio(ServicioDto servicioDto);
    List<ServicioDto> listarServicios();
    List<ServicioDto> listarDisponibles();
    ServicioDto obtenerPorId(String id);
    void actualizarServicio(String id, ServicioDto servicioDto);
    void eliminarServicio(String id);
    List<ServicioDto> filtrarPorCategoria(Especialidades categoria);

}
