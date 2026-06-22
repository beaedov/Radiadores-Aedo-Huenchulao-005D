package com.radiadores.service_mecanico.service;

import com.radiadores.service_mecanico.model.Mecanico;
import com.radiadores.service_mecanico.repository.MecanicoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j // Anotación de Lombok que te crea un objeto log para escribir mensajes en la consola.
       // Los logs son como un GPS del código. Te dicen por dónde pasó la ejecución y dónde se rompió.
@Service
public class MecanicoService {

    @Autowired
    private MecanicoRepository mecanicoRepository;

    //obtener todos los mecanicos
    public List<Mecanico> obtenerTodos(){
        log.info("Obteniendo todos los mecanicos");
        return mecanicoRepository.findAll();
    }

    //obtener mecanico por id
    public Optional<Mecanico> buscarPorId(Long id){
        log.info("Buscando mecanico por id:{}",id);
        return mecanicoRepository.findById(id);
    }

    //guardar mecanico
    @Transactional // te asegura que un método se ejecuta como una sola operación en la base de datos:
                   // o se hace todo, o no se hace nada.
    public Mecanico guardar(Mecanico mecanico){
        log.info("Guardando mecanico: {}", mecanico.getNombre());
        Mecanico guardado = mecanicoRepository.save(mecanico);
        log.info("Mecanico guardado exitosamente con id: {}", guardado.getId());
        return guardado;
    }

    //eliminar mecanico
    @Transactional
    public void eliminar(Long id){
        log.info("Eliminando mecanico con id: {}", id);
        mecanicoRepository.deleteById(id);
        log.info("Mecanico eliminado exitosamente");
    }
}
