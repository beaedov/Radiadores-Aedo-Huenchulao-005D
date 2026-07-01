package com.radiadores.service_cliente.service;
 
import com.radiadores.service_cliente.model.Cliente;
import com.radiadores.service_cliente.repository.ClienteRepository;
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
class ClienteServiceTest {
 
    // Repositorio FALSO (no toca la base de datos)
    @Mock
    private ClienteRepository clienteRepository;
 
    // Service REAL, con el repositorio falso metido adentro
    @InjectMocks
    private ClienteService clienteService;
 
    @Test
    void guardarClienteTest() {
        Cliente cliente = new Cliente();
        cliente.setRut("12345678-9");
        cliente.setNombre("Thomas");
        cliente.setApellido("Huenchulao");
        cliente.setCorreo("thomas@gmail.com");
        cliente.setTelefono("+56912345678");
 
        Cliente clienteGuardado = new Cliente();
        clienteGuardado.setId(1L);
        clienteGuardado.setRut("12345678-9");
        clienteGuardado.setNombre("Thomas");
        clienteGuardado.setApellido("Huenchulao");
        clienteGuardado.setCorreo("thomas@gmail.com");
        clienteGuardado.setTelefono("+56912345678");
 
        when(clienteRepository.save(cliente)).thenReturn(clienteGuardado);
 
        Cliente resultado = clienteService.guardar(cliente);
 
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Thomas", resultado.getNombre());
        assertEquals("12345678-9", resultado.getRut());
 
        verify(clienteRepository, times(1)).save(cliente);
    }
 
    @Test
    void buscarPorIdTest() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Pedro");
        cliente.setCorreo("pedro@gmail.com");
 
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
 
        Optional<Cliente> resultado = clienteService.buscarPorId(1L);
 
        assertTrue(resultado.isPresent());
        assertEquals("Pedro", resultado.get().getNombre());
        verify(clienteRepository, times(1)).findById(1L);
    }
 
    @Test
    void buscarPorIdNoExisteTest() {
        // Caso negativo: el cliente no existe en la base de datos
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
 
        Optional<Cliente> resultado = clienteService.buscarPorId(99L);
 
        assertTrue(resultado.isEmpty());
        verify(clienteRepository, times(1)).findById(99L);
    }
 
    @Test
    void obtenerTodosTest() {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNombre("Thomas");
 
        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNombre("Pedro");
 
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));
 
        List<Cliente> resultado = clienteService.obtenerTodos();
 
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Thomas", resultado.get(0).getNombre());
        assertEquals("Pedro", resultado.get(1).getNombre());
        verify(clienteRepository, times(1)).findAll();
    }
 
    @Test
    void eliminarClienteTest() {
        Long id = 1L;
 
        clienteService.eliminar(id);
 
        verify(clienteRepository, times(1)).deleteById(id);
    }
}