package com.radiadores.service_cotizacion.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_cotizacion.model.SolicitudCotizacion;
import com.radiadores.service_cotizacion.repository.SolicitudCotizacionRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Solicitudes de Cotización", description = "Operaciones sobre las solicitudes de cotización")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/solicitudes")
public class SolicitudCotizacionController {

    @Autowired
    private SolicitudCotizacionRepository solicitudRepository;

    @Operation(summary = "Listar solicitudes", description = "Devuelve todas las solicitudes de cotización registradas")
    @GetMapping
    public List<SolicitudCotizacion> listar() {
        log.info("GET /api/v1/solicitudes - Listando todas las solicitudes");
        return solicitudRepository.findAll();
    }

    @Operation(summary = "Buscar solicitud por ID", description = "Devuelve una solicitud según su ID")
    @GetMapping("/{id}")
    public Optional<SolicitudCotizacion> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/v1/solicitudes/{} - Buscando solicitud", id);
        return solicitudRepository.findById(id);
    }

    @Operation(summary = "Crear solicitud", description = "Registra una nueva solicitud de cotización")
    @PostMapping
    public SolicitudCotizacion crear(@RequestBody SolicitudCotizacion solicitud) {
        log.info("POST /api/v1/solicitudes - Creando nueva solicitud");
        return solicitudRepository.save(solicitud);
    }

    @Operation(summary = "Eliminar solicitud", description = "Borra una solicitud según su ID")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/solicitudes/{} - Eliminando solicitud", id);
        solicitudRepository.deleteById(id);
    }
}