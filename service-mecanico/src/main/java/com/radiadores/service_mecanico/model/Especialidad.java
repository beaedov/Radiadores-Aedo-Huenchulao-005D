package com.radiadores.service_mecanico.model;

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
@Table(name = "especialidad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la especialidad", example = "1")
    private Long id;
    @Schema(description = "Nombre de la especialidad", example = "Soldadura")
    private String nombre;
    @Schema(description = "Descripción de la especialidad", example = "Reparación de fugas por soldadura")
    private String descripcion;

}

