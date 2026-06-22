package com.radiadores.service_agenda.model;

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
@Table(name = "tipo_cita")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del tipo de cita", example = "1")
    private Long id;
    @Schema(description = "Nombre del tipo de cita", example = "Diagnóstico")
    private String nombre;
}