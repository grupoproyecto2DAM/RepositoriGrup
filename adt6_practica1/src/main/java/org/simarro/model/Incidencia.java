package org.simarro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "incidencias")
public class Incidencia {

    @Schema(description = "Identificador de la incidencia", example = "12564")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nombre principal de la incidencia", example = "Sa cagao alguien")
    @Column
    private String nombre;

    @Schema(description = "El tipo segun la incidencia que sea", example = "Otros")
    @Column
    private String tipo;

    @Schema(description = "Es donde se ha realizado la incidencia", example = "PATIO")
    @Column
    private String zona;

    @Schema(description = "Detalles de la incidencia", example = "Huele mu mal")
    @Column
    private String descripcion;

    @Schema(description = "Cuando se ha realizado", example = "15/05/2025")
    @Column
    private String fecha;

    @Schema(description = "Estado de la incidencia", example = "En proceso")
    @Column
    private String estado;

    // Relación con el objeto Usuari completo
    @ManyToOne
    @JoinColumn(name = "alumnoNIA", referencedColumnName = "nia")
    private Usuari alumno;

    public Incidencia() {
    }

    // CONSTRUCTOR CORREGIDO
    public Incidencia(String nombre, String tipo, String zona, String descripcion, String fecha, Usuari alumno, String estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.zona = zona;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.alumno = alumno; // Ahora sí coinciden los tipos
        this.estado = estado;
    }

    // GETTERS Y SETTERS
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Usuari getAlumno() { return alumno; }
    public void setAlumno(Usuari alumno) { this.alumno = alumno; }
}