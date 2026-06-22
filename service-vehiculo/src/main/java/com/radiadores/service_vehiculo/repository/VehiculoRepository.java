package com.radiadores.service_vehiculo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radiadores.service_vehiculo.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo,Long> {

    //buscar vehiculo por clienteId
    List<Vehiculo> findByClienteId(Long clienteId);

    //buscar vehiculo por tipoVehiculo
    List<Vehiculo> findByTipoVehiculoId(Long tipoVehiculoId);
}
