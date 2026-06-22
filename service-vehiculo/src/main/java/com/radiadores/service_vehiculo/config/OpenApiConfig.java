package com.radiadores.service_vehiculo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server; // Importación necesaria
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Radiadores – Servicio Vehiculo")
                        .version("1.0")
                        .description("Gestion de vehiculos y tipos de vehiculo"))
                // ESTO ES LO QUE SOLUCIONA EL "FAILED TO FETCH":
                .servers(List.of(
                        new Server().url("http://localhost:9090").description("Servidor a través del Gateway")
                ));
    }
}
