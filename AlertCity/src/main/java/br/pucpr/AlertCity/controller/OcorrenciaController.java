package br.pucpr.AlertCity.controller;

import br.pucpr.AlertCity.dto.OcorrenciaDTO;
import br.pucpr.AlertCity.service.OcorrenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaService service;


    @PostMapping
    public OcorrenciaDTO criar(@RequestBody OcorrenciaDTO dto) {
        return service.salvar(dto);
    }


    @GetMapping
    public List<OcorrenciaDTO> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    public OcorrenciaDTO atualizar(@PathVariable Long id, @RequestBody OcorrenciaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}