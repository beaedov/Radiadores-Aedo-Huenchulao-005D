package com.radiadores.service_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radiadores.service_cliente.model.Resenia;
import java.util.List;

@Repository
public interface ReseniaRepository extends JpaRepository<Resenia, Long>{

    List<Resenia> findByClienteId(Long clienteId);

}
