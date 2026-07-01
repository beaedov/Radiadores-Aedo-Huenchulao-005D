package com.radiadores.service_repuesto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.radiadores.service_repuesto.model.Repuesto;
import com.radiadores.service_repuesto.repository.RepuestoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class RepuestoServiceTest {

    @Mock  // Repositorio falso 
    private RepuestoRepository repuestoRepository;

    @Mock // WebCLient falso
    private WebClient.Builder webClientBuilder;
    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks // Injecta todos los mocks al service real
    private RepuestoService repuestoService;

    @Test
    void guardarRepuestoTest(){

        Repuesto repuesto = new Repuesto();
        repuesto.setOrdenReparacionId(5L);

        Repuesto repuestoGuardado = new Repuesto();
        repuestoGuardado.setId(1L);
        repuestoGuardado.setOrdenReparacionId(5L);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(new Object()));


        when(repuestoRepository.save(repuesto)).thenReturn(repuestoGuardado);


        Repuesto resultado = repuestoService.guardar(repuesto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repuestoRepository, times(1)).save(repuesto);
    }

}
