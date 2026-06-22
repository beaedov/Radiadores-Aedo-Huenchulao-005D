package com.radiadores.service_orden_reparacion.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_orden_reparacion.model.EstadoReparacion;
import com.radiadores.service_orden_reparacion.service.EstadoReparacionService;

import lombok.extern.slf4j.Slf4j;
@Tag(name ="Estados de Reparacion", description = "Catalogo de estados de una reparacion")
@CrossOrigin(origins = "*")// Permite que Swagger lo llame desde cualquier puerto
@Slf4j
@RequestMapping("/api/v1/estados")
@RestController
public class EstadoReparacionController {

    @Autowired
    private EstadoReparacionService estadoReparacionService;

    @Operation(summary = "Listar estados", description = "Devuelve todos los estados")
    @GetMapping
    public List<EstadoReparacion> listar(){
        log.info("GET /api/v1/estados - Listando todos los estados");
        return estadoReparacionService.findAll();
    }

    @Operation(summary = "Buscar estado por ID", description = "Devuelve un estado segun su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstadoReparacion> obtener(@PathVariable Long id){
        log.info("GET /api/v1/estados/{} - Buscando estado", id);
        return estadoReparacionService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear estado", description = "Registra un nuevo estado")
    @PostMapping
    public EstadoReparacion crear(@RequestBody EstadoReparacion estadoReparacion){
        log.info("POST /api/v1/estados - Creando nuevo estado");
        return estadoReparacionService.save(estadoReparacion);
    }

    @Operation(summary = "Eliminar estado", description = "Borra un estado segun su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        log.info("DELETE /api/v1/estados/{} - Eliminando estado", id);
        estadoReparacionService.delete(id);
    }


}
