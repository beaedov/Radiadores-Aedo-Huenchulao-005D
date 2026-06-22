package com.radiadores.service_vehiculo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.radiadores.service_vehiculo.model.TipoVehiculo;
import com.radiadores.service_vehiculo.model.Vehiculo;
import com.radiadores.service_vehiculo.repository.TipoVehiculoRepository;
import com.radiadores.service_vehiculo.repository.VehiculoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Vehiculo guardar(Vehiculo vehiculo) {
        log.info("Guardando vehiculo con patente: {}", vehiculo.getPatente());

        // Buscar el TipoVehiculo completo si solo viene el ID
        if (vehiculo.getTipoVehiculo() != null && vehiculo.getTipoVehiculo().getId() != null) {
            log.info("Buscando tipo de vehiculo con id: {}", vehiculo.getTipoVehiculo().getId());
            TipoVehiculo tipoVehiculo = tipoVehiculoRepository
                .findById(vehiculo.getTipoVehiculo().getId())
                .orElseThrow(() -> new RuntimeException("TipoVehiculo no encontrado"));
            vehiculo.setTipoVehiculo(tipoVehiculo);
        }

        // Enriquecer con datos del cliente
        if (vehiculo.getClienteId() != null) {
            log.info("Consultando datos del cliente con id: {}", vehiculo.getClienteId());
            try {
                Object datosCliente = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8083/api/v1/clientes/" + vehiculo.getClienteId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                vehiculo.setDatosCliente(datosCliente);
                log.info("Datos del cliente obtenidos exitosamente");
            } catch (Exception e) {
                log.error("Error al obtener datos del cliente: {}", e.getMessage());
                vehiculo.setDatosCliente("Cliente no disponible");
            }
        }

        // Guardar
        Vehiculo guardado = vehiculoRepository.save(vehiculo);
        log.info("Vehiculo guardado exitosamente con id: {}", guardado.getId());
        return guardado;
    }

    public List<Vehiculo> obtenerTodos() {
        log.info("Obteniendo todos los vehiculos");
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> buscarPorId(Long id) {
        log.info("Buscando vehiculo con id: {}", id);
        return vehiculoRepository.findById(id);
    }

    public void eliminar(Long id) {
        log.info("Eliminando vehiculo con id: {}", id);
        vehiculoRepository.deleteById(id);
        log.info("Vehiculo eliminado exitosamente");
    }

    public List<Vehiculo> buscarPorClienteId(Long clienteId) {
        log.info("Buscando vehiculos por clienteId: {}", clienteId);
        return vehiculoRepository.findByClienteId(clienteId);
    }

    public List<Vehiculo> buscarPorTipoVehiculoId(Long tipoVehiculoId) {
        log.info("Buscando vehiculos por tipoVehiculoId: {}", tipoVehiculoId);
        return vehiculoRepository.findByTipoVehiculoId(tipoVehiculoId);
    }

    // Método privado para enriquecer con datos del cliente
    private Vehiculo enriquecerConCliente(Vehiculo vehiculo) {
        if (vehiculo.getClienteId() != null) {
            try {
                log.info("Enriqueciendo vehiculo con datos del cliente id: {}", vehiculo.getClienteId());
                Object datosCliente = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8083/api/v1/clientes/" + vehiculo.getClienteId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                vehiculo.setDatosCliente(datosCliente);
                log.info("Datos del cliente obtenidos exitosamente");
            } catch (Exception e) {
                log.error("Error al obtener datos del cliente: {}", e.getMessage());
                vehiculo.setDatosCliente("Cliente no disponible");
            }
        }
        return vehiculo;
    }

    // Obtener vehiculo completo con datos del cliente
    public Vehiculo obtenerVehiculoCompleto(Long id) {
        log.info("Obteniendo vehiculo completo con id: {}", id);
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElse(null);
        if (vehiculo != null) {
            return enriquecerConCliente(vehiculo);
        }
        log.warn("Vehiculo con id: {} no encontrado", id);
        return null;
    }
}