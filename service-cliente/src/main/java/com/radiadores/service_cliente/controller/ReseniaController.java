package com.radiadores.service_cliente.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_cliente.model.Resenia;
import com.radiadores.service_cliente.repository.ReseniaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Reseñas", description = "Operaciones sobre las reseñas de clientes")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/resenias")
public class ReseniaController {

    @Autowired
    private ReseniaRepository reseniaRepository;

    @Operation(summary = "Listar reseñas", description = "Devuelve todas las reseñas registradas")
    @GetMapping
    public List<Resenia> listar() {
        log.info("GET /api/v1/resenias - Listando todas las resenias");
        return reseniaRepository.findAll();
    }

    @Operation(summary = "Buscar reseña por ID", description = "Devuelve una reseña según su ID")
    @GetMapping("/{id}")
    public Optional<Resenia> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/v1/resenias/{} - Buscando resenia", id);
        return reseniaRepository.findById(id);
    }

    @Operation(summary = "Crear reseña", description = "Registra una nueva reseña")
    @PostMapping
    public Resenia crear(@RequestBody Resenia resenia) {
        log.info("POST /api/v1/resenias - Creando nueva resenia");
        return reseniaRepository.save(resenia);
    }

    @Operation(summary = "Eliminar reseña", description = "Borra una reseña según su ID")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/resenias/{} - Eliminando resenia", id);
        reseniaRepository.deleteById(id);
    }

    @Operation(summary = "Buscar reseñas por cliente", description = "Devuelve las reseñas de un cliente específico")
    @GetMapping("/cliente/{clienteId}")
    public List<Resenia> buscarPorCliente(@PathVariable Long clienteId) {
        log.info("GET /api/v1/resenias/cliente/{} - Buscando resenias por cliente", clienteId);
        return reseniaRepository.findByClienteId(clienteId);
    }
}