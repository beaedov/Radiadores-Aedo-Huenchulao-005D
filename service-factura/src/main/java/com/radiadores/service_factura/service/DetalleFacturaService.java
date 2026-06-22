package com.radiadores.service_factura.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radiadores.service_factura.model.DetalleFactura;
import com.radiadores.service_factura.repository.DetalleFacturaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DetalleFacturaService {

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    public List<DetalleFactura> obtenerTodos() {
        log.info("Obteniendo todos los detalles de factura");
        return detalleFacturaRepository.findAll();
    }

    public Optional<DetalleFactura> buscarPorId(Long id) {
        log.info("Buscando detalle de factura con id: {}", id);
        return detalleFacturaRepository.findById(id);
    }

    @Transactional
    public DetalleFactura guardar(DetalleFactura detalle) {
        log.info("Guardando detalle de factura");
        detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
        DetalleFactura guardado = detalleFacturaRepository.save(detalle);
        log.info("Detalle guardado exitosamente con id: {}", guardado.getId());
        return guardado;
    }

    public void eliminar(Long id) {
        log.info("Eliminando detalle de factura con id: {}", id);
        detalleFacturaRepository.deleteById(id);
        log.info("Detalle eliminado exitosamente");
    }

    public List<DetalleFactura> buscarPorFacturaId(Long facturaId) {
        log.info("Buscando detalles por facturaId: {}", facturaId);
        return detalleFacturaRepository.findByFacturaId(facturaId);
    }
}