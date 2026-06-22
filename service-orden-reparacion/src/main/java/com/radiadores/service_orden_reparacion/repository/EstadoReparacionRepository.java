package com.radiadores.service_orden_reparacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radiadores.service_orden_reparacion.model.EstadoReparacion;

@Repository
public interface EstadoReparacionRepository extends JpaRepository<EstadoReparacion, Long> {

}
