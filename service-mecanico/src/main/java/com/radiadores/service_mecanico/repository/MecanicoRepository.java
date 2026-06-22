package com.radiadores.service_mecanico.repository;

import com.radiadores.service_mecanico.model.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico,Long> {
}
