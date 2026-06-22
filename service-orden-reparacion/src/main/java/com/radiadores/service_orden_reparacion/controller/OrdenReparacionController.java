package com.radiadores.service_orden_reparacion.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_orden_reparacion.model.OrdenReparacion;
import com.radiadores.service_orden_reparacion.service.OrdenReparacionService;

import lombok.extern.slf4j.Slf4j;
@Tag(name ="Ordenes de Reparacion", description = "Gestion de ordenes de reparacion")
@CrossOrigin(origins = "*")// Permite que Swagger lo llame desde cualquier puerto
@Slf4j
@RequestMapping("/api/v1/ordenes")
@RestController
public class OrdenReparacionController {

    @Autowired
    private OrdenReparacionService ordenReparacionService;

    @Operation(summary = "Listar ordenes", description = "Devuelve todas las ordenes de reparacion")
    @GetMapping
    public List<OrdenReparacion> listar() {
        log.info("GET /api/v1/ordenes - Listando todas las ordenes");
        return ordenReparacionService.obtenerTodos();
    }

    @Operation(summary = "Buscar orden por ID", description = "Devuelve una orden segun su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden encontrada"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrdenReparacion> obtener(@PathVariable Long id){
        log.info("GET /api/v1/ordenes/{} - Buscando orden", id);
        return ordenReparacionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener orden completa", description = "Devuelve la orden con los datos del cliente desde otro microservicio")
    @GetMapping("/completa/{id}")
    public OrdenReparacion obtenerCompleta(@PathVariable Long id) {
        return ordenReparacionService.obtenerOrdenCompleta(id);
    }

    @Operation(summary = "Buscar ordenes por cliente", description = "Devuelve las ordenes de un cliente")
    @GetMapping("/cliente/{clienteId}")
    public List<OrdenReparacion> buscarPorCliente(@PathVariable Long clienteId) {
        return ordenReparacionService.buscarPorClienteId(clienteId);
    }

    @Operation(summary = "Crear orden", description = "Registra una nueva orden de reparacion")
    @PostMapping
    public OrdenReparacion crear(@RequestBody OrdenReparacion ordenReparacion) {
        return ordenReparacionService.guardar(ordenReparacion);
    }

    @Operation(summary = "Eliminar orden", description = "Borra una orden segun su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        ordenReparacionService.eliminar(id);
    }
}