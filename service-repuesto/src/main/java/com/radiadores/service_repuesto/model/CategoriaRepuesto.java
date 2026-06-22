package com.radiadores.service_repuesto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categoriaRepuesto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRepuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la categoría", example = "1")
    private Long id;
    @Schema(description = "Nombre de la categoría", example = "Sellos y empaquetaduras")
    private String nombreCategoria;
    @Schema(description = "Descripción de la categoría", example = "Repuestos para sellado del sistema")
    private String descripcion;
}
