package br.pucpr.AlertCity.controller;

import br.pucpr.AlertCity.dto.LoginDTO;
import br.pucpr.AlertCity.dto.UsuarioDTO;
import br.pucpr.AlertCity.dto.UsuarioResponseDTO;
import br.pucpr.AlertCity.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public UsuarioResponseDTO criar(@RequestBody UsuarioDTO dto) {
        return service.criarUsuario(dto);
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return service.listarUsuarios();
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        return service.atualizarUsuario(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletarUsuario(id);
    }

    @PostMapping("/login")
    public UsuarioResponseDTO login(@RequestBody LoginDTO loginDTO) {
        return service.fazerLogin(loginDTO);
    }
}