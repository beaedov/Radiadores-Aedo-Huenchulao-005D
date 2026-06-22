package com.radiadores.service_factura.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.radiadores.service_factura.model.Factura;
import com.radiadores.service_factura.repository.DetalleFacturaRepository;
import com.radiadores.service_factura.repository.FacturaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<Factura> obtenerTodos() {
        log.info("Obteniendo todas las facturas");
        return facturaRepository.findAll();
    }

    public Optional<Factura> buscarPorId(Long id) {
        log.info("Buscando factura con id: {}", id);
        return facturaRepository.findById(id);
    }

    // Guarda una factura nueva enriqueciendo con datos del cliente y la orden
    // via WebClient antes de persistir en la base de datos
    @Transactional
    public Factura guardar(Factura factura) {
        log.info("Guardando factura para cliente id: {}", factura.getClienteId());

        if (factura.getClienteId() != null) {
            try {
                log.info("Consultando datos del cliente con id: {}", factura.getClienteId());
                Object datosCliente = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8083/api/v1/clientes/" + factura.getClienteId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                factura.setDatosCliente(datosCliente);
                log.info("Datos del cliente obtenidos exitosamente");
            } catch (Exception e) {
                log.error("Error al obtener datos del cliente: {}", e.getMessage());
                factura.setDatosCliente("Cliente no disponible");
            }
        }

        if (factura.getOrdenReparacionId() != null) {
            try {
                log.info("Consultando datos de la orden con id: {}", factura.getOrdenReparacionId());
                Object datosOrden = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8085/api/v1/ordenes/" + factura.getOrdenReparacionId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                factura.setDatosOrden(datosOrden);
                log.info("Datos de la orden obtenidos exitosamente");
            } catch (Exception e) {
                log.error("Error al obtener datos de la orden: {}", e.getMessage());
                factura.setDatosOrden("Orden no disponible");
            }
        }

        Factura guardada = facturaRepository.save(factura);
        log.info("Factura guardada exitosamente con id: {}", guardada.getId());
        return guardada;
    }

    public void eliminar(Long id) {
        log.info("Eliminando factura con id: {}", id);
        facturaRepository.deleteById(id);
        log.info("Factura eliminada exitosamente");
    }

    // Retorna todas las facturas asociadas a un cliente específico
    public List<Factura> buscarPorClienteId(Long clienteId) {
        log.info("Buscando facturas por clienteId: {}", clienteId);
        return facturaRepository.findByClienteId(clienteId);
    }

    // Retorna todas las facturas asociadas a una orden de reparación específica
    public List<Factura> buscarPorOrdenId(Long ordenId) {
        log.info("Buscando facturas por ordenId: {}", ordenId);
        return facturaRepository.findByOrdenReparacionId(ordenId);
    }

    // Retorna todas las facturas filtradas por estado (pendiente, pagada, anulada)
    public List<Factura> buscarPorEstado(String estado) {
        log.info("Buscando facturas por estado: {}", estado);
        return facturaRepository.findByEstado(estado);
    }

    // Calcula el monto total de una factura sumando los subtotales de sus detalles
    // y actualiza el campo montoTotal en la base de datos
    public Factura calcularTotal(Long facturaId) {
        log.info("Calculando total de factura con id: {}", facturaId);
        Factura factura = facturaRepository.findById(facturaId).orElse(null);
        if (factura != null) {
            int total = detalleFacturaRepository.findByFacturaId(facturaId)
                .stream()
                .mapToInt(d -> d.getCantidad() * d.getPrecioUnitario())
                .sum();
            factura.setMontoTotal(total);
            log.info("Total calculado: {}", total);
            return facturaRepository.save(factura);
        }
        log.warn("Factura con id: {} no encontrada", facturaId);
        return null;
    }

    private Factura enriquecerFactura(Factura factura) {
        // Enriquecer con datos del cliente
        if (factura.getClienteId() != null) {
            try {
                log.info("Enriqueciendo factura con datos del cliente id: {}", factura.getClienteId());
                Object datosCliente = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8083/api/v1/clientes/" + factura.getClienteId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                factura.setDatosCliente(datosCliente);
                log.info("Datos del cliente obtenidos exitosamente");
            } catch (Exception e) {
                log.error("Error al obtener datos del cliente: {}", e.getMessage());
                factura.setDatosCliente("Cliente no disponible");
            }
        }

    // Enriquecer con datos de la orden
        if (factura.getOrdenReparacionId() != null) {
            try {
                log.info("Enriqueciendo factura con datos de la orden id: {}", factura.getOrdenReparacionId());
                Object datosOrden = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8085/api/v1/ordenes/" + factura.getOrdenReparacionId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                factura.setDatosOrden(datosOrden);
                log.info("Datos de la orden obtenidos exitosamente");
            } catch (Exception e) {
                log.error("Error al obtener datos de la orden: {}", e.getMessage());
                factura.setDatosOrden("Orden no disponible");
            }
        }

        return factura;
    }
    
    // Obtiene una factura completa con datos del cliente y la orden via WebClient
    public Factura obtenerFacturaCompleta(Long id) {
        log.info("Obteniendo factura completa con id: {}", id);
        Factura factura = facturaRepository.findById(id).orElse(null);
        if (factura != null) {
            return enriquecerFactura(factura);
        }
        log.warn("Factura con id: {} no encontrada", id);
        return null;
    }
}