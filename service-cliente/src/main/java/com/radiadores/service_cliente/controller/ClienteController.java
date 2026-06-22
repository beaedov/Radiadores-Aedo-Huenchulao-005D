package com.radiadores.service_cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_cliente.model.Cliente;
import com.radiadores.service_cliente.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Clientes", description = "Operaciones sobre los clientes del taller")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar clientes", description = "Devuelve todos los clientes registrados")
    @GetMapping
    public List<Cliente> listar(){
        log.info("GET /api/v1/clientes - Listando todos los clientes");
        return clienteService.obtenerTodos();
    }

    @Operation(summary = "Buscar cliente por ID", description = "Devuelve un cliente según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Long id){
        log.info("GET /api/v1/clientes/{} - Buscando cliente", id);
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear cliente", description = "Registra un nuevo cliente")
    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente){
        log.info("POST /api/v1/clientes - Creando nuevo cliente");
        return clienteService.guardar(cliente);
    }

    @Operation(summary = "Eliminar cliente", description = "Borra un cliente según su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        log.info("DELETE /api/v1/clientes/{} - Eliminando cliente", id);
        clienteService.eliminar(id);
    }
}