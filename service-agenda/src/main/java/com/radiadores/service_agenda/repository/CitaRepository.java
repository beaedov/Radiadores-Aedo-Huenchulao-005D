package com.radiadores.service_agenda.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.radiadores.service_agenda.model.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByClienteId(Long clienteId);
    List<Cita> findByMecanicoId(Long mecanicoId);
    List<Cita> findByEstado(String estado);
    List<Cita> findByTipoCitaId(Long tipoCitaId);
}