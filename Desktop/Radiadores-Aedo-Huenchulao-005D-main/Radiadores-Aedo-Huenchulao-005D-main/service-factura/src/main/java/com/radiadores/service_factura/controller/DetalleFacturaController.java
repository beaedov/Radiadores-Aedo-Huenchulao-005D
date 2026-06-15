package com.radiadores.service_factura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radiadores.service_factura.model.DetalleFactura;
import com.radiadores.service_factura.service.DetalleFacturaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/detalles-factura")
public class DetalleFacturaController {

    @Autowired
    private DetalleFacturaService detalleFacturaService;

    @GetMapping
    public List<DetalleFactura> listar() {
        log.info("GET /api/v1/detalles-factura - Listando todos los detalles");
        return detalleFacturaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleFactura> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/detalles-factura/{} - Buscando detalle", id);
        return detalleFacturaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/factura/{facturaId}")
    public List<DetalleFactura> buscarPorFactura(@PathVariable Long facturaId) {
        log.info("GET /api/v1/detalles-factura/factura/{} - Buscando detalles por factura", facturaId);
        return detalleFacturaService.buscarPorFacturaId(facturaId);
    }

    @PostMapping
    public DetalleFactura crear(@RequestBody DetalleFactura detalle) {
        log.info("POST /api/v1/detalles-factura - Creando nuevo detalle");
        return detalleFacturaService.guardar(detalle);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        log.info("DELETE /api/v1/detalles-factura/{} - Eliminando detalle", id);
        detalleFacturaService.eliminar(id);
    }
}