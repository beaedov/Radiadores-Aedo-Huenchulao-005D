package com.radiadores.service_repuesto.controller;

import com.radiadores.service_repuesto.model.Repuesto;
import com.radiadores.service_repuesto.service.RepuestoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name ="Repuestos", description = "Gestion de repuestos del taller")
@CrossOrigin(origins = "*")// Permite que Swagger lo llame desde cualquier puerto
@Slf4j
@RestController
@RequestMapping("/api/v1/repuestos")
public class RepuestoController {

    @Autowired
    private RepuestoService repuestoService;

    @Operation(summary = "Listar repuestos", description = "Devuelve todos los repuestos registrados")
    @GetMapping
    public List<Repuesto> listar(){
        log.info("GET /api/v1/repuestos - Listando todos los repuestos");
        return repuestoService.obtenerTodos();
    }

    // Esto esta conectado con obtener
    @Operation(summary = "Buscar repuesto por ID", description = "Devuelve un repuesto segun su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repuesto encontrado"),
            @ApiResponse(responseCode = "404", description = "Repuesto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> obtener(@PathVariable Long id){
        log.info("GET /api/v1/repuestos/{} - Buscando repuesto", id);
        return repuestoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear repuesto", description = "Registra un nuevo repuesto")
    @PostMapping
    public Repuesto crear(@RequestBody Repuesto repuesto){
        log.info("POST /api/v1/repuestos - Creando nuevo repuesto");
        return repuestoService.guardar(repuesto);
    }

    @Operation(summary = "Eliminar repuesto", description = "Borra un repuesto segun su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        log.info("DELETE /api/v1/repuestos/{} - Eliminando repuesto", id);
        repuestoService.eliminar(id);
    }
}
