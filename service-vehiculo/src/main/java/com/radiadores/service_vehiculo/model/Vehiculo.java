package com.radiadores.service_vehiculo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="vehiculo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Schema(description = "ID único del vehículo", example = "1")
    private Long id;
    @Schema(description = "Patente del vehículo", example = "ABCD12")
    private String patente;
    @Schema(description = "Marca del vehículo", example = "Toyota")
    private String marca;
    @Schema(description = "Año del vehículo", example = "2018")
    private int anio;

    @Schema(description = "Tipo de vehículo asociado")
    @ManyToOne
    @JoinColumn(name= "tipo_Vehiculo_id")
    private TipoVehiculo tipoVehiculo;

    @Schema(description = "ID del cliente dueño", example = "3")
    private Long clienteId; // referencia logica al clinteId

    @Schema(description = "Datos del cliente traídos desde service-cliente (no se guarda en BD)")
    @Transient
    private Object datosCliente; 
    

}
