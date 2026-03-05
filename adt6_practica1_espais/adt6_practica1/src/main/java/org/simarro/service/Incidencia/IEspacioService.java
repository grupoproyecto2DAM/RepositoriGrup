package org.simarro.service.Incidencia;

import org.simarro.model.Espacio;


import java.util.List;

public interface IEspacioService {

    List<Espacio> listar();
    Espacio registrar(Espacio espacio);
    Espacio modificar(Espacio espacio);
    void eliminar(Integer id);

    // Métodos específicos de esta entidad
    void eliminarPorNombre(String nombre);
    boolean existsByNombre(String nombre);
    List<Espacio> listarBusquedaFiltrada2(String nombre);
}
