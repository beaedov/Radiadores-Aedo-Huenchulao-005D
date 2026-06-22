package com.radiadores.service_agenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_agenda.model.TipoCita;
import com.radiadores.service_agenda.service.TipoCitaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Tipos de Cita", description = "Operaciones sobre los tipos de cita disponibles")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/tipos-cita")
public class TipoCitaController {

    @Autowired
    private TipoCitaService tipoCitaService;

    @Operation(summary = "Listar tipos de cita", description = "Devuelve todos los tipos de cita registrados")
    @GetMapping
    public List<TipoCita> listar() {
        log.info("GET /api/v1/tipos-cita - Listando todos los tipos de cita");
        return tipoCitaService.obtenerTodos();
    }

    @Operation(summary = "Buscar tipo de cita por ID", description = "Devuelve un tipo de cita según su ID")
    @GetMapping("/{id}")
    public ResponseEntity<TipoCita> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/tipos-cita/{} - Buscando tipo de cita", id);
        return tipoCitaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear tipo de cita", description = "Registra un nuevo tipo de cita")
    @PostMapping
    public TipoCita crear(@RequestBody TipoCita tipoCita) {
        log.info("POST /api/v1/tipos-cita - Creando nuevo tipo de cita");
        return tipoCitaService.guardar(tipoCita);
    }

    @Operation(summary = "Eliminar tipo de cita", description = "Borra un tipo de cita según su ID")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/tipos-cita/{} - Eliminando tipo de cita", id);
        tipoCitaService.eliminar(id);
    }
}