package com.example.beautylab.mapper;

import java.util.List;

import com.example.beautylab.dtos.ServicioDto;
import com.example.beautylab.models.Servicio;

public interface ServicioMapper {

    Servicio toEntity(ServicioDto dto);
    ServicioDto toDto(Servicio entity);

    List<ServicioDto> toDtoList(List<Servicio> servicios);
    void updateServicio(ServicioDto dto, Servicio servicio);

}
