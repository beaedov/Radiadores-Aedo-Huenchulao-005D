package com.radiadores.service_mecanico.controller;

import com.radiadores.service_mecanico.model.Mecanico;
import com.radiadores.service_mecanico.service.MecanicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="Mecanicos", description = "Operacion sobre los mecanicos del taller")
@CrossOrigin(origins = "*")// Permite que Swagger lo llame desde cualquier puerto
@Slf4j
@RestController
@RequestMapping("/api/v1/mecanicos")
public class MecanicoController {
    @Autowired
    private MecanicoService mecanicoService;

    @Operation(summary = "Listar mecanicos", description = "Devuelve todos los mecanicos registrados")
    @GetMapping
    public List<Mecanico> listar(){
        log.info("GET /api/v1/mecanicos - Listando todos los mecanicos");
        return mecanicoService.obtenerTodos();
    }

    // Esto esta conectado con obtener
    @Operation(summary = "Buscar mecanico por ID", description = "Devuelve un mecanico segun su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mecanico encontrado"),
            @ApiResponse(responseCode = "404", description = "Mecanico no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Mecanico> obtener(@PathVariable Long id){
        log.info("GET /api/v1/mecanicos/{} - Buscando mecanico", id);
        return mecanicoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear mecanico", description = "Registra un nuevo mecanico")
    @PostMapping
    public Mecanico crear(@RequestBody Mecanico mecanico){
        log.info("POST /api/v1/mecanicos - Creando nuevo mecanico");
        return mecanicoService.guardar(mecanico);
    }

    @Operation(summary = "Eliminar mecanico", description = "Borra un mecanico segun su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        log.info("DELETE /api/v1/mecanicos/{} - Eliminando mecanico", id);
        mecanicoService.eliminar(id);
    }

}
