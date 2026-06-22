package com.radiadores.service_mecanico.controller;

import com.radiadores.service_mecanico.model.Especialidad;
import com.radiadores.service_mecanico.service.EspecialidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="Especialidades", description = "Especialidades de los mecanicos")
@CrossOrigin(origins = "*")// Permite que Swagger lo llame desde cualquier puerto
@Slf4j
@RestController
@RequestMapping("/api/v1/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @Operation(summary = "Listar especialidad", description = "Devuelve todas las especialidades registradas")
    @GetMapping
    public List<Especialidad> listar(){
        log.info("GET /api/v1/especialidades - Listando todas las especialidades");
        return especialidadService.obtenerTodos();
    }

    // Esto esta conectado con obtener
    @Operation(summary = "Buscar especialidad por ID", description = "Devuelve una especialidad segun su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidad encontrada"),
            @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> obtener(@PathVariable Long id){
        log.info("GET /api/v1/especialidades/{} - Buscando especialidad", id);
        return especialidadService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // el controller verifica si tiene algo el optional.
    }

    @Operation(summary = "Crear Especialidad", description = "Registra una nueva Especialidad")
    @PostMapping
    public Especialidad crear(@RequestBody Especialidad especialidad){
        log.info("POST /api/v1/especialidades - Creando nueva especialidad");
        return especialidadService.guardar(especialidad);
    }

    @Operation(summary = "Eliminar especialidad", description = "Borra una especialidad segun su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        log.info("DELETE /api/v1/especialidades/{} - Eliminando especialidad", id); // los long info no los ve el usuario, solo se muestran el la terminal
        especialidadService.eliminar(id);
    }
}
