package com.example.beautylab.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.beautylab.models.Citas;
import com.example.beautylab.models.EstadoCita;

public interface CitaRepository extends MongoRepository<Citas, String>{

    List<Citas> findByClienteId(String clienteId); //metodo para buscar las citas de un usuario por su id
    List<Citas> findByEmpleadoId(String empleadoId); //metodo para buscar las citas de un empleado por su id
    List<Citas> findByServicioId(String servicioId); //metodo para buscar las citas de un servicio por su id
    List<Citas> findByEstado(EstadoCita estado); //metodo para buscar las citas por su estado, por ejemplo, todas las citas pendientes, confirmadas, canceladas o completadas
}
