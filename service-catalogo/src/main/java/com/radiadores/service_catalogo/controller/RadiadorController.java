package com.radiadores.service_catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_catalogo.model.Radiador;
import com.radiadores.service_catalogo.service.RadiadorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Radiadores", description = "Operaciones sobre los radiadores en venta")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/radiadores")
public class RadiadorController {

    @Autowired
    private RadiadorService radiadorService;

    @Operation(summary = "Listar radiadores", description = "Devuelve todos los radiadores registrados")
    @GetMapping
    public List<Radiador> listar(){
        log.info("GET /api/v1/radiadores - Listando todos los radiadores");
        return radiadorService.obtenerTodos();
    }

    @Operation(summary = "Buscar radiador por ID", description = "Devuelve un radiador según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Radiador encontrado"),
            @ApiResponse(responseCode = "404", description = "Radiador no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Radiador> obtener(@PathVariable Long id){
        log.info("GET /api/v1/radiadores/{} - Buscando radiador", id);
        return radiadorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear radiador", description = "Registra un nuevo radiador")
    @PostMapping
    public Radiador crear(@RequestBody Radiador radiador){
        log.info("POST /api/v1/radiadores - Creando nuevo radiador");
        return radiadorService.guardar(radiador);
    }

    @Operation(summary = "Eliminar radiador", description = "Borra un radiador según su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        log.info("DELETE /api/v1/radiadores/{} - Eliminando radiador", id);
        radiadorService.eliminar(id);
    }

    @Operation(summary = "Buscar radiadores por categoría", description = "Devuelve los radiadores de una categoría específica")
    @GetMapping("/categoria/{categoriaId}")
    public List<Radiador> buscarPorCategoria(@PathVariable Long categoriaId) {
        log.info("GET /api/v1/radiadores/categoria/{} - Buscando radiadores por categoria", categoriaId);
        return radiadorService.buscarPorCategoriaId(categoriaId);
    }
}