package org.simarro.repository;

import org.simarro.model.Espacio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEspacioRepository extends JpaRepository<Espacio, Integer> {

    // Métodos específicos de esta entidad mediante JPQL
    //Opción 1 - Eliminar
    @Modifying
    @Query(value = "DELETE FROM espacio WHERE nombre LIKE %:nombre%", nativeQuery = true)
    void eliminarPorNombre(@Param("nombre") String nombre);
    // Opción 2 - Eliminar
    void deleteByNombre(String nombre);



    // ExisteDestino
    boolean existsByNombre(String nombre);

    List<Espacio> findByNombre(String nombre);



}
