package com.ragabe.service.usuari;

import com.ragabe.dto.usuari.UsuariRequestDto;
import com.ragabe.dto.usuari.UsuariResponseDto;
import com.ragabe.mappers.UsuariMapper;
import com.ragabe.model.Usuari;
import com.ragabe.repository.IUsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IUsuariServiceImpl implements UsuariService{

    @Autowired
    private IUsuariRepository usuariRepository;

    @Override
    public List<UsuariResponseDto> findAll() {
        return usuariRepository.findAll()
                .stream()
                .map(UsuariMapper::toResponseDto)
                .toList();
    }

    @Override
    public UsuariResponseDto findById(Long id) {
        Usuari usuari = usuariRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuari no trobat"));
        return UsuariMapper.toResponseDto(usuari);
    }

    @Override
    public UsuariResponseDto create(UsuariRequestDto dto) {
        Usuari usuari = new Usuari();
        usuari.setNombre(dto.getNombre());
        usuari.setEmail(dto.getEmail());
        usuari.setRol(dto.getRol());
        usuari.setActivo(true);

        Usuari saved  = usuariRepository.save(usuari);

        return UsuariMapper.toResponseDto(saved);
    }

    @Override
    public UsuariResponseDto update(Long id, UsuariRequestDto dto) {
        Usuari usuari = usuariRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuari no trobat"));

        usuari.setNombre(dto.getNombre());
        usuari.setEmail(dto.getEmail());
        usuari.setRol(dto.getRol());

        Usuari saved = usuariRepository.save(usuari);

        return UsuariMapper.toResponseDto(saved);
    }

    @Override
    public void delete(Long id) {
        usuariRepository.deleteById(id);
    }
}
