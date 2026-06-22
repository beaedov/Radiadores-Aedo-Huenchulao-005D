package com.radiadores.service_pago.controller;

import com.radiadores.service_pago.model.MetodoPago;
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
@Tag(name ="Metodos de pago", description = "Gestion de metodos de pago")
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/metodospago")
public class MetodoPagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(summary = "Listar metodos de pago", description = "Devuelve todos los metodos de pago registrados")
    @GetMapping
    public List<MetodoPago> listar(){
        log.info("GET /api/v1/metodospago - Listando todos los metodos de pago");
        return pagoService.obtenerTodosMetodos();
    }

    // Esto esta conectado con obtener
    @Operation(summary = "Buscar metodo de pago por ID", description = "Devuelve un metodo de pago segun su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Metodo de Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Metodo de Pago no encontrado")
    })
    @GetMapping("/{id}")
    //ResponseEntity = controla el codigo http para devolver 200 OK o 404 NOFOUND.
    public ResponseEntity<MetodoPago> obtener(@PathVariable Long id){ // PathVariable = Variable que viene de la ruta, toma el numero de la url.
        log.info("GET /api/v1/metodospago/{} - Buscando metodo de pago", id);
        return pagoService.buscarMetodoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }           // .map y .orElse Muestran 200 o 404

    @Operation(summary = "Crear metodo de pago", description = "Registra un nuevo metodo de pago")
    @PostMapping
    public MetodoPago crear(@RequestBody MetodoPago metodoPago){
        log.info("POST /api/v1/metodospago - Creando nuevo metodo de pago");
        return pagoService.guardarMetodo(metodoPago);
    }

    @Operation(summary = "Eliminar metodo de pago", description = "Borra un metodo de pago segun su ID")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        log.info("DELETE /api/v1/metodospago/{} - Eliminando metodo de pago", id);
        pagoService.eliminarMetodo(id);
    }
}
