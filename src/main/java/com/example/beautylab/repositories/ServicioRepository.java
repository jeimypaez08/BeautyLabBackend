package com.example.beautylab.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.beautylab.models.Especialidades;
import com.example.beautylab.models.Servicio;

public interface ServicioRepository extends MongoRepository<Servicio, String>{

    //metodo para filtrar servicios por categoria
    List<Servicio> findByCategoria(Especialidades categoria);

} 
