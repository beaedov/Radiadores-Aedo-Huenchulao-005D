package com.radiadores.service_cotizacion.service;

import com.radiadores.service_cotizacion.model.Cotizacion;
import com.radiadores.service_cotizacion.model.SolicitudCotizacion;
import com.radiadores.service_cotizacion.repository.CotizacionRepository;
import com.radiadores.service_cotizacion.repository.SolicitudCotizacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CotizacionServiceTest {

    // Repositorios FALSOS (no tocan la base de datos)
    @Mock
    private CotizacionRepository cotizacionRepository;

    @Mock
    private SolicitudCotizacionRepository solicitudCotizacionRepository;

    // mocks para simular el webclient que llama al service de clientes
    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    // Service REAL, con los mocks metidos adentro
    @InjectMocks
    private CotizacionService cotizacionService;

    @SuppressWarnings("unchecked")
    private void simularLlamadaCliente(Object respuestaCliente) {
        when(webClientBuilder.build()).thenReturn(webClient);
        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString());
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(respuestaCliente));
    }

    private SolicitudCotizacion crearSolicitud(Long id, Long clienteId) {
        SolicitudCotizacion solicitud = new SolicitudCotizacion();
        solicitud.setId(id);
        solicitud.setDescripcionProblema("Radiador con fuga de agua");
        solicitud.setTipoVehiculo("Auto");
        solicitud.setFechaSolicitud(LocalDate.of(2026, 6, 20));
        solicitud.setClienteId(clienteId);
        return solicitud;
    }

    @Test
    void guardarCotizacionTest() {
        SolicitudCotizacion solicitud = crearSolicitud(1L, 5L);

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setSolicitudCotizacion(solicitud);
        cotizacion.setEstado("pendiente");
        cotizacion.setPrecioEstimado(65000);
        cotizacion.setDescripcion("Reparación de fuga en radiador");
        cotizacion.setTiempoEstimado(2);

        Cotizacion cotizacionGuardada = new Cotizacion();
        cotizacionGuardada.setId(10L);
        cotizacionGuardada.setSolicitudCotizacion(solicitud);
        cotizacionGuardada.setEstado("pendiente");
        cotizacionGuardada.setPrecioEstimado(65000);

        when(solicitudCotizacionRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        simularLlamadaCliente("{nombre: Thomas}");
        when(cotizacionRepository.save(cotizacion)).thenReturn(cotizacionGuardada);

        Cotizacion resultado = cotizacionService.guardar(cotizacion);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("pendiente", resultado.getEstado());
        assertEquals(65000, resultado.getPrecioEstimado());

        verify(solicitudCotizacionRepository, times(1)).findById(1L);
        verify(cotizacionRepository, times(1)).save(cotizacion);
    }

    @Test
    void guardarCotizacionSinClienteTest() {
        SolicitudCotizacion solicitud = crearSolicitud(2L, null);

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setSolicitudCotizacion(solicitud);
        cotizacion.setEstado("pendiente");

        Cotizacion cotizacionGuardada = new Cotizacion();
        cotizacionGuardada.setId(11L);
        cotizacionGuardada.setSolicitudCotizacion(solicitud);

        when(solicitudCotizacionRepository.findById(2L)).thenReturn(Optional.of(solicitud));
        when(cotizacionRepository.save(cotizacion)).thenReturn(cotizacionGuardada);

        Cotizacion resultado = cotizacionService.guardar(cotizacion);

        assertNotNull(resultado);
        assertEquals(11L, resultado.getId());

        verify(webClientBuilder, never()).build();
        verify(cotizacionRepository, times(1)).save(cotizacion);
    }

    @Test
    void guardarSolicitudNoExisteTest() {
        SolicitudCotizacion solicitud = crearSolicitud(99L, 5L);

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setSolicitudCotizacion(solicitud);

        when(solicitudCotizacionRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(RuntimeException.class,
                () -> cotizacionService.guardar(cotizacion));

        assertEquals("Solicitud no encontrada", excepcion.getMessage());
        verify(cotizacionRepository, never()).save(any(Cotizacion.class));
    }

    @Test
    void obtenertodosTest() {
        Cotizacion cotizacion1 = new Cotizacion();
        cotizacion1.setId(1L);
        cotizacion1.setEstado("pendiente");

        Cotizacion cotizacion2 = new Cotizacion();
        cotizacion2.setId(2L);
        cotizacion2.setEstado("aprobada");

        when(cotizacionRepository.findAll()).thenReturn(Arrays.asList(cotizacion1, cotizacion2));

        List<Cotizacion> resultado = cotizacionService.obtenertodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("pendiente", resultado.get(0).getEstado());
        assertEquals("aprobada", resultado.get(1).getEstado());
        verify(cotizacionRepository, times(1)).findAll();
    }

    @Test
    void buscarPorIdTest() {
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId(1L);
        cotizacion.setDescripcion("Reparación de fuga en radiador");

        when(cotizacionRepository.findById(1L)).thenReturn(Optional.of(cotizacion));

        Optional<Cotizacion> resultado = cotizacionService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Reparación de fuga en radiador", resultado.get().getDescripcion());
        verify(cotizacionRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorIdNoExisteTest() {
        when(cotizacionRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Cotizacion> resultado = cotizacionService.buscarPorId(99L);

        assertTrue(resultado.isEmpty());
        verify(cotizacionRepository, times(1)).findById(99L);
    }

    @Test
    void eliminarCotizacionTest() {
        Long id = 1L;

        cotizacionService.eliminar(id);

        verify(cotizacionRepository, times(1)).deleteById(id);
    }

    @Test
    void buscarPorSolicitudCotizacionIdTest() {
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId(1L);
        cotizacion.setSolicitudCotizacion(crearSolicitud(7L, 5L));

        when(cotizacionRepository.findBySolicitudCotizacionId(7L)).thenReturn(cotizacion);

        Cotizacion resultado = cotizacionService.buscarPorSolicitudCotizacionId(7L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(7L, resultado.getSolicitudCotizacion().getId());
        verify(cotizacionRepository, times(1)).findBySolicitudCotizacionId(7L);
    }

    @Test
    void obtenerCotizacionCompletaTest() {
        SolicitudCotizacion solicitud = crearSolicitud(1L, 5L);

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId(1L);
        cotizacion.setSolicitudCotizacion(solicitud);

        when(cotizacionRepository.findById(1L)).thenReturn(Optional.of(cotizacion));
        simularLlamadaCliente("{nombre: Thomas, apellido: Huenchulao}");

        Cotizacion resultado = cotizacionService.obtenerCotizacionCompleta(1L);

        assertNotNull(resultado);
        assertNotNull(resultado.getSolicitudCotizacion().getDatosCliente());
        assertEquals("{nombre: Thomas, apellido: Huenchulao}",
                resultado.getSolicitudCotizacion().getDatosCliente());
        verify(cotizacionRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerCotizacionCompletaClienteCaidoTest() {
        SolicitudCotizacion solicitud = crearSolicitud(1L, 5L);

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId(1L);
        cotizacion.setSolicitudCotizacion(solicitud);

        when(cotizacionRepository.findById(1L)).thenReturn(Optional.of(cotizacion));
        when(webClientBuilder.build()).thenThrow(new RuntimeException("Servicio de clientes no disponible"));

        Cotizacion resultado = cotizacionService.obtenerCotizacionCompleta(1L);

        assertNotNull(resultado);
        assertEquals("Información de cliente no disponible actualmente",
                resultado.getSolicitudCotizacion().getDatosCliente());
    }

    @Test
    void obtenerCotizacionCompletaNoExisteTest() {
        when(cotizacionRepository.findById(99L)).thenReturn(Optional.empty());

        Cotizacion resultado = cotizacionService.obtenerCotizacionCompleta(99L);

        assertNull(resultado);
        verify(cotizacionRepository, times(1)).findById(99L);
        verify(webClientBuilder, never()).build();
    }
}