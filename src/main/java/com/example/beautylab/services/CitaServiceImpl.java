package com.example.beautylab.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.beautylab.dtos.CitaDto;
import com.example.beautylab.mapper.CitaMapper;
import com.example.beautylab.models.Citas;
import com.example.beautylab.models.EstadoCita;
import com.example.beautylab.models.Servicio;
import com.example.beautylab.repositories.CitaRepository;
import com.example.beautylab.repositories.ServicioRepository;
import com.example.beautylab.repositories.UsuarioRepository;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepo;
    private final ServicioRepository servicioRepo;
    private final UsuarioRepository userRepo;
    private final CitaMapper citaMapper;

    public CitaServiceImpl(CitaRepository citaRepo,
                           ServicioRepository servicioRepo, 
                           UsuarioRepository userRepo, 
                           CitaMapper citaMapper) {
        this.citaRepo = citaRepo;
        this.servicioRepo = servicioRepo;
        this.userRepo = userRepo;
        this.citaMapper = citaMapper;
    }

    @Override
    @Transactional
    public CitaDto crearCita(CitaDto citaDto){

        //validar si existe el cliente y el empleado
        if(!userRepo.existsById(citaDto.getClienteId())){
            throw new RuntimeException("Cliente no encontrado");
        }
        if(!userRepo.existsById(citaDto.getEmpleadoId())){
            throw new RuntimeException("Especialista no encontrado");
        }

        //buscar el servicio para obtener su precio y duración
        Servicio servicio = servicioRepo.findById(citaDto.getServicioId())
        .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        //convertir a entidad
        Citas cita = citaMapper.toEntity(citaDto);

        //logica de precios flexibles
        // Si el DTO no trae precio/duración (reserva rápida), se usa lo del servicio
        if(cita.getPrecioFinal() == null) cita.setPrecioFinal(servicio.getPrecio());
        if(cita.getDuracionCita() == null) cita.setDuracionCita(servicio.getDuracion());

        //estado inicial de la cita al crearla
        cita.setEstado(EstadoCita.PENDIENTE);

        //guardar la cita
        Citas guardada = citaRepo.save(cita);
        return citaMapper.toDto(guardada);
    }

    @Override
    public List<CitaDto> citasPorCliente(String clienteId){
        return citaMapper.toDtoList(citaRepo.findByClienteId(clienteId));
    }

    @Override
    public List<CitaDto> citasPorEmpleado(String empleadoId){
        return citaMapper.toDtoList(citaRepo.findByEmpleadoId(empleadoId));
    }

    @Override
    public CitaDto obtenerCitaPorId(String id){
        Citas cita = citaRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        return citaMapper.toDto(cita);
    }

    @Override
    @Transactional
    public void cambiarEstado(String id, EstadoCita nuevoEstado){
        Citas cita= citaRepo.findById(id)
        .orElseThrow(()-> new RuntimeException("Cita no encontrada"));
        cita.setEstado(nuevoEstado);
        citaRepo.save(cita);
    }

    @Override
    @Transactional
    public void actualizarCita(String id, CitaDto citaDto){
        Citas cita = citaRepo.findById(id)
        .orElseThrow(()-> new RuntimeException("Cita no encontrada"));
        citaMapper.updateCita(citaDto, cita);
        citaRepo.save(cita);
    }

    @Override
    public void eliminarCita(String id) {
        if (!citaRepo.existsById(id)) {
            throw new RuntimeException("La cita no existe");
        }
        citaRepo.deleteById(id);
    }
}
