package com.radiadores.service_orden_reparacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "garantia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Garantia {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Schema(description = "ID único de la garantía", example = "1")
    private Long id;

    @Schema(description = "Meses de cobertura de la garantía", example = "12")
    private int mesesCobertura;

    @Schema(description = "Orden de reparación asociada")
    @OneToOne
    @JoinColumn(name = "orden_reparacion_id")
    private OrdenReparacion ordenReparacion;

}
