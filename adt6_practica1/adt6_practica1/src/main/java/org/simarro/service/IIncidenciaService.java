package org.simarro.service;

import org.simarro.model.Incidencia;

import java.util.List;

public interface IIncidenciaService {

    List<Incidencia> listar();
    Incidencia registrar(Incidencia incidencia);
    Incidencia modificar(Incidencia incidencia);
    void eliminar(Integer id);

    // Métodos específicos de esta entidad
    void eliminarPorTitulo(String titulo);
    boolean existsByTitulo(String titulo);
    List<Incidencia> listarBusquedaFiltrada1(String titulo, String tipo, String zona);
    List<Incidencia> listarBusquedaFiltrada2(String titulo, String tipo, String zona);
}
