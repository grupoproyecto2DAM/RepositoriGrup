package org.simarro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;


@Entity
@Table(name = "incidencias")
public class Incidencia {

    @Schema(description = "Identificador de la incidencia", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nombre principal de la incidencia", example = "Valencia")
    @Column
    private String nombre;

    @Schema(description = "El tipo segun la incidencia que sea", example = "Madrid")
    @Column
    private String tipo;

    @Schema(description = "Es donde se ha realizado la incidencia", example = "15.2")
    @Column
    private String zona;

    @Schema(description = "Detalles de la incidencia", example = "Qatar Airlines")
    @Column
    private String descripcion;

    @Schema(description = "Cuando se ha realizado", example = "Qatar Airlines")
    @Column
    private String fecha;

    @Schema(description = "Cuando se ha realizado", example = "Qatar Airlines")
    @Column
    private Integer alumnoNIA;

    @Schema(description = "Cuando se ha realizado", example = "Qatar Airlines")
    @Column
    private String estado;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getAlumnoNIA() {
        return alumnoNIA;
    }

    public void setAlumnoNIA(Integer alumnoNIA) {
        this.alumnoNIA = alumnoNIA;
    }
}
