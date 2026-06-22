package com.radiadores.service_orden_reparacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radiadores.service_orden_reparacion.model.OrdenReparacion;

@Repository
public interface OrdenReparacionRepository extends JpaRepository<OrdenReparacion, Long> {

    List<OrdenReparacion> findByClienteId(Long clienteId);

}
