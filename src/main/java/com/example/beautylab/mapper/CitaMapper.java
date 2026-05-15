package com.example.beautylab.mapper;

import java.util.List;

import com.example.beautylab.dtos.CitaDto;
import com.example.beautylab.models.Citas;

public interface CitaMapper {

    Citas toEntity(CitaDto dto);
    CitaDto toDto(Citas entity);

    List<CitaDto> toDtoList(List<Citas> citas);
    void updateCita(CitaDto dto, Citas cita);

}
