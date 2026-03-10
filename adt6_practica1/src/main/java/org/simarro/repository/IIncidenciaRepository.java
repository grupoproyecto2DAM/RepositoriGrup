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

    @Modifying
    @Query(value = "DELETE FROM incidencia WHERE nombre LIKE %:nombre%", nativeQuery = true)
    void eliminarPorNombre(@Param("nombre") String nombre);

    // CORRECCIÓN AQUÍ: De NIA a Nia para que coincida con Usuari.nia
    List<Incidencia> findByAlumnoNia(Integer nia);

    boolean existsByNombre(String nombre);
}