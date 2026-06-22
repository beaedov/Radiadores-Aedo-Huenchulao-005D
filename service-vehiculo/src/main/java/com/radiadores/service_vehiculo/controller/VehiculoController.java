package com.radiadores.service_vehiculo.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_vehiculo.model.Vehiculo;
import com.radiadores.service_vehiculo.service.VehiculoService;

import lombok.extern.slf4j.Slf4j;

@Tag(name ="Vehiculos", description = "Gestion de los vehiculos de los clientes")
@CrossOrigin(origins = "*")// Permite que Swagger lo llame desde cualquier puerto
@Slf4j
@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @Operation(summary = "Listar vehiculos", description = "Devuelve todos los vehiculos registrados")
    @GetMapping
    public List<Vehiculo> listar() {
        log.info("GET /api/v1/vehiculos - Listando todos los vehiculos");
        return vehiculoService.obtenerTodos();
    }

    @Operation(summary = "Buscar vehiculo por ID", description = "Devuelve un vehiculo segun su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculo encontrado"),
            @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/vehiculos/{} - Buscando vehiculo", id);
        return vehiculoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear vehiculo", description = "Registra un nuevo vehiculo")
    @PostMapping
    public Vehiculo crear(@RequestBody Vehiculo vehiculo) {
        log.info("POST /api/v1/vehiculos - Creando nuevo vehiculo");
        return vehiculoService.guardar(vehiculo);
    }

    @Operation(summary = "Eliminar vehiculo", description = "Borra un vehiculo segun su ID")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/vehiculos/{} - Eliminando vehiculo", id);
        vehiculoService.eliminar(id);
    }

    // Buscar vehículos por clienteId
    @Operation(summary = "Buscar vehiculos por cliente", description = "Devuelve los vehiculos de un cliente")
    @GetMapping("/cliente/{clienteId}")
    public List<Vehiculo> buscarPorClienteId(@PathVariable Long clienteId) {
        log.info("GET /api/v1/vehiculos/cliente/{} - Buscando vehiculos por cliente", clienteId);
        return vehiculoService.buscarPorClienteId(clienteId);
    }

    // Buscar vehículos por tipoVehiculoId
    @Operation(summary = "Buscar vehiculos por tipo", description = "Devuelve los vehiculos de un tipo")
    @GetMapping("/tipo/{tipoVehiculoId}")
    public List<Vehiculo> buscarPorTipoVehiculo(@PathVariable Long tipoVehiculoId) {
        log.info("GET /api/v1/vehiculos/tipo/{} - Buscando vehiculos por tipo", tipoVehiculoId);
        return vehiculoService.buscarPorTipoVehiculoId(tipoVehiculoId);
    }

    @Operation(summary = "Obtener vehiculo completo", description = "Devuelve el vehiculo con los datos del cliente desde otro microservicio")
    @GetMapping("/completa/{id}")
    public Vehiculo obtenerCompleto(@PathVariable Long id) {
        log.info("GET /api/v1/vehiculos/completa/{} - Obteniendo vehiculo completo", id);
        return vehiculoService.obtenerVehiculoCompleto(id);
    }
}
