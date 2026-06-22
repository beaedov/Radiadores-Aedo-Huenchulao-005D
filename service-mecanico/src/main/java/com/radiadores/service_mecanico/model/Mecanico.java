package com.radiadores.service_mecanico.model;

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
@Table(name = "mecanico")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mecanico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del mecánico", example = "1")
    private Long id;
    @Schema(description = "RUT del mecánico", example = "12345678-9")
    private String rut;
    @Schema(description = "Nombre del mecánico", example = "Juan")
    private String nombre;
    @Schema(description = "Apellido del mecánico", example = "Pérez")
    private String apellido;
    @Schema(description = "Correo del mecánico", example = "juan@taller.cl")
    private String correo;
    @Schema(description = "Teléfono de contacto", example = "+56912345678")
    private String telefono;
    @Schema(description = "Indica si el mecánico está activo", example = "true")
    private boolean activo; // Si esta activo trabajando o no

    @Schema(description = "Especialidad asociada al mecánico")
    @ManyToOne
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;
}
