package com.example.beautylab.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.beautylab.dtos.ServicioDto;
import com.example.beautylab.mapper.ServicioMapper;
import com.example.beautylab.models.Especialidades;
import com.example.beautylab.models.Servicio;
import com.example.beautylab.repositories.ServicioRepository;

@Service
public class ServicioServiceImpl implements ServicioService{
    private final ServicioRepository servicioRepo;
    private final ServicioMapper servicioMapper;

    public ServicioServiceImpl(ServicioRepository servicioRepo,
                               ServicioMapper servicioMapper) {
        this.servicioRepo = servicioRepo;
        this.servicioMapper = servicioMapper;
    }

    @Override
    @Transactional
    public ServicioDto crearServicio(ServicioDto dto){
        Servicio servicio = servicioMapper.toEntity(dto);
        //forzar el servicio a estar activo al crearlo
        servicio.setDisponible(true);
        Servicio guardado = servicioRepo.save(servicio);
        return servicioMapper.toDto(guardado);
    }

    @Override
    public List<ServicioDto> listarServicios(){
        return servicioMapper.toDtoList(servicioRepo.findAll());
    }

    @Override
    public List<ServicioDto> listarDisponibles(){
        //filtar solo los que tienen true
        List<Servicio> disponibles = servicioRepo.findAll().stream()
        .filter(s -> Boolean.TRUE.equals(s.getDisponible()))
        .toList();
        return servicioMapper.toDtoList(disponibles);
    }

    @Override
    public ServicioDto obtenerPorId(String id){
        Servicio servicio = servicioRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));
        return servicioMapper.toDto(servicio);
    }

    @Override
    @Transactional
    public void actualizarServicio(String id, ServicioDto dto){
        Servicio servicio = servicioRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));
        //usar el mapper para actualizar solo los campos que vienen en el dto (si el campo es null, no se actualiza)
        servicioMapper.updateServicio(dto, servicio);

        servicioRepo.save(servicio);
    }
    @Override
    public void eliminarServicio(String id) {
        if (!servicioRepo.existsById(id)) {
            throw new RuntimeException("El servicio no existe");
        }
        servicioRepo.deleteById(id);
    }

    @Override
    public List<ServicioDto> filtrarPorCategoria(Especialidades categoria) {
        List<Servicio> servicios = servicioRepo.findByCategoria(categoria);
        return servicioMapper.toDtoList(servicios);
    }

}
