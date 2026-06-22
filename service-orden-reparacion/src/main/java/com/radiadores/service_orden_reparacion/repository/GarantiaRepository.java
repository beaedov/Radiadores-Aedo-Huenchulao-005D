package com.radiadores.service_orden_reparacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radiadores.service_orden_reparacion.model.Garantia;

@Repository
public interface GarantiaRepository extends JpaRepository<Garantia, Long>{

}
