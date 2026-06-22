package com.radiadores.service_orden_reparacion.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orden_reparacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenReparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la orden", example = "1")
    private Long id;

    @Schema(description = "Fecha de ingreso del vehículo", example = "2026-06-10")
    private LocalDate fechaIngreso;
    @Schema(description = "Fecha de entrega estimada", example = "2026-06-15")
    private LocalDate fechaEntrega;
    @Schema(description = "Costo final de la reparación en pesos", example = "250000")
    private int costoFinal;
    @Schema(description = "Descripción del trabajo realizado", example = "Cambio de radiador completo")
    private String descripcionTrabajo;

    @Schema(description = "ID del cliente", example = "3")
    private Long clienteId; //referencia logica al clienteId

    @Schema(description = "Datos del cliente traídos desde service-cliente (no se guarda en BD)")
    @Transient
    private Object datosCliente;  
}
