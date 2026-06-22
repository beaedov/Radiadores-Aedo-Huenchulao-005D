package com.radiadores.service_pago.controller;

import com.radiadores.service_pago.model.Pago;
import com.radiadores.service_pago.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name ="Pagos", description = "Gestion de pagos del taller")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(summary = "Listar pagos", description = "Devuelve todos los pagos registrados")
    @GetMapping
    public List<Pago> listar(){
        log.info("GET /api/v1/pagos - Listando todos los pagos");
        return pagoService.obtenerTodos();
    }

    // Esto esta conectado con obtener
    @Operation(summary = "Buscar pago por ID", description = "Devuelve un pago segun su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtener(@PathVariable Long id){
        log.info("GET /api/v1/pagos/{} - Buscando pago", id);
        return pagoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear pago", description = "Registra un nuevo pago")
    @PostMapping
    public Pago crear(@RequestBody Pago pago){
        log.info("POST /api/v1/pagos - Creando nuevo pago");
        return pagoService.guardar(pago);
    }

    @Operation(summary = "Eliminar pago", description = "Borra un pago segun su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        log.info("DELETE /api/v1/pagos/{} - Eliminando pago", id);
        pagoService.eliminar(id);
    }
}
