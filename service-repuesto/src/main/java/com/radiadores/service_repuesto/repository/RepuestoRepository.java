package com.radiadores.service_repuesto.repository;

import com.radiadores.service_repuesto.model.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto,Long> {
}
