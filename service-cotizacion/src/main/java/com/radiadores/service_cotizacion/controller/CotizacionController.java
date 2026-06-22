package com.radiadores.service_cotizacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.radiadores.service_cotizacion.model.Cotizacion;
import com.radiadores.service_cotizacion.service.CotizacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Cotizaciones", description = "Operaciones sobre las cotizaciones de reparación")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/cotizaciones")
public class CotizacionController {

    @Autowired
    private CotizacionService cotizacionService;

    @Operation(summary = "Listar cotizaciones", description = "Devuelve todas las cotizaciones registradas")
    @GetMapping
    public List<Cotizacion> listar(){
        log.info("GET /api/v1/cotizaciones - Listando todas las cotizaciones");
        return cotizacionService.obtenertodos();
    }

    @Operation(summary = "Buscar cotización por ID", description = "Devuelve una cotización según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cotización encontrada"),
            @ApiResponse(responseCode = "404", description = "Cotización no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cotizacion> obtener(@PathVariable Long id){
        log.info("GET /api/v1/cotizaciones/{} - Obteniendo cotizacion completa", id);
        Cotizacion cotizacion = cotizacionService.obtenerCotizacionCompleta(id);
        if (cotizacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cotizacion);
    }

    @Operation(summary = "Crear cotización", description = "Registra una nueva cotización")
    @PostMapping
    public Cotizacion crear(@RequestBody Cotizacion cotizacion){
        log.info("POST /api/v1/cotizaciones - Creando nueva cotizacion");
        return cotizacionService.guardar(cotizacion);
    }

    @Operation(summary = "Eliminar cotización", description = "Borra una cotización según su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        log.info("DELETE /api/v1/cotizaciones/{} - Eliminando cotizacion", id);
        cotizacionService.eliminar(id);
    }

    @Operation(summary = "Buscar cotización por solicitud", description = "Devuelve la cotización asociada a una solicitud")
    @GetMapping("/solicitudes/{solicitudCotizacionId}")
    public Cotizacion buscarPorSolicitudCotizacionId(@PathVariable Long solicitudCotizacionId) {
        log.info("GET /api/v1/cotizaciones/solicitudes/{} - Buscando cotizacion por solicitud", solicitudCotizacionId);
        return cotizacionService.buscarPorSolicitudCotizacionId(solicitudCotizacionId);
    }
}