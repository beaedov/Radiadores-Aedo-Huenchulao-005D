package com.radiadores.service_cliente.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "resenia")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resenia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la reseña", example = "1")
    private Long id;
    @Schema(description = "Puntuación otorgada por el cliente (1 a 5)", example = "5")
    private int puntuacion;
    @Schema(description = "Comentario del cliente sobre el servicio", example = "Excelente atención")
    private String comentario;
    @Schema(description = "Fecha en que se publicó la reseña", example = "2026-06-20")
    private LocalDate fechaPublicacion;

    @Schema(description = "Cliente asociado a la reseña")
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;
}