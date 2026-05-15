package com.example.beautylab.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.beautylab.dtos.CitaDto;
import com.example.beautylab.models.Citas;

@Component
public class CitaMapperImpl implements CitaMapper {

    @Override
    public Citas toEntity(CitaDto dto){
        if(dto == null) return null;

        return Citas.builder()
        .id(dto.getId())
        .clienteId(dto.getClienteId())
        .empleadoId(dto.getEmpleadoId())
        .servicioId(dto.getServicioId())
        .fechaHora(dto.getFechaHora())
        .duracionCita(dto.getDuracionCita())
        .precioFinal(dto.getPrecioFinal())
        .estado(dto.getEstado())
        .observaciones(dto.getObservaciones())
        .build();
    }

    @Override
    public CitaDto toDto(Citas entity){
        if(entity == null) return null;

        CitaDto dto = new CitaDto();
        dto.setId(entity.getId());
        dto.setClienteId(entity.getClienteId());
        dto.setEmpleadoId(entity.getEmpleadoId());
        dto.setServicioId(entity.getServicioId());
        dto.setFechaHora(entity.getFechaHora());
        dto.setDuracionCita(entity.getDuracionCita());
        dto.setPrecioFinal(entity.getPrecioFinal());
        dto.setEstado(entity.getEstado());
        dto.setObservaciones(entity.getObservaciones());
        return dto;
    }

    @Override
    public List<CitaDto> toDtoList(List<Citas> citas){
        if(citas == null) return null;
        return citas.stream()
        .map(this::toDto)
        .toList();
    }

    @Override
    public void updateCita(CitaDto dto, Citas entity){
        if (dto == null || entity == null) return;

        if(dto.getFechaHora() != null) entity.setFechaHora(dto.getFechaHora());
        if(dto.getDuracionCita() != null) entity.setDuracionCita(dto.getDuracionCita());
        if(dto.getPrecioFinal() != null) entity.setPrecioFinal(dto.getPrecioFinal());
        if(dto.getEstado() != null) entity.setEstado(dto.getEstado());
        if(dto.getObservaciones() != null) entity.setObservaciones(dto.getObservaciones());
        if(dto.getEmpleadoId() != null) entity.setEmpleadoId(dto.getEmpleadoId());
    }

}
