package com.ragabe.mappers;

import com.ragabe.dto.usuari.UsuariRequestDto;
import com.ragabe.dto.usuari.UsuariResponseDto;
import com.ragabe.model.Usuari;

public class UsuariMapper {

    public static Usuari toEntity(UsuariRequestDto dto) {
        Usuari usuari = new Usuari();
        usuari.setNombre(dto.getNombre());
        usuari.setEmail(dto.getEmail());
        usuari.setRol(dto.getRol());
        usuari.setActivo(true);
        return usuari;
    }

    public static UsuariResponseDto toResponseDto(Usuari usuari) {
        return new UsuariResponseDto(
                usuari.getId(),
                usuari.getNombre(),
                usuari.getEmail(),
                usuari.getRol(),
                usuari.isActivo()
        );
    }
}
