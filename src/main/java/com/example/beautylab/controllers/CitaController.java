package com.example.beautylab.controllers;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautylab.dtos.CitaDto;
import com.example.beautylab.models.EstadoCita;
import com.example.beautylab.services.CitaService;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaservice;

    public CitaController(CitaService citaservice) {
        this.citaservice = citaservice;
    }

    //agendar cita, cualquier usuario autenticado puede agendar una cita
    @PostMapping("/agendar")
    public ResponseEntity<CitaDto> crearCita(@RequestBody CitaDto citaDto){
        return new ResponseEntity<>(citaservice.crearCita(citaDto), HttpStatus.CREATED);
    }

    //el cliente ve sus propias citas
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CitaDto>> listarPorCliente(@PathVariable String clienteId){
        return ResponseEntity.ok(citaservice.citasPorCliente(clienteId));
    }

    //el especialista ve sus propias citas
    @GetMapping("/empleado/{empleadoId}")
    @PreAuthorize("hasAuthority('EMPLEADO','ADMIN')") //solo el especialista puede ver sus citas
    public ResponseEntity<List<CitaDto>> listarPorEmpleado(@PathVariable String empleadoId){
        return ResponseEntity.ok(citaservice.citasPorEmpleado(empleadoId));
    }

    //cambiar estado de la cita, solo el especialista o el admin pueden cambiar el estado de la cita
    @PutMapping("/{id}/estado")
    @PreAuthorize("hasAuthority('EMPLEADO','ADMIN')") //solo el especialista o el admin pueden cambiar el estado de la cita
    public ResponseEntity<Void> actualizarEstado(@PathVariable String id, @RequestParam EstadoCita nuevoEstado){
        citaservice.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.ok().build();
    }

    //eliminar cita, en caso de que el cliente haya cancelado la cita
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable String id){
        citaservice.eliminarCita(id);
        return ResponseEntity.noContent().build();
    }

}
