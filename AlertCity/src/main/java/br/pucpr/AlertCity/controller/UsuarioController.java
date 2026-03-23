package br.pucpr.AlertCity.controller;

import br.pucpr.AlertCity.dto.UsuarioDTO;
import br.pucpr.AlertCity.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public UsuarioDTO criar(@RequestBody UsuarioDTO dto) {
        return service.criarUsuario(dto);
    }
}