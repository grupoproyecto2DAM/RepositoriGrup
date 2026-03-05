package org.simarro.repository;

import org.simarro.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IIncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    // Métodos específicos de esta entidad mediante JPQL
    //Opción 1 - Eliminar
    @Modifying
    @Query(value = "DELETE FROM incidencia WHERE nombre LIKE %:nombre%", nativeQuery = true)
    void eliminarPorNombre(@Param("nombre") String nombre);
    // Opción 2 - Eliminar
    void deleteByNombre(String nombre);


    List<Incidencia> findByTipoAndZona(String tipo, String zona);
    List<Incidencia> findByZona(String zona);
    List<Incidencia> findByNombreAndZona(String nombre, String zona);
    List<Incidencia> findByNombre(String nombre);
    List<Incidencia> findByTipo(String tipo);
    List<Incidencia> findByNombreAndTipo(String nombre, String tipo);
    boolean existsByNombre(String nombre);

    // Opción 1 - ExisteDestino
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN 'true' ELSE 'false' END FROM Incidencia v WHERE v.tipo = :tipo")
    boolean existePorTipo(@Param("tipo") String tipo);


}
