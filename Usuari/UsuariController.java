package com.ragabe.controller;

import com.ragabe.dto.usuari.UsuariRequestDto;
import com.ragabe.dto.usuari.UsuariResponseDto;
import com.ragabe.service.usuari.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuaris")
public class UsuariController {


    @Autowired
    private UsuariService usuariService;

    @GetMapping
    public List<UsuariResponseDto> getAll() {
        return usuariService.findAll();
    }

    @GetMapping("/{id}")
    public UsuariResponseDto getById(@PathVariable Long id) {
        return usuariService.findById(id);
    }

    @PostMapping
    public UsuariResponseDto create(@RequestBody UsuariRequestDto dto) {
        return usuariService.create(dto);
    }

    @PutMapping("/{id}")
    public UsuariResponseDto update(@PathVariable Long id, @RequestBody UsuariRequestDto dto) {
        return usuariService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuariService.delete(id);
    }
}
