package com.radiadores.service_catalogo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_catalogo.model.Compatibilidad;
import com.radiadores.service_catalogo.repository.CompatibilidadRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Compatibilidades", description = "Operaciones sobre la compatibilidad radiador-vehículo")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/compatibilidades")
public class CompatibilidadController {

    @Autowired
    private CompatibilidadRepository compatibilidadRepository;

    @Operation(summary = "Listar compatibilidades", description = "Devuelve todas las compatibilidades registradas")
    @GetMapping
    public List<Compatibilidad> listar() {
        log.info("GET /api/v1/compatibilidades - Listando todas las compatibilidades");
        return compatibilidadRepository.findAll();
    }

    @Operation(summary = "Buscar compatibilidad por ID", description = "Devuelve una compatibilidad según su ID")
    @GetMapping("/{id}")
    public Optional<Compatibilidad> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/v1/compatibilidades/{} - Buscando compatibilidad", id);
        return compatibilidadRepository.findById(id);
    }

    @Operation(summary = "Crear compatibilidad", description = "Registra una nueva compatibilidad")
    @PostMapping
    public Compatibilidad crear(@RequestBody Compatibilidad compatibilidad) {
        log.info("POST /api/v1/compatibilidades - Creando nueva compatibilidad");
        return compatibilidadRepository.save(compatibilidad);
    }

    @Operation(summary = "Eliminar compatibilidad", description = "Borra una compatibilidad según su ID")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/compatibilidades/{} - Eliminando compatibilidad", id);
        compatibilidadRepository.deleteById(id);
    }

    @Operation(summary = "Buscar compatibilidades por radiador", description = "Devuelve las compatibilidades de un radiador específico")
    @GetMapping("/radiador/{radiadorId}")
    public List<Compatibilidad> buscarPorRadiador(@PathVariable Long radiadorId) {
        log.info("GET /api/v1/compatibilidades/radiador/{} - Buscando compatibilidades por radiador", radiadorId);
        return compatibilidadRepository.findByRadiadorId(radiadorId);
    }
}