package com.radiadores.service_orden_reparacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radiadores.service_orden_reparacion.model.Garantia;
import com.radiadores.service_orden_reparacion.repository.GarantiaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GarantiaService {

    @Autowired
    private GarantiaRepository garantiaRepository;

    public List<Garantia> findAll(){
        return garantiaRepository.findAll();
    }

    public Optional<Garantia> findById(Long id) {
        return garantiaRepository.findById(id);
    }

    public Garantia save(Garantia garantia){
        return garantiaRepository.save(garantia);
    }

    public void delete(Long id) {
        garantiaRepository.deleteById(id);
    }
}
