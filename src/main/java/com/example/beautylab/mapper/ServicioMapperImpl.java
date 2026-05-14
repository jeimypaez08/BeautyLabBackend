package com.example.beautylab.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.beautylab.dtos.ServicioDto;
import com.example.beautylab.models.Servicio;

@Component
public class ServicioMapperImpl implements ServicioMapper{

    @Override
    public Servicio toEntity(ServicioDto dto){
        if(dto == null) return null;

        return Servicio.builder()
        .id(dto.getId())
        .nombre(dto.getNombre())
        .descripcion(dto.getDescripcion())
        .precio(dto.getPrecio())
        .duracion(dto.getDuracion())
        .categoria(dto.getCategoria())
        .imagenUrl(dto.getImagenUrl())
        .build();
    }

    @Override
    public ServicioDto toDto(Servicio entity){
        if(entity == null) return null;

        ServicioDto dto = new ServicioDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecio(entity.getPrecio());
        dto.setDuracion(entity.getDuracion());
        dto.setCategoria(entity.getCategoria());
        dto.setImagenUrl(entity.getImagenUrl());
        dto.setDisponible(entity.getDisponible());
        return dto;
    }

    @Override
     public List<ServicioDto> toDtoList(List<Servicio> servicios){
        if (servicios == null) return null;
        return servicios.stream()
        .map(this::toDto)
        .toList();
     }

     @Override
     public void updateServicio(ServicioDto dto, Servicio servicio){
        if (dto == null || servicio == null) return;

        if(dto.getNombre() != null) servicio.setNombre(dto.getNombre());
        if(dto.getDescripcion() != null) servicio.setDescripcion(dto.getDescripcion());
        if(dto.getPrecio() != 0) servicio.setPrecio(dto.getPrecio());
        if(dto.getDuracion() != null) servicio.setDuracion(dto.getDuracion());
        if(dto.getCategoria() != null) servicio.setCategoria(dto.getCategoria());
        if(dto.getImagenUrl() != null) servicio.setImagenUrl(dto.getImagenUrl());
     }
}

