package com.radiadores.service_cliente.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del cliente", example = "1")
    private Long id;
    @Schema(description = "RUT del cliente", example = "12345678-9")
    private String rut;
    @Schema(description = "Nombre del cliente", example = "Thomas")
    private String nombre;
    @Schema(description = "Apellido del cliente", example = "Huenchulao")
    private String apellido;
    @Schema(description = "Correo del cliente", example = "thomas@gmail.com")
    private String correo;
    @Schema(description = "Teléfono de contacto", example = "+56912345678")
    private String telefono;
}