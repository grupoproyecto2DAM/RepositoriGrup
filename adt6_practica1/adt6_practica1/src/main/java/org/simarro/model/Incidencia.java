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
    private String titulo;

    @Schema(description = "El tipo segun la incidencia que sea", example = "Madrid")
    @Column
    private String tipo;

    @Schema(description = "Es donde se ha realizado la incidencia", example = "15.2")
    @Column
    private String zona;

    @Schema(description = "Detalles de la incidencia", example = "Qatar Airlines")
    @Column
    private String descripcion;

    @Schema(description = "Imagen de la issue", example = "Qatar Airlines")
    @Column
    private String image;

    @Schema(description = "Cuando se ha realizado", example = "Qatar Airlines")
    @Column
    private String fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
