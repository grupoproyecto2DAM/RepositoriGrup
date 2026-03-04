package org.simarro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.simarro.model.enums.TipoEspacio;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "espacios")
public class Espacio {

    @Schema(description = "Identificador de la incidencia", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nombre principal de la incidencia", example = "Valencia")
    @Column
    private String nombre;


    @Schema(description = "Es donde se ha realizado la incidencia", example = "15.2")
    @Column
    private List<TipoEspacio> subzonas;

    @Schema(description = "Es donde se ha realizado la incidencia", example = "15.2")
    @Column
    private List<Incidencia> incidencias;

    @Schema(description = "Cuando se ha realizado", example = "Qatar Airlines")
    @Column
    private LocalDateTime fechayHora;

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

    public List<TipoEspacio> getSubzonas() {
        return subzonas;
    }

    public void setSubzonas(List<TipoEspacio> subzonas) {
        this.subzonas = subzonas;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public LocalDateTime getFechayHora() {
        return fechayHora;
    }

    public void setFechayHora(LocalDateTime fechayHora) {
        this.fechayHora = fechayHora;
    }
}
