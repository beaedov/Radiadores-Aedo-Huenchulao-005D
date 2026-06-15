package com.radiadores.service_factura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radiadores.service_factura.model.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    //buscar por clineteId
    List<Factura> findByClienteId(Long clienteId);

    //buscar por ordenReparacionId
    List<Factura> findByOrdenReparacionId(Long ordenReparacionId);

    //buscar por estado
    List<Factura> findByEstado(String estado);
}

