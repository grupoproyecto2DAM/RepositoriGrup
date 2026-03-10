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


    List<Incidencia> findByAlumnoNIA(Integer alumnoNIA);

    boolean existsByNombre(String nombre);

}
