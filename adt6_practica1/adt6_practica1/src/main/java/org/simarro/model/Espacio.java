package org.simarro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "espacios")
public class Espacio {

    @Schema(description = "Identificador del lugar", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nombre del lugar", example = "Valencia")
    @Column
    private String nombre;


    @Schema(description = "Es la descripcion del lugar de la incidencia", example = "Dentro de la taza del WC del tercer aseo")
    @Column
    private String descripcion;

    @Schema(description = "La lista de incidencias", example = "Incidencia1, incidencia2...")
    @Column
    private String incidencias;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
