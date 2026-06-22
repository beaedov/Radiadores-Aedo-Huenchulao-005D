package com.radiadores.service_orden_reparacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estado_reparacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoReparacion {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Schema(description = "ID único del estado", example = "1")
    private Long id;

    @Schema(description = "Situación de la reparación", example = "En proceso")
    private String situacion;

    @Schema(description = "Orden de reparación asociada")
    @ManyToOne
    @JoinColumn(name = "orden_reparacion_id")
    private OrdenReparacion ordenReparacion;

}
