package com.ragabe.dto.usuari;

import com.ragabe.model.enums.Rol;

public class UsuariRequestDto {


    // --------------------ATRIBUTOS--------------------
    private String nombre;
    private String email;
    private Rol rol;

    // --------------------CONSTRUCTOR--------------------
    public UsuariRequestDto() {
    }

    // --------------------GETTERS / SETTERS--------------------
    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public Rol getRol() {return rol;}

    public void setRol(Rol rol) {this.rol = rol;}
}
