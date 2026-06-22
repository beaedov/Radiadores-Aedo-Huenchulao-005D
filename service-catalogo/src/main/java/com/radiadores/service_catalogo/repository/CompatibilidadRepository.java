package com.radiadores.service_catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radiadores.service_catalogo.model.Compatibilidad;

@Repository
public interface CompatibilidadRepository extends JpaRepository<Compatibilidad, Long>{

    List<Compatibilidad> findByRadiadorId(Long radiadorId);

}
