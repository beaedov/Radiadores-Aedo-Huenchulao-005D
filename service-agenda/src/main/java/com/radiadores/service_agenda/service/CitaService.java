package com.radiadores.service_agenda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.radiadores.service_agenda.model.Cita;
import com.radiadores.service_agenda.model.TipoCita;
import com.radiadores.service_agenda.repository.CitaRepository;
import com.radiadores.service_agenda.repository.TipoCitaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private TipoCitaRepository tipoCitaRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    // Retorna todas las citas almacenadas en la base de datos
    public List<Cita> obtenerTodos() {
        log.info("Obteniendo todas las citas");
        return citaRepository.findAll();
    }

    // Busca una cita por su ID
    public Optional<Cita> buscarPorId(Long id) {
        log.info("Buscando cita con id: {}", id);
        return citaRepository.findById(id);
    }

    // Guarda una cita enriqueciendo con datos del cliente y mecánico via WebClient
    @Transactional
    public Cita guardar(Cita cita) {
        log.info("Guardando cita para cliente id: {}", cita.getClienteId());

        // Busca el TipoCita completo si solo viene el ID
        if (cita.getTipoCita() != null && cita.getTipoCita().getId() != null) {
            log.info("Buscando tipo de cita con id: {}", cita.getTipoCita().getId());
            TipoCita tipoCita = tipoCitaRepository.findById(cita.getTipoCita().getId())
                .orElseThrow(() -> new RuntimeException("TipoCita no encontrado"));
            cita.setTipoCita(tipoCita);
        }

        // Consulta datos del cliente desde service-cliente (puerto 8083)
        if (cita.getClienteId() != null) {
            try {
                log.info("Consultando datos del cliente con id: {}", cita.getClienteId());
                Object datosCliente = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8083/api/v1/clientes/" + cita.getClienteId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                cita.setDatosCliente(datosCliente);
                log.info("Datos del cliente obtenidos exitosamente");
            } catch (Exception e) {
                log.error("Error al obtener datos del cliente: {}", e.getMessage());
                cita.setDatosCliente("Cliente no disponible");
            }
        }

        // Consulta datos del mecánico desde service-mecanico (puerto 8087)
        if (cita.getMecanicoId() != null) {
            try {
                log.info("Consultando datos del mecánico con id: {}", cita.getMecanicoId());
                Object datosMecanico = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8087/api/v1/mecanicos/" + cita.getMecanicoId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                cita.setDatosMecanico(datosMecanico);
                log.info("Datos del mecánico obtenidos exitosamente");
            } catch (Exception e) {
                log.error("Error al obtener datos del mecánico: {}", e.getMessage());
                cita.setDatosMecanico("Mecánico no disponible");
            }
        }

        Cita guardada = citaRepository.save(cita);
        log.info("Cita guardada exitosamente con id: {}", guardada.getId());
        return guardada;
    }

    // Elimina una cita por su ID
    public void eliminar(Long id) {
        log.info("Eliminando cita con id: {}", id);
        citaRepository.deleteById(id);
        log.info("Cita eliminada exitosamente");
    }

    // Retorna todas las citas de un cliente específico
    public List<Cita> buscarPorClienteId(Long clienteId) {
        log.info("Buscando citas por clienteId: {}", clienteId);
        return citaRepository.findByClienteId(clienteId);
    }

    // Retorna todas las citas de un mecánico específico
    public List<Cita> buscarPorMecanicoId(Long mecanicoId) {
        log.info("Buscando citas por mecanicoId: {}", mecanicoId);
        return citaRepository.findByMecanicoId(mecanicoId);
    }

    // Retorna todas las citas filtradas por estado
    public List<Cita> buscarPorEstado(String estado) {
        log.info("Buscando citas por estado: {}", estado);
        return citaRepository.findByEstado(estado);
    }

    // Retorna todas las citas de un tipo específico
    public List<Cita> buscarPorTipoCitaId(Long tipoCitaId) {
        log.info("Buscando citas por tipoCitaId: {}", tipoCitaId);
        return citaRepository.findByTipoCitaId(tipoCitaId);
    }

    // Método privado que enriquece la cita con datos del cliente y mecánico
    private Cita enriquecerCita(Cita cita) {

        // Enriquecer con datos del cliente
        if (cita.getClienteId() != null) {
            try {
                log.info("Enriqueciendo cita con datos del cliente id: {}", cita.getClienteId());
                Object datosCliente = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8083/api/v1/clientes/" + cita.getClienteId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                cita.setDatosCliente(datosCliente);
                log.info("Datos del cliente obtenidos exitosamente");
            } catch (Exception e) {
                log.error("Error al obtener datos del cliente: {}", e.getMessage());
                cita.setDatosCliente("Cliente no disponible");
            }
        }

        // Enriquecer con datos del mecánico
        if (cita.getMecanicoId() != null) {
            try {
                log.info("Enriqueciendo cita con datos del mecánico id: {}", cita.getMecanicoId());
                Object datosMecanico = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8087/api/v1/mecanicos/" + cita.getMecanicoId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                cita.setDatosMecanico(datosMecanico);
                log.info("Datos del mecánico obtenidos exitosamente");
            } catch (Exception e) {
                log.error("Error al obtener datos del mecánico: {}", e.getMessage());
                cita.setDatosMecanico("Mecánico no disponible");
            }
        }

        return cita;
    }

    // Obtiene una cita completa con datos del cliente y mecánico via WebClient
    public Cita obtenerCitaCompleta(Long id) {
        log.info("Obteniendo cita completa con id: {}", id);
        Cita cita = citaRepository.findById(id).orElse(null);
        if (cita != null) {
            return enriquecerCita(cita);
        }
        log.warn("Cita con id: {} no encontrada", id);
        return null;
    }
}