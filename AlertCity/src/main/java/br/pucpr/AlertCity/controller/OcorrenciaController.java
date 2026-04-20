package br.pucpr.AlertCity.controller;

import br.pucpr.AlertCity.dto.OcorrenciaDTO;
import br.pucpr.AlertCity.service.OcorrenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaService service;


    @PostMapping(consumes = "multipart/form-data")
    public OcorrenciaDTO criar(
            @RequestPart("dados") OcorrenciaDTO dto,
            @RequestPart(value = "foto", required = false) MultipartFile foto
    ) {
        return service.salvar(dto, foto);
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