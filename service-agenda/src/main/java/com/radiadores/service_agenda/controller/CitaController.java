package com.radiadores.service_agenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_agenda.model.Cita;
import com.radiadores.service_agenda.service.CitaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Citas", description = "Operaciones sobre las citas del taller")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Operation(summary = "Listar citas", description = "Devuelve todas las citas registradas")
    @GetMapping
    public List<Cita> listar() {
        log.info("GET /api/v1/citas - Listando todas las citas");
        return citaService.obtenerTodos();
    }

    @Operation(summary = "Buscar cita por ID", description = "Devuelve una cita según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita encontrada"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/citas/{} - Buscando cita", id);
        return citaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener cita completa", description = "Devuelve la cita con datos del cliente y mecánico via WebClient")
    @GetMapping("/completa/{id}")
    public Cita obtenerCompleta(@PathVariable Long id) {
        log.info("GET /api/v1/citas/completa/{} - Obteniendo cita completa", id);
        return citaService.obtenerCitaCompleta(id);
    }

    @Operation(summary = "Buscar citas por cliente", description = "Devuelve las citas de un cliente específico")
    @GetMapping("/cliente/{clienteId}")
    public List<Cita> buscarPorCliente(@PathVariable Long clienteId) {
        log.info("GET /api/v1/citas/cliente/{} - Buscando citas por cliente", clienteId);
        return citaService.buscarPorClienteId(clienteId);
    }

    @Operation(summary = "Buscar citas por mecánico", description = "Devuelve las citas de un mecánico específico")
    @GetMapping("/mecanico/{mecanicoId}")
    public List<Cita> buscarPorMecanico(@PathVariable Long mecanicoId) {
        log.info("GET /api/v1/citas/mecanico/{} - Buscando citas por mecanico", mecanicoId);
        return citaService.buscarPorMecanicoId(mecanicoId);
    }

    @Operation(summary = "Buscar citas por estado", description = "Devuelve las citas filtradas por estado")
    @GetMapping("/estado/{estado}")
    public List<Cita> buscarPorEstado(@PathVariable String estado) {
        log.info("GET /api/v1/citas/estado/{} - Buscando citas por estado", estado);
        return citaService.buscarPorEstado(estado);
    }

    @Operation(summary = "Buscar citas por tipo", description = "Devuelve las citas de un tipo específico")
    @GetMapping("/tipo/{tipoCitaId}")
    public List<Cita> buscarPorTipo(@PathVariable Long tipoCitaId) {
        log.info("GET /api/v1/citas/tipo/{} - Buscando citas por tipo", tipoCitaId);
        return citaService.buscarPorTipoCitaId(tipoCitaId);
    }

    @Operation(summary = "Crear cita", description = "Registra una nueva cita")
    @PostMapping
    public Cita crear(@RequestBody Cita cita) {
        log.info("POST /api/v1/citas - Creando nueva cita");
        return citaService.guardar(cita);
    }

    @Operation(summary = "Eliminar cita", description = "Borra una cita según su ID")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/citas/{} - Eliminando cita", id);
        citaService.eliminar(id);
    }
}