package com.radiadores.service_factura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_factura.model.DetalleFactura;
import com.radiadores.service_factura.service.DetalleFacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Detalle de Facturas", description = "Operaciones sobre los ítems de las facturas")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/detalles-factura")
public class DetalleFacturaController {

    @Autowired
    private DetalleFacturaService detalleFacturaService;

    @Operation(summary = "Listar detalles", description = "Devuelve todos los detalles de factura registrados")
    @GetMapping
    public List<DetalleFactura> listar() {
        log.info("GET /api/v1/detalles-factura - Listando todos los detalles");
        return detalleFacturaService.obtenerTodos();
    }

    @Operation(summary = "Buscar detalle por ID", description = "Devuelve un detalle según su ID")
    @GetMapping("/{id}")
    public ResponseEntity<DetalleFactura> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/detalles-factura/{} - Buscando detalle", id);
        return detalleFacturaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar detalles por factura", description = "Devuelve los detalles de una factura específica")
    @GetMapping("/factura/{facturaId}")
    public List<DetalleFactura> buscarPorFactura(@PathVariable Long facturaId) {
        log.info("GET /api/v1/detalles-factura/factura/{} - Buscando detalles por factura", facturaId);
        return detalleFacturaService.buscarPorFacturaId(facturaId);
    }

    @Operation(summary = "Crear detalle", description = "Registra un nuevo detalle de factura")
    @PostMapping
    public DetalleFactura crear(@RequestBody DetalleFactura detalle) {
        log.info("POST /api/v1/detalles-factura - Creando nuevo detalle");
        return detalleFacturaService.guardar(detalle);
    }

    @Operation(summary = "Eliminar detalle", description = "Borra un detalle según su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        log.info("DELETE /api/v1/detalles-factura/{} - Eliminando detalle", id);
        detalleFacturaService.eliminar(id);
    }
}