package com.radiadores.service_pago.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration// Marca esta clase como configuracion que Spring lee al arrancar
public class WebClientConfig {

    @Bean // objeto que spring administra e inyecta en otras clases.
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();// crea la herramienta para hacer peticiones HTTP a otros microservicios
    }
}
