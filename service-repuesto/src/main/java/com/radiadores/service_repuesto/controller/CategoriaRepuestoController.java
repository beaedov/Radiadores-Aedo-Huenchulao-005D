package com.radiadores.service_repuesto.controller;

import com.radiadores.service_repuesto.model.CategoriaRepuesto;
import com.radiadores.service_repuesto.model.Repuesto;
import com.radiadores.service_repuesto.service.CategoriaRepuestoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name ="Categoria de repuestos", description = "Categorias para clasificar repuestos")
@CrossOrigin(origins = "*")// Permite que Swagger lo llame desde cualquier puerto
@Slf4j
@RestController
@RequestMapping("/api/v1/categoriarepuestos")
public class CategoriaRepuestoController {

    @Autowired
    private CategoriaRepuestoService categoriaRepuestoService;

    @Operation(summary = "Listar categorias de repuesto", description = "Devuelve todas las categorias de repuesto registrados")
    @GetMapping
    public List<CategoriaRepuesto> listar(){
        log.info("GET /api/v1/categoriarepuestos - Listando todas las categorias de repuestos");
        return categoriaRepuestoService.obtenerTodos();
    }

    // Esto esta conectado con obtener
    @Operation(summary = "Buscar categoria de repuesto por ID", description = "Devuelve una categoria de repuesto segun su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria de Repuesto encontrado"),
            @ApiResponse(responseCode = "404", description = "Categoria de Repuesto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRepuesto> obtener(@PathVariable Long id){
        log.info("GET /api/v1/categoriarepuestos/{} - Buscando categoria del repuesto", id);
        return categoriaRepuestoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear repuesto", description = "Registra un nuevo repuesto")
    @PostMapping
    public CategoriaRepuesto crear(@RequestBody CategoriaRepuesto categoriaRepuesto){
        log.info("POST /api/v1/categoriarepuestos - Creando nueva categoria de repuesto");
        return categoriaRepuestoService.guardar(categoriaRepuesto);
    }

    @Operation(summary = "Eliminar repuesto", description = "Borra un repuesto segun su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        log.info("DELETE /api/v1/categoriarepuestos/{} - Eliminando categoria del repuesto", id);
        categoriaRepuestoService.eliminar(id);
    }
}
