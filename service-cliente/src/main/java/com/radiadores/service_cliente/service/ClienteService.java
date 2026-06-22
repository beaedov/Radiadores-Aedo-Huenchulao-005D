package com.radiadores.service_cliente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radiadores.service_cliente.model.Cliente;
import com.radiadores.service_cliente.repository.ClienteRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    //obtener todos los clientes
    public List<Cliente> obtenerTodos(){
        log.info("Obteniendo todos los clientes");
        return clienteRepository.findAll();
    }

    //obtener cliente por id
    public Optional<Cliente> buscarPorId(Long id){
        log.info("Buscando cliente por id:{}",id);
        return clienteRepository.findById(id);
    }

    //guardar cliente
    @Transactional
    public Cliente guardar(Cliente cliente){
        log.info("Guardando cliente: {}", cliente.getNombre());
        Cliente guardado = clienteRepository.save(cliente);
        log.info("Cliente guardado exitosamente con id: {}", guardado.getId());
        return guardado;
    }

    //eliminar cliente
    public void eliminar(Long id){
        log.info("Eliminando cliente con id: {}", id);
        clienteRepository.deleteById(id);
        log.info("Cliente eliminado exitosamente");
    }

}
