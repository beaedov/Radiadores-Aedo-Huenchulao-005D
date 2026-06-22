package com.radiadores.service_cotizacion.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "cotizacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la cotización", example = "1")
    private Long id;
    @Schema(description = "Fecha en que se generó la cotización", example = "2026-06-20")
    private LocalDate fechaCotizacion;
    @Schema(description = "Estado de la cotización", example = "pendiente")
    private String estado;
    @Schema(description = "Precio estimado en pesos chilenos", example = "65000")
    private int precioEstimado;
    @Schema(description = "Descripción del trabajo a realizar", example = "Reparación de fuga en radiador")
    private String descripcion;
    @Schema(description = "Tiempo estimado en días", example = "2")
    private int tiempoEstimado;

    @Schema(description = "Solicitud de cotización asociada")
    @OneToOne
    @JoinColumn(name = "solicitud_cotizacion_id")
    private SolicitudCotizacion solicitudCotizacion;
}