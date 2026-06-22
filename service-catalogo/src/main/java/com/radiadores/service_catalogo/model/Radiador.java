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
@Table(name = "radiador")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Radiador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del radiador", example = "1")
    private Long id;
    @Schema(description = "Marca del radiador", example = "Nissens")
    private String marca;
    @Schema(description = "Precio del radiador en pesos chilenos", example = "45000")
    private int precio;
    @Schema(description = "Stock disponible", example = "10")
    private int stock;

    @Schema(description = "Categoría asociada al radiador")
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}