package com.radiadores.service_orden_reparacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.radiadores.service_orden_reparacion.model.OrdenReparacion;
import com.radiadores.service_orden_reparacion.repository.OrdenReparacionRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class OrdenReparacionService {

    @Autowired
    private OrdenReparacionRepository ordenReparacionRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    // Listar todas las ordenReparaciones
    public List<OrdenReparacion> obtenerTodos() {
        log.info("Obteniendo todas las ordenes de reparacion");
        return ordenReparacionRepository.findAll();
    }

    // Buscar por ID ordenReparacion
    public Optional<OrdenReparacion> buscarPorId(Long id) {
        log.info("Buscando orden de reparacion con id: {}", id);
        return ordenReparacionRepository.findById(id);
    }

    // Guardar ordenReparacion
    public OrdenReparacion guardar(OrdenReparacion ordenReparacion) {
        log.info("Guardando orden de reparacion para cliente id: {}", ordenReparacion.getClienteId());
        if (ordenReparacion.getClienteId() != null) {
            log.info("Consultando datos del cliente con id: {}", ordenReparacion.getClienteId());
            Object datosCliente = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/api/v1/clientes/" + ordenReparacion.getClienteId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            ordenReparacion.setDatosCliente(datosCliente);
            log.info("Datos del cliente obtenidos exitosamente");
        }
        OrdenReparacion guardada = ordenReparacionRepository.save(ordenReparacion);
        log.info("Orden de reparacion guardada exitosamente con id: {}", guardada.getId());
        return guardada;
    }

    // Eliminar ordenReparacion
    public void eliminar(Long id) {
        log.info("Eliminando orden de reparacion con id: {}", id);
        ordenReparacionRepository.deleteById(id);
        log.info("Orden de reparacion eliminada exitosamente");
    }

    // Buscar ordenReparacion po clienteId
    public List<OrdenReparacion> buscarPorClienteId(Long clienteId) {
        log.info("Buscando ordenes de reparacion por clienteId: {}", clienteId);
        return ordenReparacionRepository.findByClienteId(clienteId);
    }

    // Método privado para enriquecer con datos del cliente
    private OrdenReparacion enriquecerConCliente(OrdenReparacion orden) {
        if (orden.getClienteId() != null) {
            try {
                log.info("Enriqueciendo orden con datos del cliente id: {}", orden.getClienteId());
                Object datosCliente = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8083/api/v1/clientes/" + orden.getClienteId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                orden.setDatosCliente(datosCliente);
                log.info("Datos del cliente obtenidos exitosamente");
            } catch (Exception e) {
                log.error("Error al obtener datos del cliente: {}", e.getMessage());
                orden.setDatosCliente("Cliente no disponible");
            }
        }
        return orden;
    }

    // Obtener ordenReparacion completa con datos del cliente
    public OrdenReparacion obtenerOrdenCompleta(Long id) {
        log.info("Obteniendo orden completa con id: {}", id);
        OrdenReparacion orden = ordenReparacionRepository.findById(id).orElse(null);
        if (orden != null) {
            return enriquecerConCliente(orden);
        }
        log.warn("Orden con id: {} no encontrada", id);
        return null;
    }
}