package com.radiadores.service_repuesto.service;

import com.radiadores.service_repuesto.model.CategoriaRepuesto;
import com.radiadores.service_repuesto.model.Repuesto;
import com.radiadores.service_repuesto.repository.CategoriaRepuestoRepository;
import com.radiadores.service_repuesto.repository.RepuestoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoriaRepuestoService {

    @Autowired
    private CategoriaRepuestoRepository categoriaRepuestoRepository;

    //obtener todos las categorias de repuestos
    public List<CategoriaRepuesto> obtenerTodos(){
        log.info("Obteniendo todas las categorias de repuestos");
        return categoriaRepuestoRepository.findAll();
    }

    //obtener repuesto por id
    public Optional<CategoriaRepuesto> buscarPorId(Long id){//El Optional es una "caja" que puede contener un valor o estar vacía. Sirve para evitar errores cuando buscas algo que quizás no existe.
        log.info("Buscando categorias de repuestos por id:{}",id);//Si existe el repuesto la caja lo trae adentro, Si no existe la caja está vacía (pero NO es null, así que no explota)
        return categoriaRepuestoRepository.findById(id);
    }

    //guardar repuesto
    @Transactional // te asegura que un método se ejecuta como una sola operación en la base de datos: o se hace todo, o no se hace nada.
    public CategoriaRepuesto guardar(CategoriaRepuesto categoriaRepuesto){
        log.info("Guardando categoria de repuesto: {}", categoriaRepuesto.getNombreCategoria());
        CategoriaRepuesto guardado = categoriaRepuestoRepository.save(categoriaRepuesto);
        log.info("Categoria de repuesto guardado exitosamente con id: {}", guardado.getId());
        return guardado;
    }

    //eliminar repuesto
    @Transactional
    public void eliminar(Long id){
        log.info("Eliminando categoria de repuesto con id: {}", id);
        categoriaRepuestoRepository.deleteById(id);
        log.info("Categoria de repuesto eliminado exitosamente");
    }
}
