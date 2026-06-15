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

import com.radiadores.service_factura.model.Factura;
import com.radiadores.service_factura.service.FacturaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public List<Factura> listar() {
        log.info("GET /api/v1/facturas - Listando todas las facturas");
        return facturaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/facturas/{} - Buscando factura", id);
        return facturaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/completa/{id}")
    public Factura obtenerCompleta(@PathVariable Long id) {
        log.info("GET /api/v1/facturas/completa/{} - Obteniendo factura completa", id);
        return facturaService.obtenerFacturaCompleta(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Factura> buscarPorCliente(@PathVariable Long clienteId) {
        log.info("GET /api/v1/facturas/cliente/{} - Buscando facturas por cliente", clienteId);
        return facturaService.buscarPorClienteId(clienteId);
    }

    @GetMapping("/orden/{ordenId}")
    public List<Factura> buscarPorOrden(@PathVariable Long ordenId) {
        log.info("GET /api/v1/facturas/orden/{} - Buscando facturas por orden", ordenId);
        return facturaService.buscarPorOrdenId(ordenId);
    }

    @GetMapping("/estado/{estado}")
    public List<Factura> buscarPorEstado(@PathVariable String estado) {
        log.info("GET /api/v1/facturas/estado/{} - Buscando facturas por estado", estado);
        return facturaService.buscarPorEstado(estado);
    }

    @GetMapping("/total/{id}")
    public Factura calcularTotal(@PathVariable Long id) {
        log.info("GET /api/v1/facturas/total/{} - Calculando total de factura", id);
        return facturaService.calcularTotal(id);
    }

    @PostMapping
    public Factura crear(@RequestBody Factura factura) {
        log.info("POST /api/v1/facturas - Creando nueva factura");
        return facturaService.guardar(factura);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        log.info("DELETE /api/v1/facturas/{} - Eliminando factura", id);
        facturaService.eliminar(id);
    }
}