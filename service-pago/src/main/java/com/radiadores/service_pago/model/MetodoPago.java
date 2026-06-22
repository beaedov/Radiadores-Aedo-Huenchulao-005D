package com.radiadores.service_pago.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "metodoPago")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del método de pago", example = "1")
    private Long id;
    @Schema(description = "Tipo de pago", example = "transferencia")
    private String tipoPago; // transferencia, efectivo, debito, credito.
}
