package com.radiadores.service_mecanico.service;

import com.radiadores.service_mecanico.model.Mecanico;
import com.radiadores.service_mecanico.repository.MecanicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MecanicoServiceTest {

    // Repositorio FALSO (no toca la base de datos)
    @Mock
    private MecanicoRepository mecanicoRepository;

    // Service REAL, con el repositorio falso metido adentro
    @InjectMocks
    private MecanicoService mecanicoService;

    @Test
    void guardarMecanicoTest() {
        Mecanico mecanico = new Mecanico();
        mecanico.setNombre("Juan");
        mecanico.setApellido("Pérez");

        Mecanico mecanicoGuardado = new Mecanico();
        mecanicoGuardado.setId(1L);
        mecanicoGuardado.setNombre("Juan");
        mecanicoGuardado.setApellido("Pérez");
        when(mecanicoRepository.save(mecanico)).thenReturn(mecanicoGuardado);

        Mecanico resultado = mecanicoService.guardar(mecanico);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan", resultado.getNombre());

        verify(mecanicoRepository, times(1)).save(mecanico);
    }

    @Test
    void buscarPorIdTest() {
        Mecanico mecanico = new Mecanico();
        mecanico.setId(1L);
        mecanico.setNombre("Pedro");

        when(mecanicoRepository.findById(1L)).thenReturn(Optional.of(mecanico));

        Optional<Mecanico> resultado = mecanicoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Pedro", resultado.get().getNombre());
        verify(mecanicoRepository, times(1)).findById(1L);
    }
}