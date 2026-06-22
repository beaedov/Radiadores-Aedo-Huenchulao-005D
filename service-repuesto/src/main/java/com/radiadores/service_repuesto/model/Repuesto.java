package com.radiadores.service_repuesto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repuesto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del repuesto", example = "1")
    private Long id;
    @Schema(description = "Nombre del repuesto", example = "Tapa de radiador")
    private String nombre;
    @Schema(description = "Código interno del repuesto", example = "RAD-001")
    private String codigo;
    @Schema(description = "Precio del repuesto en pesos", example = "12000")
    private int precio;
    @Schema(description = "Cantidad disponible en stock", example = "20")
    private int stock;
    @Schema(description = "ID de la orden donde se usó", example = "5")
    private Long ordenReparacionId; // Referencia en que orden se uso.

    @Schema(description = "Datos de la orden traídos desde service-orden-reparacion (no se guarda en BD)")
    @Transient // Este campo solo existe en la memoria.
    // contenedor temporal donde se guarda la respuesta del webCLient cuando llamas al servicio de ordenes.
    private Object datosOrden;

    @Schema(description = "Categoría a la que pertenece el repuesto")
    @ManyToOne//muchos repuestos pertenecen a una misma categoria.
    @JoinColumn(name = "categoria_repuesto_id")//crea una columna en la tabla repuestos
    private CategoriaRepuesto categoriaRepuesto;//asocia la entidad 1 con la 2.


}
