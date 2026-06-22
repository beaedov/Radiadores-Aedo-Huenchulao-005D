package com.radiadores.service_cotizacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radiadores.service_cotizacion.model.SolicitudCotizacion;

@Repository
public interface SolicitudCotizacionRepository extends JpaRepository<SolicitudCotizacion, Long>{

}
