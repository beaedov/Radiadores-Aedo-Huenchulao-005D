package com.radiadores.service_repuesto.repository;

import com.radiadores.service_repuesto.model.CategoriaRepuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepuestoRepository extends JpaRepository<CategoriaRepuesto,Long> {
}
