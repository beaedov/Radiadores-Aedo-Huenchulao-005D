package com.radiadores.service_agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.radiadores.service_agenda.model.TipoCita;

@Repository
public interface TipoCitaRepository extends JpaRepository<TipoCita, Long> {

}