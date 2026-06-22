package com.radiadores.service_cotizacion.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "solicitud_cotizacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudCotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la solicitud", example = "1")
    private Long id;
    @Schema(description = "Descripción del problema reportado", example = "Radiador con fuga de agua")
    private String descripcionProblema;
    @Schema(description = "Tipo de vehículo", example = "Auto")
    private String tipoVehiculo;
    @Schema(description = "Fecha en que se realizó la solicitud", example = "2026-06-20")
    private LocalDate fechaSolicitud;

    @Schema(description = "ID del cliente asociado (referencia lógica)", example = "1")
    private Long clienteId;

    @Schema(hidden = true)
    @Transient
    private Object datosCliente;
}