package com.example.beautylab.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautylab.dtos.ServicioDto;
import com.example.beautylab.models.Especialidades;
import com.example.beautylab.services.ServicioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/servicios")
public class ServiciosController {

    private final ServicioService servicioService;

    public ServiciosController(UsuarioController usuarioController, ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    //ruta publica, cualquier cliente puede ver los servicios dispo
    @GetMapping("/disponibles")
    public ResponseEntity<List<ServicioDto>> listarServiciosDisponibles(){
        return ResponseEntity.ok(servicioService.listarDisponibles());
    }

    //ruta privada, solo el admin pude crear los servicios
    @PostMapping("/crear")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ServicioDto> crear(@RequestBody ServicioDto dto) {
        return new ResponseEntity<>(servicioService.crearServicio(dto), HttpStatus.CREATED);
    }

    //privada, solo el admin puede actualizar todos los servicios
    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')") //solo el admin puede actualizar los servicios
    public ResponseEntity<ServicioDto> actualizar(@PathVariable String id, @RequestBody ServicioDto dto) { //el path variable se encarga de recibir el id del servicio que el admin quiere actualizar, y el servicio se encarga de hacer la logica de actualización
        servicioService.actualizarServicio(id, dto); //el servicio se encarga de hacer la logica de actualización, no es necesario retornar el servicio actualizado porque el cliente ya tiene el dto actualizado que envió en la petición
        return ResponseEntity.ok().build(); //retorna un 200 sin cuerpo, porque el servicio ya se actualizó, no es necesario retornar el servicio actualizado
    }

    //filtro para buscar por categoria, ruta publica
    @GetMapping("/categoria/{categoria}")
        public ResponseEntity<List<ServicioDto>> filtrar(@PathVariable Especialidades categoria){ //el path variable se encarga de recibir la categoria que el cliente quiere filtrar, y el servicio se encarga de hacer la logica de filtrado
            return ResponseEntity.ok(servicioService.filtrarPorCategoria(categoria)); //el servicio se encarga de hacer la logica de filtrado
    }

}
