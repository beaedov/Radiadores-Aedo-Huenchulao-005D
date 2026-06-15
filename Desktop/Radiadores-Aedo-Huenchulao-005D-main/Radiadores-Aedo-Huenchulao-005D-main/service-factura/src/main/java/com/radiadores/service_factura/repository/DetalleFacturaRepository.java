package com.radiadores.service_factura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radiadores.service_factura.model.DetalleFactura;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long>{

    //buscar por facturaId
    List<DetalleFactura> findByFacturaId(Long facturaId);

}
