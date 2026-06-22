package com.radiadores.service_mecanico.service;

import com.radiadores.service_mecanico.model.Especialidad;

import com.radiadores.service_mecanico.repository.EspecialidadRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j // Anotación de Lombok que te crea un objeto log para escribir mensajes en la consola.
       // Los logs son como un GPS del código. Te dicen por dónde pasó la ejecución y dónde se rompió.
@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    //obtener todas las especialidades
    public List<Especialidad> obtenerTodos(){
        log.info("Obteniendo todas las especialidades");
        return especialidadRepository.findAll();
    }

    //obtener especialidad por id
    public Optional<Especialidad> buscarPorId(Long id){ // opcional es una caja que puede traer una especialidad o estar vacia, para evitar el nullPointerException.
        log.info("Buscando especialidad por id:{}",id);
        return especialidadRepository.findById(id);
    }

    //guardar especialidad
    @Transactional // te asegura que un método se ejecuta como una sola operación en la base de datos: o se hace todo, o no se hace nada.
    public Especialidad guardar(Especialidad especialidad){
        log.info("Guardando Especialidad: {}", especialidad.getNombre());
        Especialidad guardado = especialidadRepository.save(especialidad);
        log.info("Especialidad guardada exitosamente con id: {}", guardado.getId());
        return guardado;
    }

    //eliminar especialidad
    @Transactional
    public void eliminar(Long id){
        log.info("Eliminando especialidad con id: {}", id);
        especialidadRepository.deleteById(id);
        log.info("Especialidad eliminada exitosamente");
    }
}
