package com.radiadores.service_mecanico.repository;

import com.radiadores.service_mecanico.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad,Long> {
}
