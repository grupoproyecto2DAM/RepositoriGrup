package com.ragabe.dto.usuari;

import com.ragabe.model.enums.Rol;

public class UsuariResponseDto {

    // --------------------ATRIBUTOS--------------------
    private Long id;
    private String nombre;
    private String email;
    private Rol rol;
    private boolean activo;

    // --------------------CONSTRUCTOR--------------------
    public UsuariResponseDto() {}

    public UsuariResponseDto(Long id, String nombre, String email, Rol rol, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.activo = activo;
    }

    // --------------------GETTERS / SETTERS--------------------
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
}
