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
    @Query(value = "DELETE FROM incidencia WHERE titulo LIKE %:titulo%", nativeQuery = true)
    void eliminarPorTitulo(@Param("titulo") String titulo);
    // Opción 2 - Eliminar
    void deleteByTitulo(String titulo);


    List<Incidencia> findByTipoAndZona(String tipo, String zona);
    List<Incidencia> findByZona(String zona);
    List<Incidencia> findByTituloAndZona(String titulo, String zona);
    List<Incidencia> findByTitulo(String titulo);
    List<Incidencia> findByTipo(String tipo);
    List<Incidencia> findByTituloAndTipo(String titulo, String tipo);


    // Opción 1 - ExisteDestino
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN 'true' ELSE 'false' END FROM Incidencia v WHERE v.tipo = :tipo")
    boolean existePorTipo(@Param("tipo") String tipo);

    // Opción 2 - ExisteDestino
    boolean existsByTitulo(String titulo);

    List<Incidencia> findByTituloAndTipoAndZona(String titulo, String tipo, String zona);



}
