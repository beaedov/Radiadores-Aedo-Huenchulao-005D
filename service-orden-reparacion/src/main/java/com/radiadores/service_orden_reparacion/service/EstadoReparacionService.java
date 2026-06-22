package com.radiadores.service_orden_reparacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radiadores.service_orden_reparacion.model.EstadoReparacion;
import com.radiadores.service_orden_reparacion.repository.EstadoReparacionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstadoReparacionService {

    @Autowired
    private EstadoReparacionRepository estadoReparacionRepository;

    public List<EstadoReparacion> findAll(){
        return estadoReparacionRepository.findAll();
    }

    public Optional<EstadoReparacion> findById(Long id) {
        return estadoReparacionRepository.findById(id);
    }

    public EstadoReparacion save(EstadoReparacion estadoReparacion){
        return estadoReparacionRepository.save(estadoReparacion);
    }

    public void delete(Long id) {
        estadoReparacionRepository.deleteById(id);
    }


}
