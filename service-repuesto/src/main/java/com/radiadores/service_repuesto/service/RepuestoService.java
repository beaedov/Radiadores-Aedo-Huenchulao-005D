package com.radiadores.service_repuesto.service;

import com.radiadores.service_repuesto.model.Repuesto;
import com.radiadores.service_repuesto.repository.RepuestoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Slf4j // Lombok te crea el log para los mensajes de consola
@Service // Spring registra esta clase como service
public class RepuestoService {

    @Autowired
    private RepuestoRepository repuestoRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Transactional
    public Repuesto guardar(Repuesto repuesto){ // Llega el repuesto que mandaste.
        log.info("Guardando repuesto para orden id: {}", repuesto.getOrdenReparacionId()); //Busca los datos de esa orden al otro microservicio.

        if (repuesto.getOrdenReparacionId() != null) {
            Object datosOrden = webClientBuilder.build() // arma el tobot
                    .get()// Crea un get
                    .uri("http://localhost:8085/api/v1/ordenes/" + repuesto.getOrdenReparacionId()) // va a esta url
                    .retrieve()// ejecuta la peticion
                    .bodyToMono(Object.class)// la respuesta JSON la guarda como objeto.
                    .block();// espera a que llegue la respuesta.
            repuesto.setDatosOrden(datosOrden); // guarda la respuesta que trajo webclient dentro del objeto repuesto (EN MEMORIA)
            log.info("Datos de la orden obtenidos exitosamente");
        }

        // guardar (GUARDA EN LA BD)
        Repuesto guardado = repuestoRepository.save(repuesto);
        log.info("Repuesto guardado exitosamente con id: {}", guardado.getId());
        return guardado;
    }


    //obtener todos los repuestos
    public List<Repuesto> obtenerTodos(){
        log.info("Obteniendo todos los repuestos");
        return repuestoRepository.findAll();
    }

    //obtener repuesto por id
    public Optional<Repuesto> buscarPorId(Long id){//El Optional es una "caja" que puede contener un valor o estar vacía. Sirve para evitar errores cuando buscas algo que quizás no existe.
        log.info("Buscando repuesto por id:{}",id);//Si existe el repuesto la caja lo trae adentro, Si no existe la caja está vacía (pero NO es null, así que no explota)
        return repuestoRepository.findById(id);
    }

    //eliminar repuesto
    @Transactional
    public void eliminar(Long id){
        log.info("Eliminando repuesto con id: {}", id);
        repuestoRepository.deleteById(id);
        log.info("Repuesto eliminado exitosamente");
    }
}
