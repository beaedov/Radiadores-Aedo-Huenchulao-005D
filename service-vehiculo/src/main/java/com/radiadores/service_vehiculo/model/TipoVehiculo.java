package com.radiadores.service_vehiculo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tipo_vehiculo")

public class TipoVehiculo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Schema(description = "ID único del tipo de vehículo", example = "1")
    private Long id;
    @Schema(description = "Nombre del tipo", example = "Automóvil")
    private String nombre;
}
