package com.ragabe.model;

import com.ragabe.utils.UsuariConfig;
import jakarta.persistence.*;
import com.ragabe.model.enums.Rol;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuari {
    @Column
    private String nombre;

    @Id
    @Column(unique = true, nullable = false)
    private String nia;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private boolean activo;

    @OneToMany(mappedBy = "creadaPor")
    private String alumnoNia;

    @OneToMany(mappedBy = "asignadaA")
    private List<Incidencia> incidenciasAsignadas = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuari)) return false;
        Usuari that = (Usuari) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public Rol getRol() {return rol;}

    public void setRol(Rol rol) {this.rol = rol;}

    public boolean isActivo() {return activo;}

    public void setActivo(boolean activo) {this.activo = activo;}

    public List<Incidencia> getIncidenciasCreadas() {return incidenciasCreadas;}

    public void setIncidenciasCreadas(List<Incidencia> incidenciasCreadas) {this.incidenciasCreadas = incidenciasCreadas;}

    public List<Incidencia> getIncidenciasAsignadas() {return incidenciasAsignadas;}

    public void setIncidenciasAsignadas(List<Incidencia> incidenciasAsignadas) {this.incidenciasAsignadas = incidenciasAsignadas;}
}
