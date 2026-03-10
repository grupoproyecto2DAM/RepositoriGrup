package org.simarro.service.Incidencia;

import org.simarro.model.Incidencia;
import org.simarro.model.Usuari;

import java.util.List;

public interface IIncidenciaService {

    List<Incidencia> listar();
    List<Incidencia> buscarporNIA(Integer nia);
    Incidencia registrar(Incidencia incidencia);
    void eliminar(Integer id);

    // Métodos específicos de esta entidad
    void eliminarPorNombre(String nombre);
    boolean existsByNombre(String nombre);
    List<Incidencia> listarBusquedaPorZona(String zona);

}
