package com.radiadores.service_agenda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radiadores.service_agenda.model.TipoCita;
import com.radiadores.service_agenda.repository.TipoCitaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TipoCitaService {

    @Autowired
    private TipoCitaRepository tipoCitaRepository;

    // Retorna todos los tipos de cita almacenados en la base de datos
    public List<TipoCita> obtenerTodos() {
        log.info("Obteniendo todos los tipos de cita");
        return tipoCitaRepository.findAll();
    }

    // Busca un tipo de cita por su ID
    public Optional<TipoCita> buscarPorId(Long id) {
        log.info("Buscando tipo de cita con id: {}", id);
        return tipoCitaRepository.findById(id);
    }

    // Guarda un nuevo tipo de cita
    public TipoCita guardar(TipoCita tipoCita) {
        log.info("Guardando tipo de cita: {}", tipoCita.getNombre());
        TipoCita guardado = tipoCitaRepository.save(tipoCita);
        log.info("Tipo de cita guardado exitosamente con id: {}", guardado.getId());
        return guardado;
    }

    // Elimina un tipo de cita por su ID
    public void eliminar(Long id) {
        log.info("Eliminando tipo de cita con id: {}", id);
        tipoCitaRepository.deleteById(id);
        log.info("Tipo de cita eliminado exitosamente");
    }
}