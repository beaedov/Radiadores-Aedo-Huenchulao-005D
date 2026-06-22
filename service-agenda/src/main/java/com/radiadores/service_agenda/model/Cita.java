package com.radiadores.service_agenda.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cita")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la cita", example = "1")
    private Long id;
    @Schema(description = "Fecha de la cita", example = "2026-06-20")
    private LocalDate fechaCita;
    @Schema(description = "Hora de la cita", example = "10:00")
    private String horaCita;
    @Schema(description = "Estado de la cita", example = "pendiente")
    private String estado;

    @Schema(description = "ID del cliente asociado (referencia lógica)", example = "1")
    private Long clienteId;
    @Schema(hidden = true)
    @Transient
    private Object datosCliente;

    @Schema(description = "ID del mecánico asociado (referencia lógica)", example = "1")
    private Long mecanicoId;
    @Schema(hidden = true)
    @Transient
    private Object datosMecanico;

    @Schema(description = "Tipo de cita asociado")
    @ManyToOne
    @JoinColumn(name = "tipo_cita_id")
    private TipoCita tipoCita;
}