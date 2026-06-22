package com.radiadores.service_pago.service;

import com.radiadores.service_pago.model.MetodoPago;
import com.radiadores.service_pago.model.Pago;
import com.radiadores.service_pago.repository.MetodoPagoRepository;
import com.radiadores.service_pago.repository.PagoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Slf4j 
@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Pago guardar(Pago pago){ // Llega el pago que mandaste.
        log.info("Guardando pago para orden id: {}", pago.getOrdenReparacionId()); //Busca los datos de esa orden al otro microservicio.

        if (pago.getOrdenReparacionId() != null) {
            Object datosOrden = webClientBuilder.build() // arma el tobot
                    .get()// Crea un get
                    .uri("http://localhost:8085/api/v1/ordenes/" + pago.getOrdenReparacionId()) // va a esta url
                    .retrieve()// ejecuta la peticion
                    .bodyToMono(Object.class)// la respuesta JSON la guarda como objeto.
                    .block();// espera a que llegue la respuesta.
            pago.setDatosOrden(datosOrden); // guarda la respuesta que trajo webclient dentro del objeto pago (EN MEMORIA)
            log.info("Datos de la orden obtenidos exitosamente");
        }

        // guardar (GUARDA EN LA BD)
        Pago guardado = pagoRepository.save(pago);
        log.info("Pago guardado exitosamente con id: {}", guardado.getId());
        return guardado;
    }

    // CRUD Pago
    public List<Pago> obtenerTodos(){
        log.info("Obteniendo todos los pagos");
        return pagoRepository.findAll();
    }

    public Optional<Pago> buscarPorId(Long id){ // Optional evita errores nulos.
        log.info("Buscando pago con id: {}", id);
        return pagoRepository.findById(id);
    }

    public void eliminar(Long id) {
        log.info("Eliminando pago con id: {}", id);
        pagoRepository.deleteById(id);
        log.info("Pago eliminado exitosamente");
    }

    // METODO PAGO

    // CRUD METODO PAGO

    // El guardar de metodopago es mucho mas simple que pago ya que este no depende de ningun dato externo, todo ya lo trae el JSON.
    public MetodoPago guardarMetodo(MetodoPago metodoPago){
        log.info("Guardando metodo de pago: {}",metodoPago.getTipoPago());
        return metodoPagoRepository.save(metodoPago);
    }


    public List<MetodoPago> obtenerTodosMetodos(){
        log.info("Obteniendo todos los metodos de pago");
        return metodoPagoRepository.findAll();
    }

    public Optional<MetodoPago> buscarMetodoPorId(Long id) {
        log.info("Buscando metodo de pago con id: {}", id);
        return metodoPagoRepository.findById(id);
    }

    public void eliminarMetodo(Long id){
        log.info("Eliminando metodo de pago con id: {}",id);
        metodoPagoRepository.deleteById(id);
        log.info("Metodo de pago eliminado exitosamente");
    }

}
