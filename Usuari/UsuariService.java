package com.ragabe.service.usuari;


import com.ragabe.dto.usuari.UsuariRequestDto;
import com.ragabe.dto.usuari.UsuariResponseDto;
import com.ragabe.model.Usuari;

import java.util.List;

public interface UsuariService {

    List<UsuariResponseDto> findAll();

    UsuariResponseDto findById(Long id);

    UsuariResponseDto create(UsuariRequestDto dto);

    UsuariResponseDto update(Long id,UsuariRequestDto dto);

    void delete(Long id);
}
