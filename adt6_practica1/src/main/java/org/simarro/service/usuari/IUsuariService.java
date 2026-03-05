package org.simarro.service.usuari;

import org.simarro.model.Usuari;

import java.util.List;

public interface IUsuariService {

    List<Usuari> listar();
    Usuari registrar(Usuari usuari);
    Usuari modificar(Usuari usuari);
    void eliminar(Integer id);

    // Métodos específicos de esta entidad
    void eliminarPorNombre(String nombre);
    boolean existsByNombre(String nombre);

}
