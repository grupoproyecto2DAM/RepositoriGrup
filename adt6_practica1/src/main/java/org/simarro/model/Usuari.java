package org.simarro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;


@Entity
@Table(name = "usuaris")
public class Usuari {

    public Usuari() {
    }

    public Usuari(String nombre, String password, String rol, String curso, String materia) {
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.curso = curso;
        this.materia = materia;
    }

    @Schema(description = "Identificador de la incidencia", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nia;

    @Schema(description = "Nombre principal de la incidencia", example = "Pedro")
    @Column
    private String nombre;

    @Schema(description = "Contraseña del usuario", example = "12345678")
    @Column
    private String password;

    @Schema(description = "Si es alumno/profesor/mantenimiento...", example = "profesor")
    @Column
    private String rol;

    @Schema(description = "Detalles del alumno", example = "Es mu weno")
    @Column
    private String curso;

    @Schema(description = "No viene de mas, solo si es profe", example = "Matematicas")
    @Column
    private String materia;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNia() {
        return nia;
    }

    public void setNia(Integer nia) {
        this.nia = nia;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
}
