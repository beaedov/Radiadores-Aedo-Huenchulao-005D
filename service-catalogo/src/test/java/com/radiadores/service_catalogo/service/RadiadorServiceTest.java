package com.radiadores.service_catalogo.service;

import com.radiadores.service_catalogo.model.Categoria;
import com.radiadores.service_catalogo.model.Radiador;
import com.radiadores.service_catalogo.repository.RadiadorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RadiadorServiceTest {

    // Repositorio FALSO (no toca la base de datos)
    @Mock
    private RadiadorRepository radiadorRepository;

    // Service REAL, con el repositorio falso metido adentro
    @InjectMocks
    private RadiadorService radiadorService;

    @Test
    void guardarRadiadorTest() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Camión");

        Radiador radiador = new Radiador();
        radiador.setMarca("Nissens");
        radiador.setPrecio(45000);
        radiador.setStock(10);
        radiador.setCategoria(categoria);

        Radiador radiadorGuardado = new Radiador();
        radiadorGuardado.setId(1L);
        radiadorGuardado.setMarca("Nissens");
        radiadorGuardado.setPrecio(45000);
        radiadorGuardado.setStock(10);
        radiadorGuardado.setCategoria(categoria);

        when(radiadorRepository.save(radiador)).thenReturn(radiadorGuardado);

        Radiador resultado = radiadorService.guardar(radiador);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Nissens", resultado.getMarca());
        assertEquals(45000, resultado.getPrecio());

        verify(radiadorRepository, times(1)).save(radiador);
    }

    @Test
    void buscarPorIdTest() {
        Radiador radiador = new Radiador();
        radiador.setId(1L);
        radiador.setMarca("Nissens");
        radiador.setStock(10);

        when(radiadorRepository.findById(1L)).thenReturn(Optional.of(radiador));

        Optional<Radiador> resultado = radiadorService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Nissens", resultado.get().getMarca());
        verify(radiadorRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorIdNoExisteTest() {
        when(radiadorRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Radiador> resultado = radiadorService.buscarPorId(99L);

        assertTrue(resultado.isEmpty());
        verify(radiadorRepository, times(1)).findById(99L);
    }

    @Test
    void obtenerTodosTest() {
        Radiador radiador1 = new Radiador();
        radiador1.setId(1L);
        radiador1.setMarca("Nissens");

        Radiador radiador2 = new Radiador();
        radiador2.setId(2L);
        radiador2.setMarca("Valeo");

        when(radiadorRepository.findAll()).thenReturn(Arrays.asList(radiador1, radiador2));

        List<Radiador> resultado = radiadorService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Nissens", resultado.get(0).getMarca());
        assertEquals("Valeo", resultado.get(1).getMarca());
        verify(radiadorRepository, times(1)).findAll();
    }

    @Test
    void eliminarRadiadorTest() {
        Long id = 1L;

        radiadorService.eliminar(id);

        verify(radiadorRepository, times(1)).deleteById(id);
    }

    @Test
    void buscarPorCategoriaIdTest() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Camión");

        Radiador radiador1 = new Radiador();
        radiador1.setId(1L);
        radiador1.setMarca("Nissens");
        radiador1.setCategoria(categoria);

        Radiador radiador2 = new Radiador();
        radiador2.setId(2L);
        radiador2.setMarca("Valeo");
        radiador2.setCategoria(categoria);

        when(radiadorRepository.findByCategoriaId(1L)).thenReturn(Arrays.asList(radiador1, radiador2));

        List<Radiador> resultado = radiadorService.buscarPorCategoriaId(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Camión", resultado.get(0).getCategoria().getNombre());
        verify(radiadorRepository, times(1)).findByCategoriaId(1L);
    }
}