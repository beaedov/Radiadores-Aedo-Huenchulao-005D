package com.radiadores.service_catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radiadores.service_catalogo.model.Radiador;

@Repository
public interface RadiadorRepository extends JpaRepository<Radiador, Long>{

    List<Radiador> findByCategoriaId(Long categoriaId);

}
