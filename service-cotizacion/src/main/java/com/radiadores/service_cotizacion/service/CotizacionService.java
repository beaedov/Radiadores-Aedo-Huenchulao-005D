package com.radiadores.service_cotizacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.radiadores.service_cotizacion.model.Cotizacion;
import com.radiadores.service_cotizacion.model.SolicitudCotizacion;
import com.radiadores.service_cotizacion.repository.CotizacionRepository;
import com.radiadores.service_cotizacion.repository.SolicitudCotizacionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;
    @Autowired
    private SolicitudCotizacionRepository solicitudCotizacionRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Cotizacion guardar(Cotizacion cotizacion) {
        log.info("Guardando cotizacion para solicitud id: {}", cotizacion.getSolicitudCotizacion().getId());

    // Primero buscar la solicitud completa
    if (cotizacion.getSolicitudCotizacion() != null && cotizacion.getSolicitudCotizacion().getId() != null) {
        SolicitudCotizacion solicitudCotizacion = solicitudCotizacionRepository
            .findById(cotizacion.getSolicitudCotizacion().getId())
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        cotizacion.setSolicitudCotizacion(solicitudCotizacion);
    }

    // Luego enriquecer con datos del cliente
    if (cotizacion.getSolicitudCotizacion().getClienteId() != null) {
        Object datosCliente = webClientBuilder.build()
            .get()
            .uri("http://localhost:8083/api/v1/clientes/" + cotizacion.getSolicitudCotizacion().getClienteId())
            .retrieve()
            .bodyToMono(Object.class)
            .block();
        cotizacion.getSolicitudCotizacion().setDatosCliente(datosCliente);
        log.info("Datos del cliente obtenidos exitosamente");
    }

    // Guardar
    Cotizacion guardada = cotizacionRepository.save(cotizacion);
    log.info("Cotizacion guardada exitosamente con id: {}", guardada.getId());
    return guardada;
    }

    // Listar todas
    public List<Cotizacion> obtenertodos() {
        log.info("Obteniendo todas las cotizaciones");
        return cotizacionRepository.findAll();
    }

    // Buscar por ID
    public Optional<Cotizacion> buscarPorId(Long id) {
        log.info("Buscando cotizacion con id: {}", id);
        return cotizacionRepository.findById(id);
    }

    // Eliminar
    public void eliminar(Long id) {
        log.info("Eliminando cotizacion con id: {}", id);
        cotizacionRepository.deleteById(id);
        log.info("Cotizacion eliminada exitosamente");
    }

    //obtener Solicitud Cotizacion por id
    public Cotizacion buscarPorSolicitudCotizacionId(Long solicitudCotizacionId) {
        log.info("Buscando cotizacion por solicitudId: {}", solicitudCotizacionId);
        return cotizacionRepository.findBySolicitudCotizacionId(solicitudCotizacionId);
    }

    // Método privado para enriquecer la cotizacion con datos del cliente
    private Cotizacion enriquecerConCliente(Cotizacion cotizacion) {
        if (cotizacion.getSolicitudCotizacion().getClienteId() != null) {
            try {
                // Llamada al Microservicio de Clientes (Puerto 8083)
                log.info("Enriqueciendo cotizacion con datos del cliente id: {}", cotizacion.getSolicitudCotizacion().getClienteId());
                Object cliente = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/api/v1/clientes/" + cotizacion.getSolicitudCotizacion().getClienteId())
                .retrieve()
                .bodyToMono(Object.class)
                .block(); // Espera la respuesta
            
                cotizacion.getSolicitudCotizacion().setDatosCliente(cliente);
                log.info("Datos del cliente obtenidos exitosamente");
            } catch (Exception e) {
                log.error("Error al obtener datos del cliente: {}", e.getMessage());
                cotizacion.getSolicitudCotizacion().setDatosCliente("Información de cliente no disponible actualmente");
            }
        }
        return cotizacion;
    }

    // Obtener la cotizacion con datos cliente
    public Cotizacion obtenerCotizacionCompleta(Long id) {
        log.info("Obteniendo cotizacion completa con id: {}", id);
        Cotizacion cotizacion = cotizacionRepository.findById(id).orElse(null);
        if (cotizacion != null) {
            return enriquecerConCliente(cotizacion);
        }
        log.warn("Cotizacion con id: {} no encontrada", id);
        return null;
    }

}
