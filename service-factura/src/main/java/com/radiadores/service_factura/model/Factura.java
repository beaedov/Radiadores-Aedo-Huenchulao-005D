package com.radiadores.service_factura.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "factura")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la factura", example = "1")
    private Long id;
    @Schema(description = "Fecha de emisión de la factura", example = "2026-06-20")
    private LocalDate fechaEmision;
    @Schema(description = "Monto total de la factura en pesos chilenos", example = "57000")
    private int montoTotal;
    @Schema(description = "Estado de la factura", example = "pendiente")
    private String estado;

    @Schema(description = "ID del cliente asociado (referencia lógica)", example = "1")
    private Long clienteId;
    @Schema(hidden = true)
    @Transient
    private Object datosCliente;

    @Schema(description = "ID de la orden de reparación asociada (referencia lógica)", example = "1")
    private Long ordenReparacionId;
    @Schema(hidden = true)
    @Transient
    private Object datosOrden;
}