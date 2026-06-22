package com.radiadores.service_vehiculo.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_vehiculo.model.TipoVehiculo;
import com.radiadores.service_vehiculo.service.TipoVehiculoService;

import lombok.extern.slf4j.Slf4j;

@Tag(name ="Tipos de Vehiculo", description = "Catalogo de tipos de vehiculos")
@CrossOrigin(origins = "*")// Permite que Swagger lo llame desde cualquier puerto
@Slf4j
@RestController
@RequestMapping("/api/v1/tiposvehiculos")
public class TipoVehiculoController {

    @Autowired
    private TipoVehiculoService tipoVehiculoService;

    @Operation(summary = "Listar tipos de vehiculos", description = "Devuelve todos los tipos de vehiculos registrados")
    @GetMapping
    public List<TipoVehiculo> listar() {
        log.info("GET /api/v1/tiposvehiculos - Listando todos los tipos de vehiculo");
        return tipoVehiculoService.obtenerTodos();
    }

    @Operation(summary = "Crear tipo de vehiculo", description = "Registra un nuevo tipo de vehiculo")
    @PostMapping
    public ResponseEntity<TipoVehiculo> crear(@RequestBody TipoVehiculo tipo) {
        log.info("POST /api/v1/tiposvehiculos - Creando nuevo tipo de vehiculo");
        return ResponseEntity.ok(tipoVehiculoService.guardar(tipo));
    }

    // GET por ID
    @Operation(summary = "Buscar tipo de vehiculo por ID", description = "Devuelve un tipo segun su ID")
    @GetMapping("/{id}")
    public Optional<TipoVehiculo> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/v1/tiposvehiculos/{} - Buscando tipo de vehiculo", id);
        return tipoVehiculoService.buscarPorId(id);
    }

    // DELETE
    @Operation(summary = "Eliminar tipo de vehiculo", description = "Borra un tipo segun su ID")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/tiposvehiculos/{} - Eliminando tipo de vehiculo", id);
        tipoVehiculoService.eliminar(id);
    }
}
