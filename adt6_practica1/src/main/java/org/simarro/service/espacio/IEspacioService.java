package org.simarro.service.espacio;

import org.simarro.model.Espacio;


import java.util.List;

public interface IEspacioService {

    List<Espacio> listar();
    Espacio registrar(Espacio espacio);
    void eliminar(Integer id);

    // Métodos específicos de esta entidad
    void eliminarPorNombre(String nombre);
    boolean existsByNombre(String nombre);
}
