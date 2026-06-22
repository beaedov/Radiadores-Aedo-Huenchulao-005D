package com.radiadores.service_factura.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_factura")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del detalle", example = "1")
    private Long id;
    @Schema(description = "Descripción del ítem facturado", example = "Mano de obra reparación radiador")
    private String descripcion;
    @Schema(description = "Cantidad de unidades", example = "1")
    private int cantidad;
    @Schema(description = "Precio unitario en pesos chilenos", example = "45000")
    private int precioUnitario;
    @Schema(description = "Subtotal calculado (cantidad x precioUnitario)", example = "45000")
    private int subtotal;

    @Schema(description = "Factura asociada a este detalle")
    @ManyToOne
    @JoinColumn(name = "factura_id")
    private Factura factura;
}