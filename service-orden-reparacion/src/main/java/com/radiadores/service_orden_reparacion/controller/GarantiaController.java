package com.radiadores.service_orden_reparacion.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_orden_reparacion.model.Garantia;
import com.radiadores.service_orden_reparacion.service.GarantiaService;

import lombok.extern.slf4j.Slf4j;
@Tag(name ="Garantias", description = "Gestion de garantias de reparacion")
@CrossOrigin(origins = "*")// Permite que Swagger lo llame desde cualquier puerto
@Slf4j
@RequestMapping("/api/v1/garantias")
@RestController
public class GarantiaController {

    @Autowired
    private GarantiaService garantiaService;

    @Operation(summary = "Listar garantias", description = "Devuelve todas las garantias")
    @GetMapping
    public List<Garantia> listar(){
        return garantiaService.findAll();
    }

    @Operation(summary = "Buscar garantia por ID", description = "Devuelve una garantia segun su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Garantia encontrada"),
            @ApiResponse(responseCode = "404", description = "Garantia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Garantia> obtener(@PathVariable Long id){
        log.info("GET /api/v1/garantias - Listando todas las garantias");
        return garantiaService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear garantia", description = "Registra una nueva garantia")
    @PostMapping
    public Garantia crear(@RequestBody Garantia garantia){
        log.info("POST /api/v1/garantias - Creando nueva garantia");
        return garantiaService.save(garantia);
    }

    @Operation(summary = "Eliminar garantia", description = "Borra una garantia segun su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        log.info("DELETE /api/v1/garantias/{} - Eliminando garantia", id);
        garantiaService.delete(id);
    }

}
