package com.radiadores.service_factura.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="factura")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaEmision;
    private int montoTotal;
    private String estado; // "pendiente", "pagada", "anulada"

    private Long clienteId; // referencia lógica a service-cliente
    @Transient
    private Object datosCliente;

    private Long ordenReparacionId; // referencia lógica a service-orden-reparacion
    @Transient
    private Object datosOrden;

}
