package com.radiadores.service_vehiculo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radiadores.service_vehiculo.model.TipoVehiculo;
import com.radiadores.service_vehiculo.repository.TipoVehiculoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TipoVehiculoService {

    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    public List<TipoVehiculo> obtenerTodos() {
        log.info("Obteniendo todos los tipos de vehiculo");
        return tipoVehiculoRepository.findAll();
    }

    public Optional<TipoVehiculo> buscarPorId(Long id) {
        log.info("Buscando tipo de vehiculo con id: {}", id);
        return tipoVehiculoRepository.findById(id);
    }

    public TipoVehiculo guardar(TipoVehiculo tipoVehiculo) {
        log.info("Guardando tipo de vehiculo: {}", tipoVehiculo.getNombre());
        TipoVehiculo guardado = tipoVehiculoRepository.save(tipoVehiculo);
        log.info("Tipo de vehiculo guardado exitosamente con id: {}", guardado.getId());
        return guardado;
    }

    public void eliminar(Long id) {
        log.info("Eliminando tipo de vehiculo con id: {}", id);
        tipoVehiculoRepository.deleteById(id);
        log.info("Tipo de vehiculo eliminado exitosamente");
    }
}
