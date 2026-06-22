package com.radiadores.service_catalogo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_catalogo.model.Categoria;
import com.radiadores.service_catalogo.repository.CategoriaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Categorías", description = "Operaciones sobre las categorías de radiadores")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Operation(summary = "Listar categorías", description = "Devuelve todas las categorías registradas")
    @GetMapping
    public List<Categoria> listar() {
        log.info("GET /api/v1/categorias - Listando todas las categorias");
        return categoriaRepository.findAll();
    }

    @Operation(summary = "Buscar categoría por ID", description = "Devuelve una categoría según su ID")
    @GetMapping("/{id}")
    public Optional<Categoria> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/v1/categorias/{} - Buscando categoria", id);
        return categoriaRepository.findById(id);
    }

    @Operation(summary = "Crear categoría", description = "Registra una nueva categoría")
    @PostMapping
    public Categoria crear(@RequestBody Categoria categoria) {
        log.info("POST /api/v1/categorias - Creando nueva categoria");
        return categoriaRepository.save(categoria);
    }

    @Operation(summary = "Eliminar categoría", description = "Borra una categoría según su ID")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/categorias/{} - Eliminando categoria", id);
        categoriaRepository.deleteById(id);
    }
}