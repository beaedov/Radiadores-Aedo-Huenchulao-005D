package com.radiadores.service_cotizacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radiadores.service_cotizacion.model.Cotizacion;
import com.radiadores.service_cotizacion.model.SolicitudCotizacion;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long>{

    // Buscar cotizacion por el objeto solicitud
    Cotizacion findBySolicitudCotizacion(SolicitudCotizacion solicitudCotizacion);

    // Buscar cotizacion por ID de solicitud (más práctico)
    Cotizacion findBySolicitudCotizacionId(Long solicitudCotizacionId);

}
