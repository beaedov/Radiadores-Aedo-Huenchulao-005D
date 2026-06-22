package com.radiadores.service_factura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_factura.model.Factura;
import com.radiadores.service_factura.service.FacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Facturas", description = "Operaciones sobre las facturas de reparación")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Operation(summary = "Listar facturas", description = "Devuelve todas las facturas registradas")
    @GetMapping
    public List<Factura> listar() {
        log.info("GET /api/v1/facturas - Listando todas las facturas");
        return facturaService.obtenerTodos();
    }

    @Operation(summary = "Buscar factura por ID", description = "Devuelve una factura según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura encontrada"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/facturas/{} - Buscando factura", id);
        return facturaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener factura completa", description = "Devuelve la factura con datos del cliente y orden via WebClient")
    @GetMapping("/completa/{id}")
    public Factura obtenerCompleta(@PathVariable Long id) {
        log.info("GET /api/v1/facturas/completa/{} - Obteniendo factura completa", id);
        return facturaService.obtenerFacturaCompleta(id);
    }

    @Operation(summary = "Buscar facturas por cliente", description = "Devuelve las facturas de un cliente específico")
    @GetMapping("/cliente/{clienteId}")
    public List<Factura> buscarPorCliente(@PathVariable Long clienteId) {
        log.info("GET /api/v1/facturas/cliente/{} - Buscando facturas por cliente", clienteId);
        return facturaService.buscarPorClienteId(clienteId);
    }

    @Operation(summary = "Buscar facturas por orden", description = "Devuelve las facturas de una orden de reparación específica")
    @GetMapping("/orden/{ordenId}")
    public List<Factura> buscarPorOrden(@PathVariable Long ordenId) {
        log.info("GET /api/v1/facturas/orden/{} - Buscando facturas por orden", ordenId);
        return facturaService.buscarPorOrdenId(ordenId);
    }

    @Operation(summary = "Buscar facturas por estado", description = "Devuelve las facturas filtradas por estado")
    @GetMapping("/estado/{estado}")
    public List<Factura> buscarPorEstado(@PathVariable String estado) {
        log.info("GET /api/v1/facturas/estado/{} - Buscando facturas por estado", estado);
        return facturaService.buscarPorEstado(estado);
    }

    @Operation(summary = "Calcular total de factura", description = "Calcula y actualiza el monto total sumando los detalles")
    @GetMapping("/total/{id}")
    public Factura calcularTotal(@PathVariable Long id) {
        log.info("GET /api/v1/facturas/total/{} - Calculando total de factura", id);
        return facturaService.calcularTotal(id);
    }

    @Operation(summary = "Crear factura", description = "Registra una nueva factura")
    @PostMapping
    public Factura crear(@RequestBody Factura factura) {
        log.info("POST /api/v1/facturas - Creando nueva factura");
        return facturaService.guardar(factura);
    }

    @Operation(summary = "Eliminar factura", description = "Borra una factura según su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        log.info("DELETE /api/v1/facturas/{} - Eliminando factura", id);
        facturaService.eliminar(id);
    }
}