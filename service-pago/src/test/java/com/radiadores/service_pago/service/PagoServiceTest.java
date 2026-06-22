package com.radiadores.service_pago.service;

import com.radiadores.service_pago.model.Pago;
import com.radiadores.service_pago.repository.MetodoPagoRepository;
import com.radiadores.service_pago.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    // Repositorios FALSOS (no tocan la base de datos)
    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private MetodoPagoRepository metodoPagoRepository;

    // Cada eslabon de la cadena del WebClient, todos falsos
    @Mock
    private WebClient.Builder webClientBuilder;
    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    // Service REAL, con todos los falsos metidos adentro
    @InjectMocks
    private PagoService pagoService;

    @Test
    void guardarPagoTest() {

        Pago pago = new Pago();
        pago.setOrdenReparacionId(5L);

        Pago pagoGuardado = new Pago();
        pagoGuardado.setId(1L);
        pagoGuardado.setOrdenReparacionId(5L);


        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(new Object()));


        when(pagoRepository.save(pago)).thenReturn(pagoGuardado);


        Pago resultado = pagoService.guardar(pago);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(pagoRepository, times(1)).save(pago);
    }
}
