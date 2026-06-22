package com.radiadores.service_catalogo.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "compatibilidad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compatibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la compatibilidad", example = "1")
    private Long id;
    @Schema(description = "Marca del vehículo compatible", example = "Ford")
    private String marcaVehiculo;
    @Schema(description = "Modelo del vehículo compatible", example = "F-150")
    private String modeloVehiculo;
    @Schema(description = "Año del vehículo compatible", example = "2018")
    private int anio;

    @Schema(description = "Radiador asociado a esta compatibilidad")
    @ManyToOne
    @JoinColumn(name = "radiador_id")
    private Radiador radiador;
}