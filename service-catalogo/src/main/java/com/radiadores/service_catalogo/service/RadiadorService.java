package com.radiadores.service_catalogo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radiadores.service_catalogo.model.Radiador;
import com.radiadores.service_catalogo.repository.RadiadorRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RadiadorService {

    @Autowired
    private RadiadorRepository radiadorRepository;

    //obtener todos los radiadores
    public List<Radiador> obtenerTodos(){
        log.info("Obteniendo todos los radiadores");
        return radiadorRepository.findAll();
    }

    //obtener radiador por id
    public Optional<Radiador> buscarPorId(Long id){
        log.info("Buscando radiador con id: {}", id);
        return radiadorRepository.findById(id);
    }

    //guardar radiador
    @Transactional
    public Radiador guardar(Radiador radiador){
        log.info("Guardando radiador: {}", radiador);
        Radiador guardado = radiadorRepository.save(radiador);
        log.info("Radiador guardado exitosamente con id: {}", guardado.getId());
        return guardado;
    }

    //eliminar radiador
    public void eliminar(Long id){
        log.info("Eliminando radiador con id: {}", id);
        radiadorRepository.deleteById(id);
        log.info("Radiador eliminado exitosamente");
    }

    //obtener radiador categoria por id
    public List<Radiador> buscarPorCategoriaId(Long categoriaId) {
        log.info("Buscando radiadores por categoriaId: {}", categoriaId);
        return radiadorRepository.findByCategoriaId(categoriaId);
    }

}
