package com.radiadores.service_pago.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "pago")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del pago", example = "1")
    private Long id;
    @Schema(description = "Monto del pago en pesos", example = "150000")
    private int monto;
    @Schema(description = "Fecha en que se realizó el pago", example = "2026-06-17")
    private LocalDate fechaPago;
    @Schema(description = "Estado del pago", example = "pagado")
    private String estado; // pendiente o pagado
    @Schema(description = "ID de la orden de reparación asociada", example = "5")
    private Long ordenReparacionId; // referencia logica a service-orden-reparacion

    @Schema(description = "Datos de la orden traídos desde service-orden-reparacion (no se guarda en BD)")
    @Transient
    private Object datosOrden;

    @Schema(description = "Método de pago utilizado")
    @ManyToOne
    @JoinColumn(name = "metodo_pago_id")
    private MetodoPago metodoPago;

}
